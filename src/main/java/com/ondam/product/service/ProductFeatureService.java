package com.ondam.product.service;

import java.util.Vector;

import com.ondam.product.dao.ProductFeatureDAO;
import com.ondam.product.dto.ProductFeatureDTO;

public class ProductFeatureService {

	private ProductFeatureDAO dao;

	public ProductFeatureService() {
		this.dao = new ProductFeatureDAO();
	}

	public Vector<ProductFeatureDTO> getProductFeatureList() {
		return dao.getProductFeature();
	}

	public boolean createProductFeature(ProductFeatureDTO dto) {
		return dao.insertProductFeature(dto);
	}

	public boolean modifyProductFeature(ProductFeatureDTO dto, int productFeatureNo) {
		return dao.updateProductFeature(dto, productFeatureNo);
	}

	public boolean removeProductFeature(int productFeatureNo) {
		return dao.deleteProductFeature(productFeatureNo);
	}
}