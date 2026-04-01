package com.ondam.orders.dto;

public class OrdersDTO {

    private int orderNo;
    private int userNo;
    private String orderCode;
    private String receiverName;
    private String receiverTel;
    private String deliveryAddr;
    private String deliveryContent;
    private int orderPrice;
    private int couponDiscount;
    private int walletUsedAmount;
    private int paymentAmount;
    private int paymentMethod;
    private int userCouponNo;
    private int orderState;
    private String orderDate;
    private String orderUpdateDate;
    private int deliveryState;
    private int orderType;
    private int giftReceiverNo;

    public OrdersDTO() {}

    public OrdersDTO(int orderNo, int userNo, String orderCode, String receiverName, String receiverTel,
                     String deliveryAddr, String deliveryContent, int orderPrice, int couponDiscount,
                     int walletUsedAmount, int paymentAmount, int paymentMethod, int userCouponNo,
                     int orderState, String orderDate, String orderUpdateDate, int deliveryState,
                     int orderType, int giftReceiverNo) {
        this.orderNo = orderNo;
        this.userNo = userNo;
        this.orderCode = orderCode;
        this.receiverName = receiverName;
        this.receiverTel = receiverTel;
        this.deliveryAddr = deliveryAddr;
        this.deliveryContent = deliveryContent;
        this.orderPrice = orderPrice;
        this.couponDiscount = couponDiscount;
        this.walletUsedAmount = walletUsedAmount;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
        this.userCouponNo = userCouponNo;
        this.orderState = orderState;
        this.orderDate = orderDate;
        this.orderUpdateDate = orderUpdateDate;
        this.deliveryState = deliveryState;
        this.orderType = orderType;
        this.giftReceiverNo = giftReceiverNo;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getDeliveryAddr() {
        return deliveryAddr;
    }

    public void setDeliveryAddr(String deliveryAddr) {
        this.deliveryAddr = deliveryAddr;
    }

    public String getDeliveryContent() {
        return deliveryContent;
    }

    public void setDeliveryContent(String deliveryContent) {
        this.deliveryContent = deliveryContent;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(int couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public int getWalletUsedAmount() {
        return walletUsedAmount;
    }

    public void setWalletUsedAmount(int walletUsedAmount) {
        this.walletUsedAmount = walletUsedAmount;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getUserCouponNo() {
        return userCouponNo;
    }

    public void setUserCouponNo(int userCouponNo) {
        this.userCouponNo = userCouponNo;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderUpdateDate() {
        return orderUpdateDate;
    }

    public void setOrderUpdateDate(String orderUpdateDate) {
        this.orderUpdateDate = orderUpdateDate;
    }

    public int getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(int deliveryState) {
        this.deliveryState = deliveryState;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getGiftReceiverNo() {
        return giftReceiverNo;
    }

    public void setGiftReceiverNo(int giftReceiverNo) {
        this.giftReceiverNo = giftReceiverNo;
    }
}
