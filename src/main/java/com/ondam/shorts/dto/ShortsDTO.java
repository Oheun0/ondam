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

    // [추가] 화면 표시용 필드 (DB 테이블에는 없으나 조립용으로 필요)
    private String vendorName;
    private String productName;
    // 가격
    private int productPrice;
    private int productOriginPrice;
    private String imgFile;
    
    // [추가] Getter & Setter
    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public int getProductPrice() {return productPrice;}
	public void setProductPrice(int productPrice) {this.productPrice = productPrice;}
	
	
	public int getProductOriginPrice() {
		return productOriginPrice;
	}
	public void setProductOriginPrice(int productOriginPrice) {
		this.productOriginPrice = productOriginPrice;
	}
	public String getImgFile() {
		return imgFile;
	}
	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}
	
	// ShortsDTO.java 에 추가
	private int discountRate; // 할인율 (%)

	public int getDiscountRate() { return discountRate; }
	public void setDiscountRate(int discountRate) { this.discountRate = discountRate; }
	
	public ShortsDTO() {}

    public ShortsDTO(int shortsNo, int vendorNo, int productNo, String shortsTitle, String shortsContent, String videoFile, String thumbnailImg,
                     int shortsState, String createdAt, String vendorName, String productName) {
        this.shortsNo = shortsNo;
        this.vendorNo = vendorNo;
        this.productNo = productNo;
        this.shortsTitle = shortsTitle;
        this.shortsContent = shortsContent;
        this.videoFile = videoFile;
        this.thumbnailImg = thumbnailImg;
        this.shortsState = shortsState;
        this.createdAt = createdAt;
        this.vendorName = vendorName;
        this.productName = productName;
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
