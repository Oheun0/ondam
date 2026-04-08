package com.ondam.product.controller;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.product.dto.CategoryDTO;
import com.ondam.product.dto.ProductDTO;
import com.ondam.product.dto.ProductImageDTO;
import com.ondam.product.dto.ProductOptionDTO;
import com.ondam.product.service.CategoryService;
import com.ondam.product.service.ProductImageService;
import com.ondam.product.service.ProductOptionService;
import com.ondam.product.service.ProductService;
import com.ondam.situation.dto.SituationDTO;
import com.ondam.situation.service.SituationService;
import com.ondam.user.dto.UserDTO;
import com.ondam.wish.dto.WishDTO;
import com.ondam.wish.service.WishService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ProductController implements Controller {

	private ProductService productService = new ProductService();
	private ProductImageService productImageService = new ProductImageService();
	private ProductOptionService productOptionService = new ProductOptionService();
	private CategoryService categoryService = new CategoryService();
	private SituationService situationService = new SituationService();
	private WishService wishService = new WishService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String action = request.getParameter("action");
		
		if ("getOptions".equals(action)) {
	        getOptionsJson(request, response);
	        return null; // 페이지 이동을 하지 않음 (중요)
	    }
		
		if (action == null)
			action = "list";

		switch (action) {
		case "list":
			return list(request, response);
		case "detail":
			return detail(request, response);
		case "listByCategory":
			return listByCategory(request, response);
		default:
			return "redirect:/product";
		}
	}

	private String list(HttpServletRequest request, HttpServletResponse response) {
	    String situationNoParam   = request.getParameter("situationNo");
	    String categoryNoParam    = request.getParameter("categoryNo");
	    String situationNameParam = request.getParameter("situationName");
	    String categoryNameParam  = request.getParameter("categoryName");

	    // 필터 파라미터
	    String   sort     = request.getParameter("sort");
	    String[] colors   = request.getParameterValues("color");
	    String   season   = request.getParameter("season");
	    String[] features = request.getParameterValues("feature");

	    String currentViewMode     = "type";
	    String currentCategoryName = "반팔";
	    Vector<ProductDTO> productList;

	    if (situationNoParam != null) {
	        int situationNo = Integer.parseInt(situationNoParam);
	        currentViewMode = "situation";
	        for (SituationDTO s : situationService.getSituationList()) {
	            if (s.getSituationNo() == situationNo) {
	                currentCategoryName = s.getSituationName();
	                break;
	            }
	        }
	        productList = productService.getProductListByFilter(
	            "situation", currentCategoryName, sort, colors, season, features
	        );

	    } else if (situationNameParam != null) {
	        currentViewMode     = "situation";
	        currentCategoryName = situationNameParam;
	        productList = productService.getProductListByFilter(
	            "situation", situationNameParam, sort, colors, season, features
	        );

	    } else if (categoryNoParam != null) {
	        int categoryNo = Integer.parseInt(categoryNoParam);
	        currentViewMode = "type";
	        for (CategoryDTO c : categoryService.getCategoryList()) {
	            if (c.getCategoryNo() == categoryNo) {
	                currentCategoryName = c.getCategoryName();
	                break;
	            }
	        }
	        productList = productService.getProductListByFilter(
	            "type", currentCategoryName, sort, colors, season, features
	        );

	    } else if (categoryNameParam != null) {
	        currentViewMode     = "type";
	        currentCategoryName = categoryNameParam;
	        productList = productService.getProductListByFilter(
	            "type", categoryNameParam, sort, colors, season, features
	        );

	    } else {
	        currentViewMode     = "type";
	        currentCategoryName = "";
	        productList = productService.getProductListByFilter(
	            "type", "", sort, colors, season, features
	        );
	    }

	    // 현재 선택된 필터값 → JSP에서 체크 상태 복원용
	    request.setAttribute("currentSort",     sort    != null ? sort    : "전체");
	    request.setAttribute("currentColors",   colors  != null ? java.util.Arrays.asList(colors)  : new java.util.ArrayList<>());
	    request.setAttribute("currentSeason",   season  != null ? season  : "");
	    request.setAttribute("currentFeatures", features != null ? java.util.Arrays.asList(features) : new java.util.ArrayList<>());

	    request.setAttribute("productList",         productList);
	    request.setAttribute("thumbnailMap",         buildThumbnailMap(productList));
	    request.setAttribute("currentViewMode",      currentViewMode);
	    request.setAttribute("currentCategoryName",  currentCategoryName);
	    
	    HttpSession session = request.getSession(false);
	    if (session != null && session.getAttribute("loginUser") != null) {
	        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
	        Set<Integer> wishSet = wishService.getWishedProductNos(loginUser.getUserNo());
	        request.setAttribute("wishSet", wishSet);
	    }
	    return "product/product-list";
	}

	// 2. 상품 상세 — 이미지 전체 + 옵션 목록
	private String detail(HttpServletRequest request, HttpServletResponse response) {
	    int productNo = Integer.parseInt(request.getParameter("productNo"));

	    ProductDTO product        = productService.getProductById(productNo);
	    Vector<String> images     = productService.getProductImages(productNo);
	    Vector<ProductOptionDTO> options = productService.getProductOptions(productNo);

	    // 색상 중복 제거
	    java.util.LinkedHashSet<String> colorSet = new java.util.LinkedHashSet<>();
	    java.util.LinkedHashMap<String, java.util.List<String>> colorSizeMap = new java.util.LinkedHashMap<>();
	    for (ProductOptionDTO opt : options) {
	        colorSet.add(opt.getOptionColor());
	        colorSizeMap
	            .computeIfAbsent(opt.getOptionColor(), k -> new java.util.ArrayList<>())
	            .add(opt.getOptionSize());
	    }

	    request.setAttribute("product",      product);
	    request.setAttribute("images",       images);
	    request.setAttribute("options",      options);
	    request.setAttribute("colorSet",     colorSet);
	    request.setAttribute("colorSizeMap", colorSizeMap);

	    return "product/product-detail";
	}
	private void getOptionsJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    int productNo = Integer.parseInt(request.getParameter("productNo"));
	    
	    // DAO를 통해 실제 DB 옵션 리스트 가져오기
	    Vector<ProductOptionDTO> options = productOptionService.getOptionsByProductNo(productNo);
	    
	    // JSON 응답 설정
	    response.setContentType("application/json;charset=UTF-8");
	    java.io.PrintWriter out = response.getWriter();
	    
	    // JSON 문자열 생성 (Jackson이나 Gson 라이브러리가 있다면 더 편리함)
	    StringBuilder json = new StringBuilder("[");
	    for (int i = 0; i < options.size(); i++) {
	        ProductOptionDTO opt = options.get(i);
	        json.append("{");
	        json.append("\"optionColor\":\"").append(opt.getOptionColor()).append("\",");
	        json.append("\"optionSize\":\"").append(opt.getOptionSize()).append("\",");
	        json.append("\"optionAddPrice\":").append(opt.getOptionAddPrice());
	        json.append("}");
	        if (i < options.size() - 1) json.append(",");
	    }
	    json.append("]");
	    
	    out.print(json.toString());
	    out.flush();
	    out.close();
	}
	
	private String listByCategory(HttpServletRequest request, HttpServletResponse response) {
	    String viewMode  = request.getParameter("viewMode");
	    String category  = request.getParameter("category");
	    String sort      = request.getParameter("sort");
	    String seasonUi  = request.getParameter("season");
	    String[] colors  = request.getParameterValues("color");
	    String[] features = request.getParameterValues("feature");

	    if (viewMode == null || viewMode.isEmpty()) viewMode = "type";

	    Vector<ProductDTO> productList = productService.getProductListByFilter(
	        viewMode, category, sort, colors, seasonUi, features
	    );

	    request.setAttribute("productList", productList);
	    request.setAttribute("thumbnailMap", buildThumbnailMap(productList));
	    return "product/product-grid"; // grid 조각만 forward
	}

	// ── 썸네일 Map 생성 공통 메서드 ────────────────────────────────────
	private java.util.Map<Integer, String> buildThumbnailMap(Vector<ProductDTO> productList) {
	    java.util.Map<Integer, String> thumbnailMap = new java.util.HashMap<>();
	    for (ProductDTO p : productList) {
	        Vector<ProductImageDTO> imgs = productImageService.getImagesByProductNo(p.getProductNo());
	        for (ProductImageDTO img : imgs) {
	            if (img.getImgType() == 0) { // 대표 이미지
	                thumbnailMap.put(p.getProductNo(), img.getImgFile());
	                break;
	            }
	        }
	    }
	    return thumbnailMap;
	}
}