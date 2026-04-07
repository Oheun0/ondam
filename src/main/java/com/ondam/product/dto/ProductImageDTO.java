package com.ondam.product.dto;

public class ProductImageDTO {

    private int productImgNo;
    private int productNo;
    private String imgFile;
    private int imgType;
    private int imgOrder;

    public ProductImageDTO() {}

    public ProductImageDTO(int productImgNo, int productNo, String imgFile, int imgType, int imgOrder) {
        this.productImgNo = productImgNo;
        this.productNo = productNo;
        this.imgFile = imgFile;
        this.imgType = imgType;
        this.imgOrder = imgOrder;
    }

    public int getProductImgNo() {
        return productImgNo;
    }

    public void setProductImgNo(int productImgNo) {
        this.productImgNo = productImgNo;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public String getImgFile() {
        return imgFile;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    public int getImgType() {
        return imgType;
    }

    public void setImgType(int imgType) {
        this.imgType = imgType;
    }

    public int getImgOrder() {
        return imgOrder;
    }

    public void setImgOrder(int imgOrder) {
        this.imgOrder = imgOrder;
    }
}