package com.ondam.product.dto;

public class ProductFeatureDTO {

    private int productFeatureNo;
    private int productNo;
    private String feature;

    public ProductFeatureDTO() {}

    public ProductFeatureDTO(int productFeatureNo, int productNo, String feature) {
        this.productFeatureNo = productFeatureNo;
        this.productNo = productNo;
        this.feature = feature;
    }

    public int getProductFeatureNo() {
        return productFeatureNo;
    }

    public void setProductFeatureNo(int productFeatureNo) {
        this.productFeatureNo = productFeatureNo;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}