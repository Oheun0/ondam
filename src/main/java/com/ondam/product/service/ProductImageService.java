package com.ondam.product.service;

import java.util.Vector;

import com.ondam.product.dao.ProductImageDAO;
import com.ondam.product.dto.ProductImageDTO;

public class ProductImageService {

	private ProductImageDAO dao;

	public ProductImageService() {
		this.dao = new ProductImageDAO();
	}

	public Vector<ProductImageDTO> getProductImageList() {
		return dao.getProductImage();
	}

	public boolean createProductImage(ProductImageDTO dto) {
		return dao.insertProductImage(dto);
	}

	public boolean modifyProductImage(ProductImageDTO dto, int productImgNo) {
		return dao.updateProductImage(dto, productImgNo);
	}

	public boolean removeProductImage(int productImgNo) {
		return dao.deleteProductImage(productImgNo);
	}
	
	public Vector<ProductImageDTO> getImagesByProductNo(int productNo) {
	    return dao.getByProductNo(productNo);
	}
	
	public ProductImageDTO getProductImageById(int productNo) {
		return dao.getProductImageById(productNo);
	}
}