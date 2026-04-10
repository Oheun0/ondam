package com.ondam.coupon.dto;

public class CouponDTO {

    private int couponNo;
    private String couponName;
    private String couponCode;
    private int discountType;
    private int discountValue;
    private int minOrderAmount;
    private Integer maxDiscountAmount;
    private String validFrom;
    private String validUntil;
    private String createdAt;

    public CouponDTO() {}

    public CouponDTO(int couponNo, String couponName, String couponCode,  int discountType, int discountValue,
                     int minOrderAmount, Integer maxDiscountAmount, String validFrom, String validUntil,
                     String createdAt) {
        this.couponNo = couponNo;
        this.couponName = couponName;
        this.couponCode = couponCode;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minOrderAmount = minOrderAmount;
        this.maxDiscountAmount = maxDiscountAmount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.createdAt = createdAt;
    }

    public int getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(int couponNo) {
        this.couponNo = couponNo;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponCode() { 
    	return couponCode;
    	}
    
    public void setCouponCode(String couponCode) { 
    	this.couponCode = couponCode; 
    	}

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(int discountValue) {
        this.discountValue = discountValue;
    }

    public int getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(int minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public Integer getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(Integer maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
