package com.ondam.coupon.service;

import java.util.Vector;

import com.ondam.coupon.dao.CouponDAO;
import com.ondam.coupon.dto.CouponDTO;

public class CouponService {

	private CouponDAO dao;

	public CouponService() {
		this.dao = new CouponDAO();
	}

	public Vector<CouponDTO> getCouponList() {
		return dao.getCoupon();
	}

	public boolean createCoupon(CouponDTO dto) {
		return dao.insertCoupon(dto);
	}

	public boolean modifyCoupon(CouponDTO dto, int couponNo) {
		return dao.updateCoupon(dto, couponNo);
	}

	public boolean removeCoupon(int couponNo) {
		return dao.deleteCoupon(couponNo);
	}
}

