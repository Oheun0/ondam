package com.ondam.wallet.dto;

public class WalletDTO {

    private int walletNo;
    private int familyNo;
    private int balance;
    private String createdAt;

    public WalletDTO() {}

    public WalletDTO(int walletNo, int familyNo, int balance, String createdAt) {
        this.walletNo = walletNo;
        this.familyNo = familyNo;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public int getWalletNo() {
        return walletNo;
    }

    public void setWalletNo(int walletNo) {
        this.walletNo = walletNo;
    }

    public int getFamilyNo() {
        return familyNo;
    }

    public void setFamilyNo(int familyNo) {
        this.familyNo = familyNo;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
