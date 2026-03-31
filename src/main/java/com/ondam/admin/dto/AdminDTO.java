package com.ondam.admin.dto;

public class AdminDTO {

    private int adminNo;
    private String adminId;
    private String adminPwd;
    private String adminName;

    public AdminDTO() {}

    public AdminDTO(int adminNo, String adminId, String adminPwd, String adminName) {
        this.adminNo = adminNo;
        this.adminId = adminId;
        this.adminPwd = adminPwd;
        this.adminName = adminName;
    }

    public int getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(int adminNo) {
        this.adminNo = adminNo;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}