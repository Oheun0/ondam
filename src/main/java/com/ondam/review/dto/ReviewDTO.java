package com.ondam.review.dto;

public class ReviewDTO {

    private int reviewNo;
    private int orderItemNo;
    private int userNo;
    private int reviewRating;
    private String reviewContent;
    private int isBodyPublic;
    private String createdAt;
    private String updatedAt;
    
    private String snapProductName;
    private String snapOptionSize;
    private String snapOptionColor;
    private String productImg;

    public ReviewDTO() {}

    public ReviewDTO(int reviewNo, int orderItemNo, int userNo, int reviewRating, String reviewContent,
                     int isBodyPublic, String createdAt, String updatedAt) {
        this.reviewNo = reviewNo;
        this.orderItemNo = orderItemNo;
        this.userNo = userNo;
        this.reviewRating = reviewRating;
        this.reviewContent = reviewContent;
        this.isBodyPublic = isBodyPublic;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getReviewNo() {
        return reviewNo;
    }

    public void setReviewNo(int reviewNo) {
        this.reviewNo = reviewNo;
    }

    public int getOrderItemNo() {
        return orderItemNo;
    }

    public void setOrderItemNo(int orderItemNo) {
        this.orderItemNo = orderItemNo;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public int getIsBodyPublic() {
        return isBodyPublic;
    }

    public void setIsBodyPublic(int isBodyPublic) {
        this.isBodyPublic = isBodyPublic;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getSnapProductName() { 
    	return snapProductName; 
    	}
    
    public void setSnapProductName(String snapProductName) { 
    	this.snapProductName = snapProductName; 
    	}
    
    public String getSnapOptionSize() { 
    	return snapOptionSize; 
    	}
    
    public void setSnapOptionSize(String snapOptionSize) { 
    	this.snapOptionSize = snapOptionSize;
    	}
    
    public String getSnapOptionColor() {
    	return snapOptionColor;
    	}
    
    public void setSnapOptionColor(String snapOptionColor) { 
    	this.snapOptionColor = snapOptionColor; 
    	}
   

    public String getProductImg() { 
    	return productImg; 
    	}
    
    public void setProductImg(String productImg) { 
    	this.productImg = productImg; 
    	}
}
