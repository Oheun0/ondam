package com.ondam.group.dto;

public class FamilyGroupDTO {
	
	private int familyNo;
	private String familyName;
	private String familyInviteCode;
	private String familyDate;
	
	public FamilyGroupDTO() {}

	public FamilyGroupDTO(int familyNo, String familyName, String familyInvitecode, String familyDate) {
		this.familyNo = familyNo;
		this.familyName = familyName;
		this.familyInviteCode = familyInvitecode;
		this.familyDate = familyDate;
	}

	public int getFamilyNo() {
		return familyNo;
	}

	public void setFamilyNo(int familyNo) {
		this.familyNo = familyNo;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
	public String getFamilyInviteCode() {
		return familyInviteCode;
	}

	public void setFamilyInviteCode(String familyInviteCode) {
		this.familyInviteCode = familyInviteCode;
	}

	public String getFamilyDate() {
		return familyDate;
	}

	public void setFamilyDate(String familyDate) {
		this.familyDate = familyDate;
	}
}