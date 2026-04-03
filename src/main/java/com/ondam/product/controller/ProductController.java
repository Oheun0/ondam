package com.ondam.product.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.product.dto.ProductDTO;
import com.ondam.product.dto.ProductImageDTO;
import com.ondam.product.dto.ProductOptionDTO;
import com.ondam.product.service.ProductImageService;
import com.ondam.product.service.ProductOptionService;
import com.ondam.product.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProductController implements Controller {

	private ProductService productService = new ProductService();
	private ProductImageService productImageService = new ProductImageService();
	private ProductOptionService productOptionService = new ProductOptionService();

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
		default:
			return "redirect:/product";
		}
	}

	// 1. 상품 목록 — 썸네일은 imgType=0(대표이미지) 첫 번째
	private String list(HttpServletRequest request, HttpServletResponse response) {
		Vector<ProductDTO> productList = productService.getProductList();

		// productNo → 썸네일 imgFile 매핑
		java.util.Map<Integer, String> thumbnailMap = new java.util.HashMap<>();
		for (ProductDTO p : productList) {
			Vector<ProductImageDTO> imgs = productImageService.getImagesByProductNo(p.getProductNo());
			if (!imgs.isEmpty()) {
				thumbnailMap.put(p.getProductNo(), imgs.get(0).getImgFile());
			}
		}

		request.setAttribute("productList", productList);
		request.setAttribute("thumbnailMap", thumbnailMap);
		return "product/list";
	}

	// 2. 상품 상세 — 이미지 전체 + 옵션 목록
	private String detail(HttpServletRequest request, HttpServletResponse response) {
		String productNoParam = request.getParameter("productNo");
		if (productNoParam == null)
			return "redirect:/product";

		int productNo = Integer.parseInt(productNoParam);

		ProductDTO product = productService.getProductById(productNo);
		Vector<ProductImageDTO> imageList = productImageService.getImagesByProductNo(productNo);
		Vector<ProductOptionDTO> optionList = productOptionService.getOptionsByProductNo(productNo);

		if (product == null)
			return "redirect:/product";

		request.setAttribute("product", product);
		request.setAttribute("imageList", imageList);
		request.setAttribute("optionList", optionList);
		return "product/detail";
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
}