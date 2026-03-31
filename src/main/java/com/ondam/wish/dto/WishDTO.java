package com.ondam.wish.dto;

public class WishDTO {

	private int wishNo;
	private int userNo;
	private int productNo;
	private String wishDate;

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
}