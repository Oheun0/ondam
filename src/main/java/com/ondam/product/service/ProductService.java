package com.ondam.product.service;

import java.util.Vector;

import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dto.ProductDTO;
import com.ondam.product.dto.ProductOptionDTO;

public class ProductService {

	private ProductDAO dao;

	public ProductService() {
		this.dao = new ProductDAO();
	}

	public Vector<ProductDTO> getProductList() {
		return dao.getProduct();
	}

	public boolean createProduct(ProductDTO dto) {
		return dao.insertProduct(dto);
	}

	public boolean modifyProduct(ProductDTO dto, int productNo) {
		return dao.updateProduct(dto, productNo);
	}

	public boolean removeProduct(int productNo) {
		return dao.deleteProduct(productNo);
	}

	public ProductDTO getProductById(int productNo) {
		return dao.getProductById(productNo);
	}
	
	public Vector<String> getProductImages(int productNo) {
		return dao.getProductImages(productNo);
	}
	
	public Vector<ProductOptionDTO> getProductOptions(int productNo) {
		return dao.getProductOptions(productNo);
	}

	public Vector<ProductDTO> getProductsBySituation(int situationNo) {
		return dao.getProductBySituationNo(situationNo);
	}

	public Vector<ProductDTO> getProductsByCategory(int categoryNo) {
		return dao.getProductByCategoryNo(categoryNo);
	}
	
	public Vector<ProductDTO> getProductListByFilter(
	        String viewMode, String category,
	        String sort, String[] colors,
	        String seasonUi, String[] features) {

	    String[] seasons = null;
	    boolean seasonAllMatch = false;  // 추가

	    if (seasonUi != null && !seasonUi.isEmpty()) {
	        switch (seasonUi) {
	            case "따뜻해요":
	                seasons = new String[]{"봄", "가을"};
	                seasonAllMatch = false;  // OR — 봄 or 가을
	                break;
	            case "시원해요":
	                seasons = new String[]{"여름"};
	                seasonAllMatch = false;
	                break;
	            case "사계절 입어요":
	                seasons = new String[]{"봄", "여름", "가을", "겨울"};
	                seasonAllMatch = true;   // AND — 4개 모두 보유
	                break;
	        }
	    }

	    return dao.getProductsByFilter(viewMode, category, sort, colors, seasons, seasonAllMatch, features);
	}
	
	public Vector<ProductDTO> getProductsByCategoryName(String categoryName) {
	    return dao.getProductsByCategoryName(categoryName);
	}

	public Vector<ProductDTO> getProductsBySituationName(String situationName) {
	    return dao.getProductsBySituationName(situationName);
	}
	
	public Vector<ProductDTO> searchProducts(String keyword) {
	    return dao.searchProducts(keyword);
	}
	
	public Vector<ProductDTO> searchProductsWithFilter(
	        String keyword, String sort,
	        String[] colors, String season, String[] features) {
	    return dao.searchProductsWithFilter(keyword, sort, colors, season, features);
	}
	
	public String getProductImage(int productNo) {
	    return dao.getProductImage(productNo);
	}
	// [추가] 찜 횟수 증가
	public boolean increaseWishCount(int productNo) {
		return dao.increaseWishCount(productNo);
	}

	// [추가] 찜 횟수 감소
	public boolean decreaseWishCount(int productNo) {
		return dao.decreaseWishCount(productNo);
	}
	
	public Vector<ProductDTO> getProductsBySeason(String season) {
	    return dao.getProductsBySeason(season);
	}
	
	public Vector<ProductDTO> getNewProducts() {
	    return dao.getNewProducts();
	}
}