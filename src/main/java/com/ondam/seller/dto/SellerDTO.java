package com.ondam.seller.dto;

public class SellerDTO {

    private int sellerAccountNo;
    private int vendorNo;
    private String sellerId;
    private String sellerPwd;
    private String sellerName;

    public SellerDTO() {}

    public SellerDTO(int sellerAccountNo, int vendorNo, String sellerId, String sellerPwd,
                     String sellerName) {
        this.sellerAccountNo = sellerAccountNo;
        this.vendorNo = vendorNo;
        this.sellerId = sellerId;
        this.sellerPwd = sellerPwd;
        this.sellerName = sellerName;
    }

    public int getSellerAccountNo() {
        return sellerAccountNo;
    }

    public void setSellerAccountNo(int sellerAccountNo) {
        this.sellerAccountNo = sellerAccountNo;
    }

    public int getVendorNo() {
        return vendorNo;
    }

    public void setVendorNo(int vendorNo) {
        this.vendorNo = vendorNo;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerPwd() {
        return sellerPwd;
    }

    public void setSellerPwd(String sellerPwd) {
        this.sellerPwd = sellerPwd;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
}
