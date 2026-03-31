package com.ondam.product.service;

import java.util.Vector;

import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dto.ProductDTO;

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
}

