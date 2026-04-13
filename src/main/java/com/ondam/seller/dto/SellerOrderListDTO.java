package com.ondam.seller.dto;

public class SellerOrderListDTO {
    // 1. 부모 주문 정보 (Orders)
    private int orderNo;
    private String orderDate;
    private int paymentMethod;
    private int orderType;
    private String deliveryContent;
    private int deliveryState;
    private String repProductName;
    private String repProductImage;
    private int totalQuantity;
    private int totalPrice;
    private int subItemCount;
    private String receiverName;

    public SellerOrderListDTO() {}

    public int getOrderNo() { return orderNo; }
    public void setOrderNo(int orderNo) { this.orderNo = orderNo; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public int getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(int paymentMethod) { this.paymentMethod = paymentMethod; }

    public int getOrderType() { return orderType; }
    public void setOrderType(int orderType) { this.orderType = orderType; }

    public String getDeliveryContent() { return deliveryContent; }
    public void setDeliveryContent(String deliveryContent) { this.deliveryContent = deliveryContent; }

    public int getDeliveryState() { return deliveryState; }
    public void setDeliveryState(int deliveryState) { this.deliveryState = deliveryState; }

    public String getRepProductName() { return repProductName; }
    public void setRepProductName(String repProductName) { this.repProductName = repProductName; }

    public String getRepProductImage() { return repProductImage; }
    public void setRepProductImage(String repProductImage) { this.repProductImage = repProductImage; }

    public int getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }

    public int getSubItemCount() { return subItemCount; }
    public void setSubItemCount(int subItemCount) { this.subItemCount = subItemCount; }
    
    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }
}