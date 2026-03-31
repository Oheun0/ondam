package com.ondam.review.dto;

public class ReviewImageDTO {

    private int reviewImgNo;
    private int reviewNo;
    private String reviewImg;
    private int imgOrder;

    public ReviewImageDTO() {}

    public ReviewImageDTO(int reviewImgNo, int reviewNo, String reviewImg, int imgOrder) {
        this.reviewImgNo = reviewImgNo;
        this.reviewNo = reviewNo;
        this.reviewImg = reviewImg;
        this.imgOrder = imgOrder;
    }

    public int getReviewImgNo() {
        return reviewImgNo;
    }

    public void setReviewImgNo(int reviewImgNo) {
        this.reviewImgNo = reviewImgNo;
    }

    public int getReviewNo() {
        return reviewNo;
    }

    public void setReviewNo(int reviewNo) {
        this.reviewNo = reviewNo;
    }

    public String getReviewImg() {
        return reviewImg;
    }

    public void setReviewImg(String reviewImg) {
        this.reviewImg = reviewImg;
    }

    public int getImgOrder() {
        return imgOrder;
    }

    public void setImgOrder(int imgOrder) {
        this.imgOrder = imgOrder;
    }
}
