package com.ondam.wish.dto;

public class WishDTO {

	private int wishNo;
	private int userNo;
	private int productNo;
	private String wishDate;

	private String productName;
	private int productPrice;
	private int productOriginPrice;
	private String productBrand;
	private String productImg;
	private int saleCount;
	private int wishCount;
	private String productPart;
	private String categoryName;
    
	public WishDTO() {}

	public WishDTO(int wishNo, int userNo, int productNo, String wishDate) {
		this.wishNo = wishNo;
		this.userNo = userNo;
		this.productNo = productNo;
		this.wishDate = wishDate;
	}

	public int getWishNo() {
		return wishNo;
	}

	public void setWishNo(int wishNo) {
		this.wishNo = wishNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public int getProductNo() {
		return productNo;
	}

	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}

	public String getWishDate() {
		return wishDate;
	}

	public void setWishDate(String wishDate) {
		this.wishDate = wishDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductOriginPrice() {
		return productOriginPrice;
	}

	public void setProductOriginPrice(int productOriginPrice) {
		this.productOriginPrice = productOriginPrice;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public int getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}

	public int getWishCount() {
		return wishCount;
	}

	public void setWishCount(int wishCount) {
		this.wishCount = wishCount;
	}

	public String getProductPart() {
		return productPart;
	}

	public void setProductPart(String productPart) {
		this.productPart = productPart;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}