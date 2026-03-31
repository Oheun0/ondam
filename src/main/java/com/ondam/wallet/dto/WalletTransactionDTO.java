package com.ondam.wallet.dto;

public class WalletTransactionDTO {

    private int transactionNo;
    private int walletNo;
    private int userNo;
    private int transactionType;
    private int amount;
    private int balanceSnapshot;
    private int orderNo;
    private String transactionDate;
    private String transactionMemo;

    public WalletTransactionDTO() {}

    public WalletTransactionDTO(int transactionNo, int walletNo, int userNo, int transactionType,
                                int amount, int balanceSnapshot, int orderNo, String transactionDate,
                                String transactionMemo) {
        this.transactionNo = transactionNo;
        this.walletNo = walletNo;
        this.userNo = userNo;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceSnapshot = balanceSnapshot;
        this.orderNo = orderNo;
        this.transactionDate = transactionDate;
        this.transactionMemo = transactionMemo;
    }

    public int getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(int transactionNo) {
        this.transactionNo = transactionNo;
    }

    public int getWalletNo() {
        return walletNo;
    }

    public void setWalletNo(int walletNo) {
        this.walletNo = walletNo;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBalanceSnapshot() {
        return balanceSnapshot;
    }

    public void setBalanceSnapshot(int balanceSnapshot) {
        this.balanceSnapshot = balanceSnapshot;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionMemo() {
        return transactionMemo;
    }

    public void setTransactionMemo(String transactionMemo) {
        this.transactionMemo = transactionMemo;
    }
}
