package com.ondam.user.service;

import java.util.List;

import com.ondam.user.dao.UserCouponDAO;
import com.ondam.user.dto.UserCouponDTO;

public class UserCouponService {

    private final UserCouponDAO dao;

    public UserCouponService() {
        this.dao = new UserCouponDAO();
    }
    // 마이페이지 내 쿠폰함 조회
    public List<UserCouponDTO> getMyCouponList(int userNo) {
        if (userNo <= 0) return null;
        return dao.getCouponList(userNo);
    }

    // 결제창 사용 가능 쿠폰 조회
    public List<UserCouponDTO> getAvailableCoupons(int userNo, int orderAmount) {
        if (userNo <= 0) return null;
        return dao.getAvailableCoupons(userNo, orderAmount);
    }

    // 쿠폰 발급/다운로드
    public boolean downloadCoupon(int userNo, int couponNo) {
        if (userNo <= 0 || couponNo <= 0) return false;
        
        // TODO: 이미 발급받은 쿠폰인지 검증하는 로직 추가 필요 (DAO에 existsUserCoupon 구현 요망)
        return dao.insertUserCoupon(userNo, couponNo);
    }
    
    public boolean useUserCoupon(int userCouponNo, int orderNo) {
        return dao.useUserCoupon(userCouponNo, orderNo);
    }
}