package com.ondam.group.dto;

public class FamilyHelpDTO {

    private int familyHelpNo;
    private int familyNo;
    private int helperUserNo;
    private int helpeeUserNo;

    public FamilyHelpDTO() {}

    public FamilyHelpDTO(int familyHelpNo, int familyNo, int helperUserNo, int helpeeUserNo) {
        this.familyHelpNo = familyHelpNo;
        this.familyNo = familyNo;
        this.helperUserNo = helperUserNo;
        this.helpeeUserNo = helpeeUserNo;
    }

    public int getFamilyHelpNo() {
        return familyHelpNo;
    }

    public void setFamilyHelpNo(int familyHelpNo) {
        this.familyHelpNo = familyHelpNo;
    }

    public int getFamilyNo() {
        return familyNo;
    }

    public void setFamilyNo(int familyNo) {
        this.familyNo = familyNo;
    }

    public int getHelperUserNo() {
        return helperUserNo;
    }

    public void setHelperUserNo(int helperUserNo) {
        this.helperUserNo = helperUserNo;
    }

    public int getHelpeeUserNo() {
        return helpeeUserNo;
    }

    public void setHelpeeUserNo(int helpeeUserNo) {
        this.helpeeUserNo = helpeeUserNo;
    }
}