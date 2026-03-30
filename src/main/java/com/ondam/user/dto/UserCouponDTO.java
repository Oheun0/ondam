package com.ondam.user.dto;

public class UserCouponDTO {
		private int userCouponNo;
		private int userNo;
		private int couponNo;
		private int isUsed;
		private String issuedAt;
		private String usedAt;
		private int orderNo;
		
		public int getUserCouponNo() {
			return userCouponNo;
		}
		public void setUserCouponNo(int userCouponNo) {
			this.userCouponNo = userCouponNo;
		}
		public int getUserNo() {
			return userNo;
		}
		public void setUserNo(int userNo) {
			this.userNo = userNo;
		}
		public int getCouponNo() {
			return couponNo;
		}
		public void setCouponNo(int couponNo) {
			this.couponNo = couponNo;
		}
		public int getIsUsed() {
			return isUsed;
		}
		public void setIsUsed(int isUsed) {
			this.isUsed = isUsed;
		}
		public String getIssuedAt() {
			return issuedAt;
		}
		public void setIssuedAt(String issuedAt) {
			this.issuedAt = issuedAt;
		}
		public String getUsedAt() {
			return usedAt;
		}
		public void setUsedAt(String usedAt) {
			this.usedAt = usedAt;
		}
		public int getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(int orderNo) {
			this.orderNo = orderNo;
		}
}
