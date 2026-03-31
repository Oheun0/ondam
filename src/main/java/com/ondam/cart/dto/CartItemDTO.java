package com.ondam.cart.dto;

public class CartItemDTO {

    private int cartItemNo;
    private int cartNo;
    private int productNo;
    private int productOptionNo;
    private int cartQuantity;
    private String cartAddedDate;

    public CartItemDTO() {}

    public CartItemDTO(int cartItemNo, int cartNo, int productNo,
                       int productOptionNo, int cartQuantity, String cartAddedDate) {
        this.cartItemNo = cartItemNo;
        this.cartNo = cartNo;
        this.productNo = productNo;
        this.productOptionNo = productOptionNo;
        this.cartQuantity = cartQuantity;
        this.cartAddedDate = cartAddedDate;
    }

    public int getCartItemNo() {
        return cartItemNo;
    }

    public void setCartItemNo(int cartItemNo) {
        this.cartItemNo = cartItemNo;
    }

    public int getCartNo() {
        return cartNo;
    }

    public void setCartNo(int cartNo) {
        this.cartNo = cartNo;
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

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getCartAddedDate() {
        return cartAddedDate;
    }

    public void setCartAddedDate(String cartAddedDate) {
        this.cartAddedDate = cartAddedDate;
    }
}