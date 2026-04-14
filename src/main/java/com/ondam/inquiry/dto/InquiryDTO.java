package com.ondam.inquiry.dto;

public class InquiryDTO {
    // 1. inquiry 테이블 기본 컬럼
    private int inquiryNo;
    private int productNo;
    private int userNo;
    private int orderNo;
    private String inquiryTitle;
    private String inquiryContent;
    private int inquiryStatus;
    private String answerContent;
    private String answeredAt;
    private String createdAt;
    private int isSecret;
    private int isNameHidden;

    private String productBrand;
    private String productName;
    private String productImage;
    private String userName;
    
    
	public int getInquiryNo() {
		return inquiryNo;
	}
	public void setInquiryNo(int inquiryNo) {
		this.inquiryNo = inquiryNo;
	}
	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getInquiryTitle() {
		return inquiryTitle;
	}
	public void setInquiryTitle(String inquiryTitle) {
		this.inquiryTitle = inquiryTitle;
	}
	public String getInquiryContent() {
		return inquiryContent;
	}
	public void setInquiryContent(String inquiryContent) {
		this.inquiryContent = inquiryContent;
	}
	public int getInquiryStatus() {
		return inquiryStatus;
	}
	public void setInquiryStatus(int inquiryStatus) {
		this.inquiryStatus = inquiryStatus;
	}
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	public String getAnsweredAt() {
		return answeredAt;
	}
	public void setAnsweredAt(String answeredAt) {
		this.answeredAt = answeredAt;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public int getIsSecret() {
		return isSecret;
	}
	public void setIsSecret(int isSecret) {
		this.isSecret = isSecret;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public int getIsNameHidden() { 
		return isNameHidden; 
	}
    public void setIsNameHidden(int isNameHidden) { 
    	this.isNameHidden = isNameHidden;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}