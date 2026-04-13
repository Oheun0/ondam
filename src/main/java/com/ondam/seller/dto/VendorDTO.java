package com.ondam.seller.dto;

public class VendorDTO {

	private int vendorNo;
	private String vendorName;
	private int bizType;
	private String bizRegNo;
	private String repName;
	private String bizAddr;
	private String bizReturnAddr;
	private String bizTel;
	private String contactEmail;
	private String bizRegImg;
	private String mailOrderImg;
	private String sealCertImg;
	private String corpRegImg;
	private String logoImg;
	private String bizDescription;
	private int reviewStatus;
	private String rejectReason;
	private String applyDate;

	/** DB: return_exchange_guide */
	private String returnExchangeGuide;
	private Integer shipFee;
	private Integer freeShipMin;
	private String prepDays;
	private String defaultCourier;
	private Integer islandExtra;
	private String shipNotice;
	private String delayNotice;
	private String giftNotice;
	private String exchangeNotice;

	public VendorDTO() {}

	public VendorDTO(int vendorNo, String vendorName, int bizType, String bizRegNo, String repName,
			String bizAddr, String bizReturnAddr, String bizTel, String contactEmail, String bizRegImg,
			String mailOrderImg, String sealCertImg, String corpRegImg, String logoImg,
			String bizDescription, int reviewStatus, String rejectReason, String applyDate) {
		this.vendorNo = vendorNo;
		this.vendorName = vendorName;
		this.bizType = bizType;
		this.bizRegNo = bizRegNo;
		this.repName = repName;
		this.bizAddr = bizAddr;
		this.bizReturnAddr = bizReturnAddr;
		this.bizTel = bizTel;
		this.contactEmail = contactEmail;
		this.bizRegImg = bizRegImg;
		this.mailOrderImg = mailOrderImg;
		this.sealCertImg = sealCertImg;
		this.corpRegImg = corpRegImg;
		this.logoImg = logoImg;
		this.bizDescription = bizDescription;
		this.reviewStatus = reviewStatus;
		this.rejectReason = rejectReason;
		this.applyDate = applyDate;
	}

	public int getVendorNo() {
		return vendorNo;
	}

	public void setVendorNo(int vendorNo) {
		this.vendorNo = vendorNo;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public int getBizType() {
		return bizType;
	}

	public void setBizType(int bizType) {
		this.bizType = bizType;
	}

	public String getBizRegNo() {
		return bizRegNo;
	}

	public void setBizRegNo(String bizRegNo) {
		this.bizRegNo = bizRegNo;
	}

	public String getRepName() {
		return repName;
	}

	public void setRepName(String repName) {
		this.repName = repName;
	}

	public String getBizAddr() {
		return bizAddr;
	}

	public void setBizAddr(String bizAddr) {
		this.bizAddr = bizAddr;
	}

	public String getBizReturnAddr() {
		return bizReturnAddr;
	}

	public void setBizReturnAddr(String bizReturnAddr) {
		this.bizReturnAddr = bizReturnAddr;
	}

	public String getBizTel() {
		return bizTel;
	}

	public void setBizTel(String bizTel) {
		this.bizTel = bizTel;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getBizRegImg() {
		return bizRegImg;
	}

	public void setBizRegImg(String bizRegImg) {
		this.bizRegImg = bizRegImg;
	}

	public String getMailOrderImg() {
		return mailOrderImg;
	}

	public void setMailOrderImg(String mailOrderImg) {
		this.mailOrderImg = mailOrderImg;
	}

	public String getSealCertImg() {
		return sealCertImg;
	}

	public void setSealCertImg(String sealCertImg) {
		this.sealCertImg = sealCertImg;
	}

	public String getCorpRegImg() {
		return corpRegImg;
	}

	public void setCorpRegImg(String corpRegImg) {
		this.corpRegImg = corpRegImg;
	}

	public String getLogoImg() {
		return logoImg;
	}

	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}

	public String getBizDescription() {
		return bizDescription;
	}

	public void setBizDescription(String bizDescription) {
		this.bizDescription = bizDescription;
	}

	public int getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(int reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getReturnExchangeGuide() {
		return returnExchangeGuide;
	}

	public void setReturnExchangeGuide(String returnExchangeGuide) {
		this.returnExchangeGuide = returnExchangeGuide;
	}

	public Integer getShipFee() {
		return shipFee;
	}

	public void setShipFee(Integer shipFee) {
		this.shipFee = shipFee;
	}

	public Integer getFreeShipMin() {
		return freeShipMin;
	}

	public void setFreeShipMin(Integer freeShipMin) {
		this.freeShipMin = freeShipMin;
	}

	public String getPrepDays() {
		return prepDays;
	}

	public void setPrepDays(String prepDays) {
		this.prepDays = prepDays;
	}

	public String getDefaultCourier() {
		return defaultCourier;
	}

	public void setDefaultCourier(String defaultCourier) {
		this.defaultCourier = defaultCourier;
	}

	public Integer getIslandExtra() {
		return islandExtra;
	}

	public void setIslandExtra(Integer islandExtra) {
		this.islandExtra = islandExtra;
	}

	public String getShipNotice() {
		return shipNotice;
	}

	public void setShipNotice(String shipNotice) {
		this.shipNotice = shipNotice;
	}

	public String getDelayNotice() {
		return delayNotice;
	}

	public void setDelayNotice(String delayNotice) {
		this.delayNotice = delayNotice;
	}

	public String getGiftNotice() {
		return giftNotice;
	}

	public void setGiftNotice(String giftNotice) {
		this.giftNotice = giftNotice;
	}

	public String getExchangeNotice() {
		return exchangeNotice;
	}

	public void setExchangeNotice(String exchangeNotice) {
		this.exchangeNotice = exchangeNotice;
	}
}
