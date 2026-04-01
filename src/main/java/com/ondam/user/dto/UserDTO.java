package com.ondam.user.dto;

public class UserDTO {
	private int userNo ;
	private String userId;
	private String userPwd;
	private String userName;
	private String userNick;
	private String userPhoneNumber;
	private String userEmail;
	private String userBirth;
	private int userGender;
	private int userHeight;
	private int userWeight;
	private String userProfileImg;
	private int joinReason;
	private int isActive;
	private String deleteAt;
	private int preferPayment;
	private int signupStep;
	private int signUpCompleted;
	
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}
	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserBirth() {
		return userBirth;
	}
	public void setUserBirth(String userBirth) {
		this.userBirth = userBirth;
	}
	public int getUserGender() {
		return userGender;
	}
	public void setUserGender(int userGender) {
		this.userGender = userGender;
	}
	public int getUserHeight() {
		return userHeight;
	}
	public void setUserHeight(int userHeight) {
		this.userHeight = userHeight;
	}
	public int getUserWeight() {
		return userWeight;
	}
	public void setUserWeight(int userWeight) {
		this.userWeight = userWeight;
	}
	public String getUserProfileImg() {
		return userProfileImg;
	}
	public void setUserProfileImg(String userProfileImg) {
		this.userProfileImg = userProfileImg;
	}
	public int getJoinReason() {
		return joinReason;
	}
	public void setJoinReason(int joinReason) {
		this.joinReason = joinReason;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getDeleteAt() {
		return deleteAt;
	}
	public void setDeleteAt(String deleteAt) {
		this.deleteAt = deleteAt;
	}
	public int getPreferPayment() {
		return preferPayment;
	}
	public void setPreferPayment(int preferPayment) {
		this.preferPayment = preferPayment;
	}
	public int getSignupStep() {
		return signupStep;
	}
	public void setSignupStep(int signupStep) {
		this.signupStep = signupStep;
	}
	public int getSignUpCompleted() {
		return signUpCompleted;
	}
	public void setSignUpCompleted(int signUpCompleted) {
		this.signUpCompleted = signUpCompleted;
	}
}