package com.ondam.product.service;

import java.util.Vector;

import com.ondam.product.dao.ProductSeasonDAO;
import com.ondam.product.dto.ProductSeasonDTO;

public class ProductSeasonService {

	private ProductSeasonDAO dao;

	public ProductSeasonService() {
		this.dao = new ProductSeasonDAO();
	}

	public Vector<ProductSeasonDTO> getProductSeasonList() {
		return dao.getProductSeason();
	}

	public boolean createProductSeason(ProductSeasonDTO dto) {
		return dao.insertProductSeason(dto);
	}

	public boolean modifyProductSeason(ProductSeasonDTO dto, int productSeasonNo) {
		return dao.updateProductSeason(dto, productSeasonNo);
	}

	public boolean removeProductSeason(int productSeasonNo) {
		return dao.deleteProductSeason(productSeasonNo);
	}
}