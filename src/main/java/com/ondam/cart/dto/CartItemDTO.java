package com.ondam.cart.dto;

public class CartItemDTO {

    private int cartItemNo;
    private int cartNo;
    private int productNo;
    private int productOptionNo;
    private int cartQuantity;
    private String cartAddedDate;

    // [추가] 화면 표시용 필드
    private String productName;   // 상품명
    private int productPrice;    // 상품가격
    private int productOriginPrice; // 원가
    private String productImg;    // 상품 이미지 (product_img1)
    private String optionSize;    // 옵션 사이즈
    private String optionColor;    // 옵션 색상
    private int optionStock;    // 재고 수량    
    
    // [추가된 필드의 Getter/Setter]
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getProductPrice() { return productPrice; }
    public void setProductPrice(int productPrice) { this.productPrice = productPrice; }
    public int getProductOriginPrice() { return productOriginPrice; }
    public void setProductOriginPrice(int productOriginPrice) { this.productOriginPrice = productOriginPrice; }
    public String getProductImg() { return productImg; }
    public void setProductImg(String productImg) { this.productImg = productImg; }
    public String getOptionSize() { return optionSize; }
    public void setOptionSize(String optionSize) { this.optionSize = optionSize; }
    public String getOptionColor() { return optionColor; }
    public void setOptionColor(String optionColor) { this.optionColor = optionColor; }
    public int getOptionStock() { return optionStock; }
    public void setOptionStock(int optionStock) { this.optionStock = optionStock; }
    
    
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