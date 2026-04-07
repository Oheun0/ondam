package com.ondam.product.dto;

public class ProductSeasonDTO {

    private int productSeasonNo;
    private int productNo;
    private String season;

    public ProductSeasonDTO() {}

    public ProductSeasonDTO(int productSeasonNo, int productNo, String season) {
        this.productSeasonNo = productSeasonNo;
        this.productNo = productNo;
        this.season = season;
    }

    public int getProductSeasonNo() {
        return productSeasonNo;
    }

    public void setProductSeasonNo(int productSeasonNo) {
        this.productSeasonNo = productSeasonNo;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}