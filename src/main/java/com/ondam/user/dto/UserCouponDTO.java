package com.ondam.user.dto;

public class UserCouponDTO {
		private int userCouponNo;
		private int userNo;
		private int couponNo;
		private int isUsed;
		private String issuedAt;
		private String usedAt;
		private Integer orderNo;
		
		private String couponName;
		private int discountType;
		private int discountValue;
		private int minOrderAmount;
		private Integer maxDiscountAmount;
		private String validFrom;
		private String validUntil;
		
		public String getCouponName() { return couponName; }
		public void setCouponName(String couponName) { this.couponName = couponName; }
		public int getDiscountType() { return discountType; }
		public void setDiscountType(int discountType) { this.discountType = discountType; }
		public int getDiscountValue() { return discountValue; }
		public void setDiscountValue(int discountValue) { this.discountValue = discountValue; }
		public int getMinOrderAmount() { return minOrderAmount; }
		public void setMinOrderAmount(int minOrderAmount) { this.minOrderAmount = minOrderAmount; }
		public Integer getMaxDiscountAmount() { return maxDiscountAmount; }
		public void setMaxDiscountAmount(Integer maxDiscountAmount) { this.maxDiscountAmount = maxDiscountAmount; }
		public String getValidFrom() { return validFrom; }
		public void setValidFrom(String validFrom) { this.validFrom = validFrom; }
		public String getValidUntil() { return validUntil; }
		public void setValidUntil(String validUntil) { this.validUntil = validUntil; }
		
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
		public Integer getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(Integer orderNo) {
			this.orderNo = orderNo;
		}
}
