package com.ondam.product.service;

import java.util.Vector;

import com.ondam.product.dao.ProductOptionDAO;
import com.ondam.product.dto.ProductOptionDTO;

public class ProductOptionService {

	private ProductOptionDAO dao;

	public ProductOptionService() {
		this.dao = new ProductOptionDAO();
	}

	public Vector<ProductOptionDTO> getProductOptionList() {
		return dao.getProductOption();
	}

	public boolean createProductOption(ProductOptionDTO dto) {
		return dao.insertProductOption(dto);
	}

	public boolean modifyProductOption(ProductOptionDTO dto, int productOptionNo) {
		return dao.updateProductOption(dto, productOptionNo);
	}

	public boolean removeProductOption(int productOptionNo) {
		return dao.deleteProductOption(productOptionNo);
	}
	
	public Vector<ProductOptionDTO> getOptionsByProductNo(int productNo) {
	    return dao.getByProductNo(productNo);
	}
	
	public ProductOptionDTO getProductOptionByNo(int productOptionNo) {
		return dao.getProductOptionByNo(productOptionNo);
	}
}