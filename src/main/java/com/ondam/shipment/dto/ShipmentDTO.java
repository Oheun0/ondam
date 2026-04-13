package com.ondam.shipment.dto;

public class ShipmentDTO {

    private int shipmentNo;
    private int orderItemNo;
    private int orderNo;
    private int productNo;
    private String snapProductName;
    private int vendorNo;
    private String carrierCode;
    private String trackingNo;
    private int shipmentStatus;
    private String shippedAt;
    private String deliveredAt;
    private String createdAt;
    private String updatedAt;

    public int getShipmentNo() {
        return shipmentNo;
    }

    public void setShipmentNo(int shipmentNo) {
        this.shipmentNo = shipmentNo;
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

    public String getSnapProductName() {
        return snapProductName;
    }

    public void setSnapProductName(String snapProductName) {
        this.snapProductName = snapProductName;
    }

    public int getVendorNo() {
        return vendorNo;
    }

    public void setVendorNo(int vendorNo) {
        this.vendorNo = vendorNo;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public int getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(int shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public String getShippedAt() {
        return shippedAt;
    }

    public void setShippedAt(String shippedAt) {
        this.shippedAt = shippedAt;
    }

    public String getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(String deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

