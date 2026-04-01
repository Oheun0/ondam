package com.ondam.product.service;

import java.util.Vector;

import com.ondam.product.dao.CategoryDAO;
import com.ondam.product.dto.CategoryDTO;

public class CategoryService {

	private CategoryDAO dao;

	public CategoryService() {
		this.dao = new CategoryDAO();
	}

	public Vector<CategoryDTO> getCategoryList() {
		return dao.getCategory();
	}

	public boolean createCategory(CategoryDTO dto) {
		return dao.insertCategory(dto);
	}

	public boolean modifyCategory(CategoryDTO dto, int categoryNo) {
		return dao.updateCategory(dto, categoryNo);
	}

	public boolean removeCategory(int categoryNo) {
		return dao.deleteCategory(categoryNo);
	}
}

