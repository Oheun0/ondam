package com.ondam.group.dto;

public class FamilyInviteDTO {

    private int invitationNo;
    private int familyNo;
    private int inviterNo;
    private int inviteeNo;
    private String inviteCode;
    private int invitationStatus;
    private String invitedAt;
    private String respondedAt;
    private String expiresAt;

    public FamilyInviteDTO() {}

    public FamilyInviteDTO(int invitationNo, int familyNo, int inviterNo, int inviteeNo,
                           String inviteCode, int invitationStatus,
                           String invitedAt, String respondedAt, String expiresAt) {
        this.invitationNo = invitationNo;
        this.familyNo = familyNo;
        this.inviterNo = inviterNo;
        this.inviteeNo = inviteeNo;
        this.inviteCode = inviteCode;
        this.invitationStatus = invitationStatus;
        this.invitedAt = invitedAt;
        this.respondedAt = respondedAt;
        this.expiresAt = expiresAt;
    }

    public int getInvitationNo() {
        return invitationNo;
    }

    public void setInvitationNo(int invitationNo) {
        this.invitationNo = invitationNo;
    }

    public int getFamilyNo() {
        return familyNo;
    }

    public void setFamilyNo(int familyNo) {
        this.familyNo = familyNo;
    }

    public int getInviterNo() {
        return inviterNo;
    }

    public void setInviterNo(int inviterNo) {
        this.inviterNo = inviterNo;
    }

    public int getInviteeNo() {
        return inviteeNo;
    }

    public void setInviteeNo(int inviteeNo) {
        this.inviteeNo = inviteeNo;
    }

    public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public int getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(int invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public String getInvitedAt() {
        return invitedAt;
    }

    public void setInvitedAt(String invitedAt) {
        this.invitedAt = invitedAt;
    }

    public String getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(String respondedAt) {
        this.respondedAt = respondedAt;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }
}