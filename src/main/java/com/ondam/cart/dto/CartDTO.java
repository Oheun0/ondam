package com.ondam.cart.dto;

public class CartDTO {

    private int cartNo;
    private int userNo;

    public CartDTO() {}

    public CartDTO(int cartNo, int userNo) {
        this.cartNo = cartNo;
        this.userNo = userNo;
    }

    public int getCartNo() {
        return cartNo;
    }

    public void setCartNo(int cartNo) {
        this.cartNo = cartNo;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }
}