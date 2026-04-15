package com.ondam.gift.dto;

public class GiftChatDTO {

    private int chatNo;
    private int giftNo;
    private int senderNo;
    private int receiverNo;
    private int chatType;       // 0: 선물카드, 1: 감사카드
    private String cardImg;     // 예: gift-card1.png, thanks_card_01.png
    private String sentAt;

    // 조회 시 JOIN 없이 Service에서 채워줄 부가 정보
    private String senderName;
    private String receiverName;

    public GiftChatDTO() {}

    public GiftChatDTO(int chatNo, int giftNo, int senderNo, int receiverNo,
                       int chatType, String cardImg, String sentAt) {
        this.chatNo = chatNo;
        this.giftNo = giftNo;
        this.senderNo = senderNo;
        this.receiverNo = receiverNo;
        this.chatType = chatType;
        this.cardImg = cardImg;
        this.sentAt = sentAt;
    }

    public int getChatNo() {
        return chatNo;
    }

    public void setChatNo(int chatNo) {
        this.chatNo = chatNo;
    }

    public int getGiftNo() {
        return giftNo;
    }

    public void setGiftNo(int giftNo) {
        this.giftNo = giftNo;
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

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public String getCardImg() {
        return cardImg;
    }

    public void setCardImg(String cardImg) {
        this.cardImg = cardImg;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}