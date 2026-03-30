package com.ondam.user.dto;

public class UserBodyDTO {
		private int UserBodyNo;
		private int userNo;
		private String userBodyType;
		private String createdAt;
		
		public int getUserBodyNo() {
			return UserBodyNo;
		}
		public void setUserBodyNo(int userBodyNo) {
			UserBodyNo = userBodyNo;
		}
		public int getUserNo() {
			return userNo;
		}
		public void setUserNo(int userNo) {
			this.userNo = userNo;
		}
		public String getUserBodyType() {
			return userBodyType;
		}
		public void setUserBodyType(String userBodyType) {
			this.userBodyType = userBodyType;
		}
		public String getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(String createdAt) {
			this.createdAt = createdAt;
		}
	}
