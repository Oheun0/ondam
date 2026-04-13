package com.ondam.seller.controller;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ondam.common.controller.Controller;
import com.ondam.product.dao.CategoryDAO;
import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dao.ProductFeatureDAO;
import com.ondam.product.dao.ProductImageDAO;
import com.ondam.product.dao.ProductOptionDAO;
import com.ondam.product.dao.ProductSeasonDAO;
import com.ondam.product.dto.ProductDTO;
import com.ondam.product.dto.CategoryDTO;
import com.ondam.product.dto.ProductFeatureDTO;
import com.ondam.product.dto.ProductImageDTO;
import com.ondam.product.dto.ProductOptionDTO;
import com.ondam.product.dto.ProductSeasonDTO;
import com.ondam.seller.dto.SellerDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/**
 * 판매자 상품 관리
 * <ul>
 * <li>GET /seller/product</li>
 * <li>GET /seller/product/form</li>
 * <li>POST /seller/product/save</li>
 * </ul>
 */
public class SellerProductController implements Controller {

	private final ProductDAO productDAO = new ProductDAO();
	private final CategoryDAO categoryDAO = new CategoryDAO();
	private final ProductSeasonDAO productSeasonDAO = new ProductSeasonDAO();
	private final ProductFeatureDAO productFeatureDAO = new ProductFeatureDAO();
	private final ProductOptionDAO productOptionDAO = new ProductOptionDAO();
	private final ProductImageDAO productImageDAO = new ProductImageDAO();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = uri.substring(contextPath.length());
		String method = request.getMethod();

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginSeller") == null) {
			return "redirect:/seller/auth";
		}
		SellerDTO seller = (SellerDTO) session.getAttribute("loginSeller");
		int vendorNo = seller.getVendorNo();

		if ("/seller/product".equals(path) && "GET".equalsIgnoreCase(method)) {
			return handleList(request, vendorNo);
		}
		if ("/seller/product/form".equals(path) && "GET".equalsIgnoreCase(method)) {
			return handleCreateForm(request);
		}
		if ("/seller/product/edit".equals(path) && "GET".equalsIgnoreCase(method)) {
			return handleEditForm(request, vendorNo);
		}
		if ("/seller/product/save".equals(path) && "POST".equalsIgnoreCase(method)) {
			return handleSave(request, vendorNo);
		}
		if ("/seller/product/update".equals(path) && "POST".equalsIgnoreCase(method)) {
			return handleUpdate(request, vendorNo);
		}
		if ("/seller/product/generate-easy-desc".equals(path) && "POST".equalsIgnoreCase(method)) {
			return handleGenerateEasyDesc(request, response);
		}

		return "redirect:/seller/product";
	}

	private String handleList(HttpServletRequest request, int vendorNo) {
		Vector<ProductDTO> list = productDAO.getProductsByVendor(vendorNo);
		Map<Integer, String> categoryNames = loadCategoryNameMap();
		List<Map<String, Object>> rows = new ArrayList<>();
		String query = trim(request.getParameter("query"));
		String categoryFilter = trim(request.getParameter("category"));
		String saleFilter = trim(request.getParameter("sale"));
		String stockFilter = trim(request.getParameter("stock"));

		int total = 0;
		int selling = 0;
		int hidden = 0;
		int soldout = 0;

		for (ProductDTO p : list) {
			int stock = 0;
			Vector<ProductOptionDTO> opts = productDAO.getProductOptions(p.getProductNo());
			if (opts != null && !opts.isEmpty()) {
				for (ProductOptionDTO o : opts) {
					stock += o.getOptionStock();
				}
			}
			if (!matchesFilter(p, stock, query, categoryFilter, saleFilter, stockFilter)) {
				continue;
			}

			total++;
			int state = p.getProductState();
			if (state == 1) {
				selling++;
			} else if (state == 0) {
				hidden++;
			} else if (state == 2) {
				soldout++;
			}

			Map<String, Object> row = new HashMap<>();
			row.put("product", p);
			row.put("categoryName", categoryNames.getOrDefault(Integer.valueOf(p.getCategoryNo()), String.valueOf(p.getCategoryNo())));

			String thumb = productDAO.getProductImage(p.getProductNo());
			row.put("thumb", thumb);
			row.put("stock", Integer.valueOf(stock));

			rows.add(row);
		}

		request.setAttribute("productRows", rows);
		request.setAttribute("productTotal", Integer.valueOf(total));
		request.setAttribute("productSelling", Integer.valueOf(selling));
		request.setAttribute("productHidden", Integer.valueOf(hidden));
		request.setAttribute("productSoldout", Integer.valueOf(soldout));
		request.setAttribute("filterQuery", query);
		request.setAttribute("filterCategory", categoryFilter.isEmpty() ? "all" : categoryFilter);
		request.setAttribute("filterSale", saleFilter.isEmpty() ? "all" : saleFilter);
		request.setAttribute("filterStock", stockFilter.isEmpty() ? "all" : stockFilter);

		return "seller/product/list";
	}

	private boolean matchesFilter(ProductDTO p, int stock, String query, String categoryFilter, String saleFilter, String stockFilter) {
		if (!query.isEmpty()) {
			String name = p.getProductName() == null ? "" : p.getProductName().toLowerCase();
			if (!name.contains(query.toLowerCase())) {
				return false;
			}
		}
		if (!categoryFilter.isEmpty() && !"all".equalsIgnoreCase(categoryFilter)) {
			int categoryNo = parseIntOrZero(categoryFilter);
			if (categoryNo <= 0 || p.getCategoryNo() != categoryNo) {
				return false;
			}
		}
		if (!saleFilter.isEmpty() && !"all".equalsIgnoreCase(saleFilter)) {
			int expectedState = mapSaleFilterToState(saleFilter);
			if (expectedState == Integer.MIN_VALUE || p.getProductState() != expectedState) {
				return false;
			}
		}
		if (!stockFilter.isEmpty() && !"all".equalsIgnoreCase(stockFilter)) {
			if ("out".equalsIgnoreCase(stockFilter) && stock > 0) {
				return false;
			}
			if ("in".equalsIgnoreCase(stockFilter) && stock <= 0) {
				return false;
			}
			if ("low".equalsIgnoreCase(stockFilter) && (stock <= 0 || stock > 5)) {
				return false;
			}
		}
		return true;
	}

	private Map<Integer, String> loadCategoryNameMap() {
		Map<Integer, String> map = new HashMap<>();
		Vector<CategoryDTO> categories = categoryDAO.getCategory();
		if (categories == null) {
			return map;
		}
		for (CategoryDTO c : categories) {
			map.put(Integer.valueOf(c.getCategoryNo()), c.getCategoryName());
		}
		return map;
	}

	private String handleCreateForm(HttpServletRequest request) {
		request.setAttribute("editMode", Boolean.FALSE);
		request.setAttribute("editFeatures", new Vector<String>());
		request.setAttribute("editSeasonUi", "");
		return "seller/product/form";
	}

	private String handleEditForm(HttpServletRequest request, int vendorNo) {
		int productNo = parseIntOrZero(request.getParameter("productNo"));
		if (productNo <= 0) {
			return "redirect:/seller/product";
		}
		ProductDTO product = productDAO.getProductById(productNo);
		if (product == null || product.getVendorNo() != vendorNo) {
			return "redirect:/seller/product";
		}

		request.setAttribute("editMode", Boolean.TRUE);
		request.setAttribute("editProduct", product);
		request.setAttribute("editProductNo", Integer.valueOf(productNo));
		request.setAttribute("editSeasonUi", mapDbSeasonsToUi(productSeasonDAO.getSeasonsByProductNo(productNo)));
		request.setAttribute("editFeatures", productFeatureDAO.getFeaturesByProductNo(productNo));
		request.setAttribute("editOptions", productOptionDAO.getByProductNo(productNo));
		request.setAttribute("editImages", productImageDAO.getByProductNo(productNo));
		return "seller/product/form";
	}

	private String handleSave(HttpServletRequest request, int vendorNo) {
		try {
			String brandName = trim(request.getParameter("brandName"));
			String productName = trim(request.getParameter("productName"));
			String situationCategory = trim(request.getParameter("situationCategory"));
			String typeCategory = trim(request.getParameter("typeCategory"));
			String saleStatus = trim(request.getParameter("saleStatus"));
			int productGender = parseIntOrZero(request.getParameter("productGender"));
			String saveMode = trim(request.getParameter("saveMode"));
			String price = digitsOnly(request.getParameter("price"));
			String discountRate = digitsOnly(request.getParameter("discountRate"));
			String salePrice = digitsOnly(request.getParameter("salePrice"));
			String productEx = trim(request.getParameter("productEx"));
			String easyOneLine = trim(request.getParameter("easyOneLine"));
			String easyFor = trim(request.getParameter("easyFor"));
			String easyComfort = trim(request.getParameter("easyComfort"));
			String productMaterial = trim(request.getParameter("productMaterial"));
			String productPattern = trim(request.getParameter("productPattern"));
			String productFit = trim(request.getParameter("productFit"));
			String productThickness = trim(request.getParameter("productThickness"));

			if (brandName.isEmpty() || productName.isEmpty() || situationCategory.isEmpty() || typeCategory.isEmpty() || price.isEmpty()) {
				return "redirect:/seller/product/form?save=fail";
			}

			int situationNo = Integer.parseInt(situationCategory);
			int categoryNo = Integer.parseInt(typeCategory);
			int originPrice = Integer.parseInt(price);
			int finalPrice = originPrice;
			if (!salePrice.isEmpty()) {
				finalPrice = Integer.parseInt(salePrice);
			} else if (!discountRate.isEmpty()) {
				int rate = Integer.parseInt(discountRate);
				if (rate > 0 && rate <= 100) {
					finalPrice = Math.round(originPrice * (100 - rate) / 100.0f);
				}
			}
			if (finalPrice <= 0 || finalPrice > originPrice) {
				finalPrice = originPrice;
			}

			int productState = mapSaleStatusToState(saleStatus);
			if ("temp".equalsIgnoreCase(saveMode)) {
				// 임시 저장은 항상 숨김 상태로 저장
				productState = 0;
			}

			ProductDTO dto = new ProductDTO();
			dto.setVendorNo(vendorNo);
			dto.setCategoryNo(categoryNo);
			dto.setSituationNo(situationNo);
			dto.setProductName(productName);
			dto.setProductBrand(brandName);
			dto.setProductEx(productEx.isEmpty() ? null : productEx);
			dto.setEasyOneLine(easyOneLine.isEmpty() ? null : easyOneLine);
			dto.setEasyFor(easyFor.isEmpty() ? null : easyFor);
			dto.setEasyComfort(easyComfort.isEmpty() ? null : easyComfort);
			dto.setProductGender(normalizeProductGender(productGender));
			dto.setProductPrice(finalPrice);
			dto.setProductOriginPrice(originPrice);
			dto.setProductMaterial(productMaterial.isEmpty() ? null : productMaterial);
			dto.setProductPattern(productPattern.isEmpty() ? null : productPattern);
			dto.setProductFit(productFit.isEmpty() ? null : productFit);
			dto.setProductThickness(productThickness.isEmpty() ? null : productThickness);
			dto.setProductState(productState);

			int productNo = productDAO.insertProductAndGetNo(dto);
			if (productNo <= 0) {
				return "redirect:/seller/product/form?save=fail";
			}

			saveSeasons(productNo, trim(request.getParameter("productSeason")));
			saveFeatures(productNo, request.getParameterValues("clothesFeature"));
			saveOptions(productNo, request.getParameterValues("optionColor"),
					request.getParameterValues("optionSize"), request.getParameterValues("optionStock"));
			saveImages(request, productNo);

			if (!"temp".equalsIgnoreCase(saveMode)) {
				int optionCount = countSavedOptions(productNo);
				int imageCount = countSavedImages(productNo);
				if (optionCount == 0 || imageCount == 0) {
					return "redirect:/seller/product/form?save=fail";
				}
			}

			if ("temp".equalsIgnoreCase(saveMode)) {
				return "redirect:/seller/product?save=temp";
			}
			return "redirect:/seller/product?save=ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/seller/product/form?save=fail";
		}
	}

	private String handleUpdate(HttpServletRequest request, int vendorNo) {
		try {
			int productNo = parseIntOrZero(request.getParameter("productNo"));
			if (productNo <= 0) {
				return "redirect:/seller/product?save=fail";
			}
			ProductDTO existing = productDAO.getProductById(productNo);
			if (existing == null || existing.getVendorNo() != vendorNo) {
				return "redirect:/seller/product?save=fail";
			}

			String brandName = trim(request.getParameter("brandName"));
			String productName = trim(request.getParameter("productName"));
			String situationCategory = trim(request.getParameter("situationCategory"));
			String typeCategory = trim(request.getParameter("typeCategory"));
			String saleStatus = trim(request.getParameter("saleStatus"));
			int productGender = parseIntOrZero(request.getParameter("productGender"));
			String saveMode = trim(request.getParameter("saveMode"));
			String price = digitsOnly(request.getParameter("price"));
			String discountRate = digitsOnly(request.getParameter("discountRate"));
			String salePrice = digitsOnly(request.getParameter("salePrice"));
			String productEx = trim(request.getParameter("productEx"));
			String easyOneLine = trim(request.getParameter("easyOneLine"));
			String easyFor = trim(request.getParameter("easyFor"));
			String easyComfort = trim(request.getParameter("easyComfort"));
			String productMaterial = trim(request.getParameter("productMaterial"));
			String productPattern = trim(request.getParameter("productPattern"));
			String productFit = trim(request.getParameter("productFit"));
			String productThickness = trim(request.getParameter("productThickness"));

			if (brandName.isEmpty() || productName.isEmpty() || price.isEmpty()) {
				return "redirect:/seller/product/edit?productNo=" + productNo + "&save=fail";
			}
			if (situationCategory.isEmpty()) {
				situationCategory = String.valueOf(existing.getSituationNo());
			}
			if (typeCategory.isEmpty()) {
				typeCategory = String.valueOf(existing.getCategoryNo());
			}

			int originPrice = Integer.parseInt(price);
			int finalPrice = originPrice;
			if (!salePrice.isEmpty()) {
				finalPrice = Integer.parseInt(salePrice);
			} else if (!discountRate.isEmpty()) {
				int rate = Integer.parseInt(discountRate);
				if (rate > 0 && rate <= 100) {
					finalPrice = Math.round(originPrice * (100 - rate) / 100.0f);
				}
			}
			if (finalPrice <= 0 || finalPrice > originPrice) {
				finalPrice = originPrice;
			}

			ProductDTO dto = new ProductDTO();
			dto.setVendorNo(vendorNo);
			dto.setCategoryNo(Integer.parseInt(typeCategory));
			dto.setSituationNo(Integer.parseInt(situationCategory));
			dto.setProductName(productName);
			dto.setProductBrand(brandName);
			dto.setProductEx(productEx.isEmpty() ? null : productEx);
			dto.setEasyOneLine(easyOneLine.isEmpty() ? null : easyOneLine);
			dto.setEasyFor(easyFor.isEmpty() ? null : easyFor);
			dto.setEasyComfort(easyComfort.isEmpty() ? null : easyComfort);
			dto.setProductGender(normalizeProductGender(productGender));
			dto.setProductPrice(finalPrice);
			dto.setProductOriginPrice(originPrice);
			dto.setProductMaterial(productMaterial.isEmpty() ? null : productMaterial);
			dto.setProductPattern(productPattern.isEmpty() ? null : productPattern);
			dto.setProductFit(productFit.isEmpty() ? null : productFit);
			dto.setProductThickness(productThickness.isEmpty() ? null : productThickness);
			dto.setProductState("temp".equalsIgnoreCase(saveMode) ? 0 : mapSaleStatusToState(saleStatus));

			if (!productDAO.updateProduct(dto, productNo)) {
				return "redirect:/seller/product/edit?productNo=" + productNo + "&save=fail";
			}

			productSeasonDAO.deleteByProductNo(productNo);
			productFeatureDAO.deleteByProductNo(productNo);
			saveSeasons(productNo, trim(request.getParameter("productSeason")));
			saveFeatures(productNo, request.getParameterValues("clothesFeature"));

			String[] colors = request.getParameterValues("optionColor");
			String[] sizes = request.getParameterValues("optionSize");
			String[] stocks = request.getParameterValues("optionStock");
			if (colors != null && sizes != null && stocks != null && colors.length > 0) {
				productOptionDAO.deleteByProductNo(productNo);
				saveOptions(productNo, colors, sizes, stocks);
			}

			boolean hasNewThumb = hasUpload(request.getPart("thumbImage"));
			if (hasNewThumb) {
				productImageDAO.deleteByProductNoAndType(productNo, 0);
				saveMainImage(request, productNo);
			}

			updateDetailImages(request, productNo);

			return "redirect:/seller/product?save=updated";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/seller/product?save=fail";
		}
	}

	private void saveOptions(int productNo, String[] optionColors, String[] optionSizes, String[] optionStocks) {
		if (optionColors == null || optionSizes == null || optionStocks == null) {
			return;
		}
		int min = Math.min(optionColors.length, Math.min(optionSizes.length, optionStocks.length));
		for (int i = 0; i < min; i++) {
			String color = trim(optionColors[i]);
			String size = trim(optionSizes[i]);
			String stockRaw = digitsOnly(optionStocks[i]);
			if (color.isEmpty() || size.isEmpty()) {
				continue;
			}
			int stock = stockRaw.isEmpty() ? 0 : Integer.parseInt(stockRaw);
			ProductOptionDTO dto = new ProductOptionDTO();
			dto.setProductNo(productNo);
			dto.setOptionColor(color);
			dto.setOptionSize(size);
			dto.setOptionAddPrice(0);
			dto.setOptionStock(stock);
			productOptionDAO.insertProductOption(dto);
		}
	}

	private int countSavedOptions(int productNo) {
		Vector<ProductOptionDTO> options = productOptionDAO.getByProductNo(productNo);
		return options == null ? 0 : options.size();
	}

	private void saveImages(HttpServletRequest request, int productNo) throws Exception {
		saveMainImage(request, productNo);
		saveDetailImages(request, productNo, 2, 5);
	}

	private void saveMainImage(HttpServletRequest request, int productNo) throws Exception {
		Part thumb = request.getPart("thumbImage");
		String thumbFile = saveUploadedImage(request, thumb);
		if (thumbFile == null) {
			return;
		}
		ProductImageDTO thumbDto = new ProductImageDTO();
		thumbDto.setProductNo(productNo);
		thumbDto.setImgFile(thumbFile);
		thumbDto.setImgType(0);
		thumbDto.setImgOrder(1);
		if (!productImageDAO.insertProductImage(thumbDto)) {
			throw new IllegalStateException("대표 이미지 DB 저장 실패");
		}
	}

	private int saveDetailImages(HttpServletRequest request, int productNo, int startOrder, int limit) throws Exception {
		List<Part> details = request.getParts().stream()
				.filter(part -> "detailImages".equals(part.getName()))
				.collect(java.util.stream.Collectors.toList());
		int saved = 0;
		int order = startOrder;
		for (Part part : details) {
			if (saved >= limit) {
				break;
			}
			String file = saveUploadedImage(request, part);
			if (file == null) {
				continue;
			}
			ProductImageDTO dto = new ProductImageDTO();
			dto.setProductNo(productNo);
			dto.setImgFile(file);
			dto.setImgType(1);
			dto.setImgOrder(order++);
			if (!productImageDAO.insertProductImage(dto)) {
				throw new IllegalStateException("상세 이미지 DB 저장 실패");
			}
			saved++;
		}
		return saved;
	}

	private void updateDetailImages(HttpServletRequest request, int productNo) throws Exception {
		String[] keepNos = request.getParameterValues("keepDetailImageNos");
		Set<Integer> keepSet = new HashSet<>();
		List<Integer> keepOrder = new ArrayList<>();
		if (keepNos != null) {
			for (String no : keepNos) {
				int imageNo = parseIntOrZero(no);
				if (imageNo <= 0 || keepSet.contains(Integer.valueOf(imageNo))) {
					continue;
				}
				keepSet.add(Integer.valueOf(imageNo));
				keepOrder.add(Integer.valueOf(imageNo));
			}
		}

		Map<Integer, ProductImageDTO> existingDetailMap = new HashMap<>();
		Vector<ProductImageDTO> existingImages = productImageDAO.getByProductNo(productNo);
		for (ProductImageDTO img : existingImages) {
			if (img.getImgType() == 1) {
				existingDetailMap.put(Integer.valueOf(img.getProductImgNo()), img);
			}
		}
		int uploadedDetailCount = countUploadsByName(request.getParts(), "detailImages");
		if (keepOrder.isEmpty() && uploadedDetailCount == 0 && !existingDetailMap.isEmpty()) {
			// 프론트에서 keep 파라미터를 못 보낸 경우 기존 상세 이미지를 그대로 유지
			for (ProductImageDTO img : existingImages) {
				if (img.getImgType() == 1) {
					keepOrder.add(Integer.valueOf(img.getProductImgNo()));
				}
			}
		}

		productImageDAO.deleteByProductNoAndType(productNo, 1);

		int maxDetailCount = 5;
		int savedCount = 0;
		int order = 2;
		for (Integer keepNo : keepOrder) {
			if (savedCount >= maxDetailCount) {
				break;
			}
			ProductImageDTO existing = existingDetailMap.get(keepNo);
			if (existing == null) {
				continue;
			}
			ProductImageDTO dto = new ProductImageDTO();
			dto.setProductNo(productNo);
			dto.setImgFile(existing.getImgFile());
			dto.setImgType(1);
			dto.setImgOrder(order++);
			if (!productImageDAO.insertProductImage(dto)) {
				throw new IllegalStateException("기존 상세 이미지 재저장 실패");
			}
			savedCount++;
		}

		if (savedCount < maxDetailCount) {
			saveDetailImages(request, productNo, order, maxDetailCount - savedCount);
		}
	}

	private int countUploadsByName(java.util.Collection<Part> parts, String name) {
		if (parts == null || name == null) {
			return 0;
		}
		int count = 0;
		for (Part part : parts) {
			if (name.equals(part.getName()) && part.getSize() > 0) {
				count++;
			}
		}
		return count;
	}

	private int countSavedImages(int productNo) {
		Vector<ProductImageDTO> images = productImageDAO.getByProductNo(productNo);
		return images == null ? 0 : images.size();
	}

	private String saveUploadedImage(HttpServletRequest request, Part part) throws Exception {
		if (part == null || part.getSize() <= 0) {
			return null;
		}
		String originalName = part.getSubmittedFileName();
		if (originalName == null || originalName.trim().isEmpty()) {
			return null;
		}
		String ext = "";
		int dot = originalName.lastIndexOf('.');
		if (dot >= 0) {
			ext = originalName.substring(dot);
		}
		String fileName = UUID.randomUUID().toString() + ext;
		String uploadPath = request.getServletContext().getRealPath("/uploads/products");
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		part.write(new File(uploadDir, fileName).getAbsolutePath());
		return fileName;
	}

	private String mapDbSeasonsToUi(Vector<String> seasons) {
		if (seasons == null || seasons.isEmpty()) {
			return "";
		}
		boolean hasSummer = seasons.contains("여름");
		boolean hasWinter = seasons.contains("겨울");
		boolean hasSpring = seasons.contains("봄");
		boolean hasAutumn = seasons.contains("가을");
		if (hasSummer && hasWinter && hasSpring && hasAutumn) {
			return "사계절 입어요";
		}
		if (hasSummer && !hasWinter && !hasSpring && !hasAutumn) {
			return "시원해요";
		}
		if (hasSpring && hasAutumn && !hasSummer && !hasWinter) {
			return "따뜻해요";
		}
		return "";
	}

	private static int parseIntOrZero(String s) {
		try {
			return Integer.parseInt(trim(s));
		} catch (Exception e) {
			return 0;
		}
	}

	private static boolean hasUpload(Part part) {
		return part != null && part.getSize() > 0;
	}

	private static boolean hasAnyUpload(java.util.Collection<Part> parts, String name) {
		if (parts == null) return false;
		for (Part part : parts) {
			if (name.equals(part.getName()) && part.getSize() > 0) {
				return true;
			}
		}
		return false;
	}

	private void saveSeasons(int productNo, String seasonUi) {
		if (seasonUi == null || seasonUi.isEmpty()) {
			return;
		}
		String[] seasons = mapSeasonUiToDbSeasons(seasonUi);
		for (String s : seasons) {
			ProductSeasonDTO dto = new ProductSeasonDTO();
			dto.setProductNo(productNo);
			dto.setSeason(s);
			productSeasonDAO.insertProductSeason(dto);
		}
	}

	private void saveFeatures(int productNo, String[] features) {
		if (features == null || features.length == 0) {
			return;
		}
		for (String f : features) {
			String v = trim(f);
			if (v.isEmpty()) {
				continue;
			}
			ProductFeatureDTO dto = new ProductFeatureDTO();
			dto.setProductNo(productNo);
			dto.setFeature(v);
			productFeatureDAO.insertProductFeature(dto);
		}
	}

	private static String[] mapSeasonUiToDbSeasons(String seasonUi) {
		switch (seasonUi) {
		case "따뜻해요":
			return new String[] { "봄", "가을" };
		case "시원해요":
			return new String[] { "여름" };
		case "사계절 입어요":
			return new String[] { "봄", "여름", "가을", "겨울" };
		default:
			return new String[0];
		}
	}

	private static int mapSaleStatusToState(String saleStatus) {
		if ("selling".equalsIgnoreCase(saleStatus)) {
			return 1;
		}
		if ("soldout".equalsIgnoreCase(saleStatus)) {
			return 2;
		}
		// hidden/unknown
		return 0;
	}

	private static int mapSaleFilterToState(String saleFilter) {
		if ("selling".equalsIgnoreCase(saleFilter)) {
			return 1;
		}
		if ("soldout".equalsIgnoreCase(saleFilter)) {
			return 2;
		}
		if ("hidden".equalsIgnoreCase(saleFilter)) {
			return 0;
		}
		return Integer.MIN_VALUE;
	}

	private static int normalizeProductGender(int gender) {
		if (gender == 1 || gender == 2) {
			return gender;
		}
		return 0;
	}

	private String handleGenerateEasyDesc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		String brandName = trim(request.getParameter("brandName"));
		String productName = trim(request.getParameter("productName"));
		String situationCategory = trim(request.getParameter("situationCategory"));
		String typeCategory = trim(request.getParameter("typeCategory"));
		String saleStatus = trim(request.getParameter("saleStatus"));
		String productGender = trim(request.getParameter("productGender"));
		String price = digitsOnly(request.getParameter("price"));
		String salePrice = digitsOnly(request.getParameter("salePrice"));
		String productEx = trim(request.getParameter("productEx"));
		String productMaterial = trim(request.getParameter("productMaterial"));
		String productPattern = trim(request.getParameter("productPattern"));
		String productFit = trim(request.getParameter("productFit"));
		String productThickness = trim(request.getParameter("productThickness"));
		String productSeason = trim(request.getParameter("productSeason"));

		if (brandName.isEmpty() || productName.isEmpty() || situationCategory.isEmpty() || typeCategory.isEmpty()
				|| saleStatus.isEmpty() || productGender.isEmpty() || price.isEmpty()
				|| productEx.isEmpty() || productMaterial.isEmpty() || productPattern.isEmpty()
				|| productFit.isEmpty() || productThickness.isEmpty() || productSeason.isEmpty()) {
			writeJson(response, false, "위의 상품 정보를 모두 입력한 후 설명 자동 생성을 실행해 주세요.", "", "", "");
			return null;
		}

		String apiKey = trim(request.getServletContext().getInitParameter("openaiApiKey"));
		if (apiKey.isEmpty()) {
			writeJson(response, false, "OpenAI API Key가 설정되지 않았어요. web.xml의 openaiApiKey를 입력해 주세요.", "", "", "");
			return null;
		}

		String prompt = buildEasyDescPrompt(brandName, productName, situationCategory, typeCategory, saleStatus, productGender,
				price, salePrice, productEx, productMaterial, productPattern, productFit, productThickness, productSeason);
		String outputText = requestEasyDescFromOpenAI(apiKey, prompt);
		String[] parsed = parseEasyDescFromOutput(outputText);
		if (parsed == null) {
			writeJson(response, false, "설명 생성 응답을 해석하지 못했어요. 잠시 후 다시 시도해 주세요.", "", "", "");
			return null;
		}
		writeJson(response, true, "", parsed[0], parsed[1], parsed[2]);
		return null;
	}

	private String buildEasyDescPrompt(String brandName, String productName, String situationCategory, String typeCategory,
			String saleStatus, String productGender, String price, String salePrice, String productEx, String material,
			String pattern, String fit, String thickness, String season) {
		StringBuilder sb = new StringBuilder();
		sb.append("너는 시니어 사용자를 위한 쇼핑몰 상품 설명을 쉽게 풀어주는 도우미다.\n");
		sb.append("다음 상품 정보를 바탕으로 쉽고 이해하기 쉬운 한국어 문장을 작성해라.\n\n");
		sb.append("반드시 JSON 한 줄만 반환해라.\n");
		sb.append("다른 설명, 줄바꿈, 추가 텍스트 절대 금지.\n");
		sb.append("형식:\n");
		sb.append("{\"easyOneLine\":\"...\",\"easyFor\":\"...\",\"easyComfort\":\"...\"}\n\n");
		sb.append("작성 조건:\n");
		sb.append("- easyOneLine은 1문장(한 줄)\n");
		sb.append("- easyFor와 easyComfort는 각각 2문장(2줄) 정도\n");
		sb.append("- 쉬운 단어 사용\n");
		sb.append("- 노인이 이해할 수 있는 표현\n");
		sb.append("- 길지 않게 작성\n\n");
		sb.append("상품 정보:\n");
		sb.append("브랜드: ").append(brandName).append('\n');
		sb.append("상품명: ").append(productName).append('\n');
		sb.append("상품 설명: ").append(productEx).append('\n');
		sb.append("소재: ").append(material).append('\n');
		sb.append("핏: ").append(fit).append('\n');
		sb.append("두께감: ").append(thickness).append('\n');
		sb.append("계절: ").append(season).append('\n');
		return sb.toString();
	}

	private String requestEasyDescFromOpenAI(String apiKey, String prompt) throws Exception {
		URL url = new URL("https://api.openai.com/v1/responses");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "Bearer " + apiKey);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);

		String body = "{"
				+ "\"model\":\"gpt-4.1-mini\","
				+ "\"input\":" + toJsonString(prompt)
				+ "}";
		try (OutputStream os = conn.getOutputStream()) {
			os.write(body.getBytes(StandardCharsets.UTF_8));
		}

		InputStream is = (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300)
				? conn.getInputStream() : conn.getErrorStream();
		String responseBody = readAll(is);
		String outputText = extractOutputTextFromResponsesJson(responseBody);
		if (outputText.isEmpty()) {
			throw new IllegalStateException("OpenAI output text is empty");
		}
		return outputText;
	}

	private String[] parseEasyDescFromOutput(String outputText) {
		String text = outputText == null ? "" : outputText.trim();
		text = stripCodeFence(text);
		int start = text.indexOf('{');
		int end = text.lastIndexOf('}');
		if (start >= 0 && end > start) {
			text = text.substring(start, end + 1);
		}
		String easyOneLine = extractFieldValue(text, "easyOneLine", "한 줄 요약");
		String easyFor = extractFieldValue(text, "easyFor", "이런 분께 좋아요");
		String easyComfort = extractFieldValue(text, "easyComfort", "입기 편한 점");
		if (easyOneLine.isEmpty() && easyFor.isEmpty() && easyComfort.isEmpty()) {
			return null;
		}
		return new String[] { easyOneLine, easyFor, easyComfort };
	}

	private String extractOutputTextFromResponsesJson(String responseBody) {
		String outputText = extractJsonStringValue(responseBody, "output_text");
		if (!outputText.isEmpty()) {
			return outputText;
		}
		Pattern p = Pattern.compile("\"text\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");
		Matcher m = p.matcher(responseBody == null ? "" : responseBody);
		if (m.find()) {
			return unescapeJsonString(m.group(1));
		}
		return "";
	}

	private String extractFieldValue(String text, String jsonKey, String labelKey) {
		String v = extractJsonStringValue(text, jsonKey);
		if (!v.isEmpty()) {
			return v.trim();
		}
		Pattern p = Pattern.compile(Pattern.quote(labelKey) + "\\s*[:：]\\s*(.+)");
		Matcher m = p.matcher(text == null ? "" : text);
		if (m.find()) {
			return trim(m.group(1));
		}
		return "";
	}

	private String stripCodeFence(String text) {
		if (text == null) return "";
		String t = text.trim();
		if (t.startsWith("```")) {
			int firstNl = t.indexOf('\n');
			if (firstNl >= 0) {
				t = t.substring(firstNl + 1);
			}
			if (t.endsWith("```")) {
				t = t.substring(0, t.length() - 3).trim();
			}
		}
		return t;
	}

	private static String toJsonString(String s) {
		String v = s == null ? "" : s;
		v = v.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "\\r").replace("\n", "\\n");
		return "\"" + v + "\"";
	}

	private static String readAll(InputStream is) throws Exception {
		if (is == null) return "";
		byte[] data = is.readAllBytes();
		return new String(data, StandardCharsets.UTF_8);
	}

	private static String extractJsonStringValue(String json, String key) {
		if (json == null || key == null) return "";
		Pattern p = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");
		Matcher m = p.matcher(json);
		if (m.find()) {
			return unescapeJsonString(m.group(1));
		}
		return "";
	}

	private static String unescapeJsonString(String s) {
		if (s == null) return "";
		StringBuilder sb = new StringBuilder();
		for (int p = 0; p < s.length(); p++) {
			char ch = s.charAt(p);
			if (ch == '\\' && p + 1 < s.length()) {
				char n = s.charAt(++p);
				if (n == 'n') sb.append('\n');
				else if (n == 'r') sb.append('\r');
				else if (n == 't') sb.append('\t');
				else if (n == 'u' && p + 4 < s.length()) {
					String hex = s.substring(p + 1, p + 5);
					try {
						sb.append((char) Integer.parseInt(hex, 16));
						p += 4;
					} catch (Exception ignore) {
						sb.append("\\u").append(hex);
						p += 4;
					}
				} else sb.append(n);
				continue;
			}
			sb.append(ch);
		}
		return sb.toString();
	}

	private void writeJson(HttpServletResponse response, boolean ok, String message,
			String easyOneLine, String easyFor, String easyComfort) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"ok\":").append(ok ? "true" : "false").append(",");
		sb.append("\"message\":").append(toJsonString(message)).append(",");
		sb.append("\"easyOneLine\":").append(toJsonString(easyOneLine)).append(",");
		sb.append("\"easyFor\":").append(toJsonString(easyFor)).append(",");
		sb.append("\"easyComfort\":").append(toJsonString(easyComfort));
		sb.append("}");
		response.getWriter().write(sb.toString());
	}

	private static String trim(String s) {
		return s == null ? "" : s.trim();
	}

	private static String digitsOnly(String s) {
		if (s == null) {
			return "";
		}
		String t = s.replaceAll("[^0-9]", "");
		return t.trim();
	}
}

