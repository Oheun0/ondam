package com.ondam.orders.dto;

public class OrdersProductDTO {

    private int orderItemNo;
    private int orderNo;
    private int productNo;
    private int productOptionNo;
    private String snapProductName;
    private int snapProductPrice;
    private String snapOptionSize;
    private String snapOptionColor;
    private int orderQuantity;
    private String productImage;

    public OrdersProductDTO() {}

    public OrdersProductDTO(int orderItemNo, int orderNo, int productNo, int productOptionNo,
                            String snapProductName, int snapProductPrice, String snapOptionSize,
                            String snapOptionColor, int orderQuantity) {
        this.orderItemNo = orderItemNo;
        this.orderNo = orderNo;
        this.productNo = productNo;
        this.productOptionNo = productOptionNo;
        this.snapProductName = snapProductName;
        this.snapProductPrice = snapProductPrice;
        this.snapOptionSize = snapOptionSize;
        this.snapOptionColor = snapOptionColor;
        this.orderQuantity = orderQuantity;
    }

    public int getOrderItemNo() {
        return orderItemNo;
    }

    public void setOrderItemNo(int orderItemNo) {
        this.orderItemNo = orderItemNo;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public int getProductOptionNo() {
        return productOptionNo;
    }

    public void setProductOptionNo(int productOptionNo) {
        this.productOptionNo = productOptionNo;
    }

    public String getSnapProductName() {
        return snapProductName;
    }

    public void setSnapProductName(String snapProductName) {
        this.snapProductName = snapProductName;
    }

    public int getSnapProductPrice() {
        return snapProductPrice;
    }

    public void setSnapProductPrice(int snapProductPrice) {
        this.snapProductPrice = snapProductPrice;
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

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
    
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
    
    public String getProductImage() {
        return productImage;
    }
}
