package com.ondam.product.dto;

public class SearchDTO {

	private int searchNo;
	private int userNo;
	private String searchKeyword;
	private String searchDate;
	
	public SearchDTO() {}

	public SearchDTO(int searchNo, int userNo, String searchKeyword, String searchDate) {
		this.searchNo = searchNo;
		this.userNo = userNo;
		this.searchKeyword = searchKeyword;
		this.searchDate = searchDate;
	}

	public int getSearchNo() {
		return searchNo;
	}

	public void setSearchNo(int searchNo) {
		this.searchNo = searchNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}
}