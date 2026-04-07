package com.ondam.product.dto;

public class ProductOptionDTO {

    private int productOptionNo;
    private int productNo;
    private String optionSize;
    private String optionColor;
    private int optionAddPrice;
    private int optionStock;

    public ProductOptionDTO() {}

    public ProductOptionDTO(int productOptionNo, int productNo, String optionSize, String optionColor,
                            int optionAddPrice, int optionStock) {
        this.productOptionNo = productOptionNo;
        this.productNo = productNo;
        this.optionSize = optionSize;
        this.optionColor = optionColor;
        this.optionAddPrice = optionAddPrice;
        this.optionStock = optionStock;
    }

    public int getProductOptionNo() {
        return productOptionNo;
    }

    public void setProductOptionNo(int productOptionNo) {
        this.productOptionNo = productOptionNo;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public String getOptionSize() {
        return optionSize;
    }

    public void setOptionSize(String optionSize) {
        this.optionSize = optionSize;
    }

    public String getOptionColor() {
        return optionColor;
    }

    public void setOptionColor(String optionColor) {
        this.optionColor = optionColor;
    }

    public int getOptionAddPrice() {
        return optionAddPrice;
    }

    public void setOptionAddPrice(int optionAddPrice) {
        this.optionAddPrice = optionAddPrice;
    }

    public int getOptionStock() {
        return optionStock;
    }

    public void setOptionStock(int optionStock) {
        this.optionStock = optionStock;
    }
}