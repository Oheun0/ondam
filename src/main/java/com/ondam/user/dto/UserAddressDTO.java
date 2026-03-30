package com.ondam.user.dto;

public class UserAddressDTO {
		private int userAddressNo;
		private int userNo;
		private String addressName;
		private int isDefault;
		private String receiverName;
		private String receiverTel;
		private String userAddress;
		private String userDetailAddress;
		private String userZipcode;
		
		public int getUserAddressNo() {
			return userAddressNo;
		}
		public void setUserAddressNo(int userAddressNo) {
			this.userAddressNo = userAddressNo;
		}
		public int getUserNo() {
			return userNo;
		}
		public void setUserNo(int userNo) {
			this.userNo = userNo;
		}
		public String getAddressName() {
			return addressName;
		}
		public void setAddressName(String addressName) {
			this.addressName = addressName;
		}
		public int getIsDefault() {
			return isDefault;
		}
		public void setIsDefault(int isDefault) {
			this.isDefault = isDefault;
		}
		public String getReceiverName() {
			return receiverName;
		}
		public void setReceiverName(String receiverName) {
			this.receiverName = receiverName;
		}
		public String getReceiverTel() {
			return receiverTel;
		}
		public void setReceiverTel(String receiverTel) {
			this.receiverTel = receiverTel;
		}
		public String getUserAddress() {
			return userAddress;
		}
		public void setUserAddress(String userAddress) {
			this.userAddress = userAddress;
		}
		public String getUserDetailAddress() {
			return userDetailAddress;
		}
		public void setUserDetailAddress(String userDetailAddress) {
			this.userDetailAddress = userDetailAddress;
		}
		public String getUserZipcode() {
			return userZipcode;
		}
		public void setUserZipcode(String userZipcode) {
			this.userZipcode = userZipcode;
		}
}
