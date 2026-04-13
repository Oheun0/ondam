package com.ondam.seller.dto;

public class SellerOrderListDTO {
    // 1. 부모 주문 정보 (Orders)
    private int orderNo;
    private String orderDate;
    private int paymentMethod;
    private int orderType;
    private String deliveryContent; // 요청사항
    private int deliveryState;      // 부모 주문의 대표 배송 상태

    // 2. 화면 표출용 가공 데이터 (Java 로직으로 생성)
    private String repProductName;  // 대표 상품명 (예: "가디건 외 1건")
    private String repProductImage; // 대표 상품 썸네일
    private int totalQuantity;      // 이 주문에서 '내 업체'가 파는 총 수량
    private int totalPrice;         // 이 주문에서 '내 업체'가 파는 총 금액
    private int subItemCount;       // 외 N건을 계산하기 위한 숨김 필드

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
}