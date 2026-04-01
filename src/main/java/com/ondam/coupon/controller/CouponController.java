package com.ondam.coupon.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.coupon.dto.CouponDTO;
import com.ondam.coupon.service.CouponService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CouponController implements Controller {

    private final CouponService couponService;
    
    // 향후 뷰(JSP) 폴더 구조나 라우팅 규칙이 변경될 경우 이곳만 수정하도록 상수화
    private static final String VIEW_PREFIX = "admin/coupon/";
    private static final String REDIRECT_LIST = "redirect:/coupon?action=list";

    public CouponController() {
        this.couponService = new CouponService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // 1. 권한 검증: 향후 관리자 인증 방식이 확정되면 isAdminAuthorized 내부만 변경
        if (!isAdminAuthorized(request)) {
            // 권한 부족 시 이동할 경로 (추후 관리자 로그인 페이지 등으로 변경 가능)
            return "redirect:/main";
        }

        // 2. Action 라우팅
        String action = getAction(request);

        switch (action) {
            case "list":
                return handleList(request);
            case "createForm":
                return VIEW_PREFIX + "create";
            case "createProc":
                return handleCreate(request);
            case "modifyForm":
                return handleModifyForm(request);
            case "modifyProc":
                return handleModify(request);
            case "deleteProc":
                return handleDelete(request);
            default:
                // 매핑되지 않은 액션에 대한 안전한 폴백(Fallback) 처리
                return REDIRECT_LIST;
        }
    }

    // ==========================================
    // [Handler Methods] 각 비즈니스 로직 단위 격리
    // ==========================================

    private String handleList(HttpServletRequest request) {
        Vector<CouponDTO> couponList = couponService.getCouponList();
        request.setAttribute("couponList", couponList);
        return VIEW_PREFIX + "list";
    }

    private String handleCreate(HttpServletRequest request) {
        CouponDTO dto = extractCouponDto(request);
        // 필수 값인 쿠폰명이 존재하는지 최소한의 무결성 검증
        if (dto != null && dto.getCouponName() != null && !dto.getCouponName().trim().isEmpty()) {
            couponService.createCoupon(dto);
        }
        return REDIRECT_LIST;
    }

    private String handleModifyForm(HttpServletRequest request) {
        int targetNo = parseParam(request.getParameter("couponNo"), -1);
        CouponDTO targetCoupon = couponService.getCouponById(targetNo);
        
        if (targetCoupon == null) {
            return REDIRECT_LIST;
        }
        
        request.setAttribute("coupon", targetCoupon);
        return VIEW_PREFIX + "modify";
    }

    private String handleModify(HttpServletRequest request) {
        int couponNo = parseParam(request.getParameter("couponNo"), -1);
        CouponDTO dto = extractCouponDto(request);
        
        if (couponNo != -1 && dto != null) {
            couponService.modifyCoupon(dto, couponNo);
        }
        return REDIRECT_LIST;
    }

    private String handleDelete(HttpServletRequest request) {
        int couponNo = parseParam(request.getParameter("couponNo"), -1);
        if (couponNo != -1) {
            couponService.removeCoupon(couponNo);
        }
        return REDIRECT_LIST;
    }

    // ==========================================
    // [Utility Methods] 향후 확장을 위한 공통 유틸리티
    // ==========================================

    /**
     * Action 파라미터 추출 및 기본값 처리
     */
    private String getAction(HttpServletRequest request) {
        String action = request.getParameter("action");
        return (action == null || action.trim().isEmpty()) ? "list" : action.trim();
    }

    /**
     * 관리자 권한 검증 로직을 단일 메서드로 캡슐화.
     * 향후 어드민 DTO나 세션 키 구조가 확정되면 이 부분만 수정.
     */
    private boolean isAdminAuthorized(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        // TODO: 관리자 세션 구조가 확정되면 아래 로직으로 교체
        // Object adminUser = session.getAttribute("adminUser");
        // return adminUser != null;
        
        // 현재는 개발 및 테스트를 위해 임시로 무조건 패스(true) 시킵니다.
        return true; 
    }

    /**
     * Request에서 파라미터를 파싱하여 DTO로 변환하는 책임 분리
     */
    private CouponDTO extractCouponDto(HttpServletRequest request) {
        CouponDTO dto = new CouponDTO();
        dto.setCouponName(request.getParameter("couponName"));
        dto.setDiscountType(parseParam(request.getParameter("discountType"), 0));
        dto.setDiscountValue(parseParam(request.getParameter("discountValue"), 0));
        dto.setMinOrderAmount(parseParam(request.getParameter("minOrderAmount"), 0));
        
        // 최대 할인 금액(maxDiscountAmount) null 처리
        if (dto.getDiscountType() == 0) {
            dto.setMaxDiscountAmount(null);
        } else {
            dto.setMaxDiscountAmount(parseParam(request.getParameter("maxDiscountAmount"), 0));
        }
        
        dto.setValidFrom(request.getParameter("validFrom"));
        dto.setValidUntil(request.getParameter("validUntil"));
        
        return dto;
    }

    /**
     * 안전한 숫자 파싱 (NumberFormatException 방어)
     */
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