package com.ondam.seller.dto;

public class SellerOrderItemDTO {
    private int orderItemNo;
    private String productName;
    private String optionSize;
    private String optionColor;
    private int quantity;
    private int price;
    private String productImage;
    private int deliveryState;
    private String courier;
    private String trackingNo;

    public SellerOrderItemDTO() {}

    public int getOrderItemNo() {
    	return orderItemNo;
    }
    public void setOrderItemNo(int orderItemNo) { 
    	this.orderItemNo = orderItemNo;
    }
    public String getProductName() { 
    	return productName; 
    }
    public void setProductName(String productName) { 
    	this.productName = productName;
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
    public int getQuantity() { 
    	return quantity; 
    }
    public void setQuantity(int quantity) { 
    	this.quantity = quantity;
    }
    public int getPrice() { 
    	return price; 
    }
    public void setPrice(int price) { 
    	this.price = price; 
    }
    public String getProductImage() {
    	return productImage;
    }
    public void setProductImage(String productImage) {
    	this.productImage = productImage;
    }
    public int getDeliveryState() {
        return deliveryState;
    }
    public void setDeliveryState(int deliveryState) {
        this.deliveryState = deliveryState;
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