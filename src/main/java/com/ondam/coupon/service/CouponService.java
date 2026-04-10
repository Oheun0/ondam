package com.ondam.coupon.service;

import java.util.Vector;
import com.ondam.coupon.dao.CouponDAO;
import com.ondam.coupon.dto.CouponDTO;
import com.ondam.user.dao.UserCouponDAO;

public class CouponService {

	private final CouponDAO dao;
	private final UserCouponDAO userCouponDao;

	public CouponService() {
		this.dao = new CouponDAO();
		this.userCouponDao = new UserCouponDAO();
	}
	/* --- 관리자용 CRUD 로직 --- */
	public Vector<CouponDTO> getCouponList() {
		return dao.getCoupon();
	}

	public boolean createCoupon(CouponDTO dto) {
		if (!isValidCoupon(dto)) return false;
		return dao.insertCoupon(dto);
	}

	public boolean modifyCoupon(CouponDTO dto, int couponNo) {
		if (!isValidCoupon(dto)) return false;
		return dao.updateCoupon(dto, couponNo);
	}

	public boolean removeCoupon(int couponNo) {
		return dao.deleteCoupon(couponNo);
	}

	public CouponDTO getCouponById(int couponNo) {
		return dao.getCouponById(couponNo);
	}

	public String registerUserCoupon(int userNo, String inputCode) {
		// 1. 코드로 원본 쿠폰(마스터) 정보가 존재하는지 확인
		CouponDTO master = dao.getCouponByCode(inputCode);
		
		if (master == null) {
			return "NOT_FOUND";
		}
		// 2. 해당 유저가 이미 이 쿠폰을 발급받았는지 체크
		if (dao.isCouponAlreadyIssued(userNo, master.getCouponNo())) {
			return "DUPLICATE";
		}
		// 3. 발급 처리 (userCoupon 테이블에 데이터 추가)
		boolean result = userCouponDao.insertUserCoupon(userNo, master.getCouponNo());
		
		return result ? "SUCCESS" : "FAIL";
	}

	/* --- 비즈니스 규칙 검증 (기존 코드 유지) --- */
	private boolean isValidCoupon(CouponDTO dto) {
		if (dto == null) return false;
		if (dto.getCouponName() == null || dto.getCouponName().trim().isEmpty()) return false;
		if (dto.getDiscountValue() <= 0) return false;
		if (dto.getMinOrderAmount() < 0) return false;
		if (dto.getValidFrom() == null || dto.getValidFrom().trim().isEmpty() ||
			dto.getValidUntil() == null || dto.getValidUntil().trim().isEmpty()) {
			return false;
		}
		return true;
	}
}