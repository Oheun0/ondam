package com.ondam.group.dto;

public class FamilyMemberDTO {

    private int familyMemberNo;
    private int familyNo;
    private int userNo;
    private int familyAuth;
    private String userName;

    public FamilyMemberDTO() {}

    public FamilyMemberDTO(int familyMemberNo, int familyNo, int userNo,
                           int familyAuth, String userName) {
        this.familyMemberNo = familyMemberNo;
        this.familyNo = familyNo;
        this.userNo = userNo;
        this.familyAuth = familyAuth;
        this.userName = userName;
    }

    public int getFamilyMemberNo() {
        return familyMemberNo;
    }

    public void setFamilyMemberNo(int familyMemberNo) {
        this.familyMemberNo = familyMemberNo;
    }

    public int getFamilyNo() {
        return familyNo;
    }

    public void setFamilyNo(int familyNo) {
        this.familyNo = familyNo;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public int getFamilyAuth() {
        return familyAuth;
    }

    public void setFamilyAuth(int familyAuth) {
        this.familyAuth = familyAuth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}