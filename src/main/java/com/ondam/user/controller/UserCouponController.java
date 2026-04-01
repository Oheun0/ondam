package com.ondam.user.controller;

import java.util.List;

import com.ondam.common.controller.Controller;
import com.ondam.user.dto.UserCouponDTO;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserCouponController implements Controller {

    // 향후 Service 클래스 연동을 위한 주석 처리
    // private final UserCouponService userCouponService;
    
    private static final String VIEW_PREFIX = "user/coupon/";
    private static final String REDIRECT_LOGIN = "redirect:/login";

    public UserCouponController() {
        // this.userCouponService = new UserCouponService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // 1. 소비자(User) 인증 검증 (방어적 코딩: 비로그인 접근 차단)
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        
        if (loginUser == null) {
            return REDIRECT_LOGIN;
        }

        // 2. Action 라우팅
        String action = getAction(request);

        switch (action) {
            case "list":
                return handleMyCouponList(request, loginUser.getUserNo());
            case "download":
                return handleDownloadCoupon(request, loginUser.getUserNo());
            case "available":
                // 팝업/모달 창에 쿠폰 목록을 리스팅하기 위한 JSP 포워딩
                return handleAvailableCouponsForOrder(request, loginUser.getUserNo());
            default:
                return "redirect:/main";
        }
    }

    /**
     * [조회] 마이페이지 - 내 쿠폰함 리스트
     */
    private String handleMyCouponList(HttpServletRequest request, int userNo) {
        // List<UserCouponDTO> couponList = userCouponService.getCouponList(userNo);
        // request.setAttribute("myCoupons", couponList);
        
        return VIEW_PREFIX + "list"; 
    }

    /**
     * [발급] 이벤트/상품 페이지에서 쿠폰 다운로드
     */
    private String handleDownloadCoupon(HttpServletRequest request, int userNo) {
        int targetCouponNo = parseParam(request.getParameter("couponNo"), -1);
        
        if (targetCouponNo != -1) {
            // userCouponService.downloadCoupon(userNo, targetCouponNo);
        }
        
        // 발급 요청 이전 페이지(Referer)로 돌아가기
        String referer = request.getHeader("Referer");
        return referer != null ? "redirect:" + referer.substring(request.getContextPath().length()) : "redirect:/userCoupon?action=list";
    }

    /**
     * [팝업 리스팅] 주문/결제 창에서 사용 가능한 쿠폰 목록 JSP 반환
     */
    private String handleAvailableCouponsForOrder(HttpServletRequest request, int userNo) {
        // 주문 페이지에서 넘어온 결제 예정 금액 (이 금액 이상의 최소주문금액을 가진 쿠폰만 필터링하기 위함)
        int orderAmount = parseParam(request.getParameter("orderAmount"), 0);
        
        // TODO: Service에서 Coupon 마스터 테이블과 조인하여, 
        // 1) isUsed == 0 이고 
        // 2) minOrderAmount <= orderAmount 이며
        // 3) 유효기간 내에 있는 쿠폰만 가져오는 로직 호출
        // List<UserCouponDTO> availableCoupons = userCouponService.getAvailableCoupons(userNo, orderAmount);
        
        // request에 리스트를 담아 JSP로 전달
        // request.setAttribute("availableCoupons", availableCoupons);
        request.setAttribute("orderAmount", orderAmount);
        
        // 쿠폰 선택 팝업창(또는 모달 컨텐츠) JSP로 포워딩
        return VIEW_PREFIX + "availablePopup"; 
    }

    // ==========================================
    // [Utility Methods]
    // ==========================================
    private String getAction(HttpServletRequest request) {
        String action = request.getParameter("action");
        return (action == null || action.trim().isEmpty()) ? "list" : action.trim();
    }

    private int parseParam(String param, int defaultValue) {
        if (param == null || param.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(param.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}