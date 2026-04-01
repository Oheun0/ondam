package com.ondam.coupon.service;

import java.util.Vector;

import com.ondam.coupon.dao.CouponDAO;
import com.ondam.coupon.dto.CouponDTO;

public class CouponService {

	private final CouponDAO dao;

	public CouponService() {
		this.dao = new CouponDAO();
	}

	public Vector<CouponDTO> getCouponList() {
		return dao.getCoupon();
	}

	public boolean createCoupon(CouponDTO dto) {
		if (!isValidCoupon(dto)) {
			System.out.println("[CouponService] 유효하지 않은 쿠폰 데이터입니다. (생성 거부)");
			return false;
		}
		return dao.insertCoupon(dto);
	}

	public boolean modifyCoupon(CouponDTO dto, int couponNo) {
		if (!isValidCoupon(dto)) {
			System.out.println("[CouponService] 유효하지 않은 쿠폰 데이터입니다. (수정 거부)");
			return false;
		}
		return dao.updateCoupon(dto, couponNo);
	}

	public boolean removeCoupon(int couponNo) {
		return dao.deleteCoupon(couponNo);
	}
	
	public CouponDTO getCouponById(int couponNo) {
        return dao.getCouponById(couponNo);
    }

	// [강화된 비즈니스 규칙 검증]
	private boolean isValidCoupon(CouponDTO dto) {
		if (dto == null) return false;
		
		// 1. 필수 값인 쿠폰명이 존재하는지 확인
		if (dto.getCouponName() == null || dto.getCouponName().trim().isEmpty()) {
			return false;
		}
		
		// 2. 할인 금액/비율은 0보다 커야 함
		if (dto.getDiscountValue() <= 0) {
			return false;
		}
		
		// 3. 최소 주문 금액은 0 이상이어야 함
		if (dto.getMinOrderAmount() < 0) {
		    return false;
		}
		
		// 4. 날짜 데이터가 입력되었는지 확인 (간단한 null 체크)
		if (dto.getValidFrom() == null || dto.getValidFrom().trim().isEmpty() ||
		    dto.getValidUntil() == null || dto.getValidUntil().trim().isEmpty()) {
		    return false;
		}
		
		return true;
	}
}