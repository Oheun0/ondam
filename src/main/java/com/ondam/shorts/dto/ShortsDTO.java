package com.ondam.shorts.dto;

public class ShortsDTO {

    private int shortsNo;
    private int vendorNo;
    private int productNo;
    private String shortsTitle;
    private String shortsContent;    
    private String videoFile;
    private String thumbnailImg;
    private int shortsState; // -1: 실패, 0: 생성 중, 1: 공개, 2: 비공개(나만보기)
    private String createdAt;

    public ShortsDTO() {}

    public ShortsDTO(int shortsNo, int vendorNo, int productNo, String shortsTitle, String shortsContent, String videoFile, String thumbnailImg,
                     int shortsState, String createdAt) {
        this.shortsNo = shortsNo;
        this.vendorNo = vendorNo;
        this.productNo = productNo;
        this.shortsTitle = shortsTitle;
        this.shortsContent = shortsContent;
        this.videoFile = videoFile;
        this.thumbnailImg = thumbnailImg;
        this.shortsState = shortsState;
        this.createdAt = createdAt;
    }

    public int getShortsNo() {
        return shortsNo;
    }

    public String getShortsTitle() {
		return shortsTitle;
	}

	public void setShortsTitle(String shortsTitle) {
		this.shortsTitle = shortsTitle;
	}

	public String getShortsContent() {
		return shortsContent;
	}

	public void setShortsContent(String shortsContent) {
		this.shortsContent = shortsContent;
	}

	public void setShortsNo(int shortsNo) {
        this.shortsNo = shortsNo;
    }

    public int getVendorNo() {
        return vendorNo;
    }

    public void setVendorNo(int vendorNo) {
        this.vendorNo = vendorNo;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    public String getThumbnailImg() {
        return thumbnailImg;
    }

    public void setThumbnailImg(String thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }

    public int getShortsState() {
        return shortsState;
    }

    public void setShortsState(int shortsState) {
        this.shortsState = shortsState;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
