package com.ondam.seller.dto;
import java.util.List;

public class SellerOrderDetailDTO {
    private int orderNo;
    private String orderDate;
    private int deliveryState;
    private int orderType;
    private int paymentMethod;
    private String courier;
    private String trackingNo;
    
    private String receiverName;
    private String receiverTel;
    private String deliveryAddr;
    private String deliveryContent;
    
    private List<SellerOrderItemDTO> itemList;

    public SellerOrderDetailDTO() {}

    public int getOrderNo() { 
    	return orderNo;
    	}
    public void setOrderNo(int orderNo) { 
    	this.orderNo = orderNo;
    	}
    public String getOrderDate() { 
    	return orderDate;
    	}
    public void setOrderDate(String orderDate) { 
    	this.orderDate = orderDate;
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
    public int getPaymentMethod() { 
    	return paymentMethod;
    	}
    public void setPaymentMethod(int paymentMethod) { 
    	this.paymentMethod = paymentMethod;
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
    public List<SellerOrderItemDTO> getItemList() {
    	return itemList; 
    	}
    public void setItemList(List<SellerOrderItemDTO> itemList) {
    	this.itemList = itemList; 
    	}
    public String getCourier() { 
    	return courier; 
    	}
    public void setCourier(String courier) {
    	this.courier = courier; 
    	}
    public String getTrackingNo() { 
    	return trackingNo; 
    	}
    public void setTrackingNo(String trackingNo) {
    	this.trackingNo = trackingNo; 
    	}
}