package com.ondam.poke.dto;

public class PokeDTO {

    private int pokeNo;
    private int productNo;
    private int senderNo;
    private int receiverNo;
    private int familyNo;
    private String pokeMsg;
    private int sendState;
    private String sendDate;
    private int connectedOrderNo;

    public PokeDTO() {}

    public PokeDTO(int pokeNo, int productNo, int senderNo, int receiverNo,
                   int familyNo, String pokeMsg, int sendState,
                   String sendDate, int connectedOrderNo) {
        this.pokeNo = pokeNo;
        this.productNo = productNo;
        this.senderNo = senderNo;
        this.receiverNo = receiverNo;
        this.familyNo = familyNo;
        this.pokeMsg = pokeMsg;
        this.sendState = sendState;
        this.sendDate = sendDate;
        this.connectedOrderNo = connectedOrderNo;
    }

    public int getPokeNo() {
        return pokeNo;
    }

    public void setPokeNo(int pokeNo) {
        this.pokeNo = pokeNo;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
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

    public int getFamilyNo() {
        return familyNo;
    }

    public void setFamilyNo(int familyNo) {
        this.familyNo = familyNo;
    }

    public String getPokeMsg() {
        return pokeMsg;
    }

    public void setPokeMsg(String pokeMsg) {
        this.pokeMsg = pokeMsg;
    }

    public int getSendState() {
        return sendState;
    }

    public void setSendState(int sendState) {
        this.sendState = sendState;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public int getConnectedOrderNo() {
        return connectedOrderNo;
    }

    public void setConnectedOrderNo(int connectedOrderNo) {
        this.connectedOrderNo = connectedOrderNo;
    }
}