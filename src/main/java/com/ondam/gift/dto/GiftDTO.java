package com.ondam.gift.dto;

public class GiftDTO {

    private int giftNo;
    private int orderNo;
    private int senderNo;
    private int receiverNo;
    private String giftMsg;
    private int giftState;
    private String sentAt;
    private String respondedAt;

    public GiftDTO() {}

    public GiftDTO(int giftNo, int orderNo, int senderNo, int receiverNo, String giftMsg,
                   int giftState, String sentAt, String respondedAt) {
        this.giftNo = giftNo;
        this.orderNo = orderNo;
        this.senderNo = senderNo;
        this.receiverNo = receiverNo;
        this.giftMsg = giftMsg;
        this.giftState = giftState;
        this.sentAt = sentAt;
        this.respondedAt = respondedAt;
    }

    public int getGiftNo() {
        return giftNo;
    }

    public void setGiftNo(int giftNo) {
        this.giftNo = giftNo;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getSenderNo() {
        return senderNo;
    }

    public void setSenderNo(int senderNo) {
        this.senderNo = senderNo;
    }

    public int getReceiverNo() {
        return receiverNo;
    }

    public void setReceiverNo(int receiverNo) {
        this.receiverNo = receiverNo;
    }

    public String getGiftMsg() {
        return giftMsg;
    }

    public void setGiftMsg(String giftMsg) {
        this.giftMsg = giftMsg;
    }

    public int getGiftState() {
        return giftState;
    }

    public void setGiftState(int giftState) {
        this.giftState = giftState;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public String getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(String respondedAt) {
        this.respondedAt = respondedAt;
    }
}
