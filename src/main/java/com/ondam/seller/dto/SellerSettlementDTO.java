package com.ondam.seller.dto;

public class SellerSettlementDTO {
    private int settlementNo;
    private int vendorNo;
    private String settleDate;
    private int totalAmount;
    private int commissionFee;
    private int actualAmount;
    private int settleState;
    private String createdAt;

    public int getSettlementNo() { return settlementNo; }
    public void setSettlementNo(int settlementNo) { this.settlementNo = settlementNo; }

    public int getVendorNo() { return vendorNo; }
    public void setVendorNo(int vendorNo) { this.vendorNo = vendorNo; }

    public String getSettleDate() { return settleDate; }
    public void setSettleDate(String settleDate) { this.settleDate = settleDate; }

    public int getTotalAmount() { return totalAmount; }
    public void setTotalAmount(int totalAmount) { this.totalAmount = totalAmount; }

    public int getCommissionFee() { return commissionFee; }
    public void setCommissionFee(int commissionFee) { this.commissionFee = commissionFee; }

    public int getActualAmount() { return actualAmount; }
    public void setActualAmount(int actualAmount) { this.actualAmount = actualAmount; }

    public int getSettleState() { return settleState; }
    public void setSettleState(int settleState) { this.settleState = settleState; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}