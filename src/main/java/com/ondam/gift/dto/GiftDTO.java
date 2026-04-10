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
    private int addressNo;

    
    private String senderName;    // 보낸 사람 이름
    private String receiverName;  // 받는 사람 이름
    private String productName;   // 상품명
    private String productImg;    // 상품 이미지 파일명 
    
    private String receiverAddressName;
    private String receiverAddress;
    private String receiverDetailAddress;
    private String receiverZipcode;
    private String receiverPhoneNumber;
    
    public String getSenderName() {return senderName;}
	public void setSenderName(String senderName) {this.senderName = senderName;}
	public String getReceiverName() {return receiverName;}
	public void setReceiverName(String receiverName) {this.receiverName = receiverName;}
	public String getProductName() {return productName;}
	public void setProductName(String productName) {this.productName = productName;}
	public String getProductImg() {return productImg;}
	public void setProductImg(String productImg) {this.productImg = productImg;}

	public String getReceiverAddressName() {return receiverAddressName;}
	public void setReceiverAddressName(String receiverAddressName) {this.receiverAddressName = receiverAddressName;}
	public String getReceiverAddress() {return receiverAddress;}
	public void setReceiverAddress(String receiverAddress) {this.receiverAddress = receiverAddress;}
	public String getReceiverDetailAddress() {return receiverDetailAddress;}
	public void setReceiverDetailAddress(String receiverDetailAddress) {this.receiverDetailAddress = receiverDetailAddress;}
	public String getReceiverZipcode() {return receiverZipcode;}
	public void setReceiverZipcode(String receiverZipcode) {this.receiverZipcode = receiverZipcode;}
	public String getReceiverPhoneNumber() {return receiverPhoneNumber;}
	public void setReceiverPhoneNumber(String receiverPhoneNumber) {this.receiverPhoneNumber = receiverPhoneNumber;}

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
	public int getAddressNo() {
		return addressNo;
	}
	public void setAddressNo(int addressNo) {
		this.addressNo = addressNo;
	}
}
