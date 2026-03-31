package com.ondam.product.dto;

public class CategoryDTO {

    private int categoryNo;
    private int upCategoryNo;
    private int categoryLevel;
    private String categoryName;
    private String categoryImg;

    public CategoryDTO() {}

    public CategoryDTO(int categoryNo, int upCategoryNo, int categoryLevel, String categoryName, String categoryImg) {
        this.categoryNo = categoryNo;
        this.upCategoryNo = upCategoryNo;
        this.categoryLevel = categoryLevel;
        this.categoryName = categoryName;
        this.categoryImg = categoryImg;
    }

    public int getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(int categoryNo) {
        this.categoryNo = categoryNo;
    }

    public int getUpCategoryNo() {
        return upCategoryNo;
    }

    public void setUpCategoryNo(int upCategoryNo) {
        this.upCategoryNo = upCategoryNo;
    }

    public int getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(int categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

	public String getCategoryImg() {
		return categoryImg;
	}

	public void setCategoryImg(String categoryImg) {
		this.categoryImg = categoryImg;
	}
}