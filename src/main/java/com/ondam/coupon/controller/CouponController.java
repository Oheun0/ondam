package com.ondam.coupon.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.coupon.dto.CouponDTO;
import com.ondam.coupon.service.CouponService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CouponController implements Controller {

    private final CouponService couponService = new CouponService();;
    
    private static final String VIEW_PREFIX = "admin/coupon/";
    private static final String REDIRECT_LIST = "redirect:/coupon?action=list";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        if (!isAdminAuthorized(request)) {
            return "redirect:/main";
        }

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
                return REDIRECT_LIST;
        }
    }

    // ==========================================
    // [Handler Methods]
    // ==========================================

    private String handleList(HttpServletRequest request) {
        Vector<CouponDTO> couponList = couponService.getCouponList();
        request.setAttribute("couponList", couponList);
        return VIEW_PREFIX + "list";
    }

    private String handleCreate(HttpServletRequest request) {
        CouponDTO dto = extractCouponDto(request);
        // [수정] 무결성 검증은 Service가 담당하므로 무조건 생성 요청을 보냅니다.
        couponService.createCoupon(dto);
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
        
        if (couponNo != -1) {
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
    // [Utility Methods]
    // ==========================================

    private String getAction(HttpServletRequest request) {
        String action = request.getParameter("action");
        return (action == null || action.trim().isEmpty()) ? "list" : action.trim();
    }

    private boolean isAdminAuthorized(HttpServletRequest request) {
        HttpSession session = request.getSession();
        // TODO: 관리자 세션 구조 확정 시 수정
        return true; 
    }

    private CouponDTO extractCouponDto(HttpServletRequest request) {
        CouponDTO dto = new CouponDTO();
        dto.setCouponName(request.getParameter("couponName"));
        dto.setDiscountType(parseParam(request.getParameter("discountType"), 0));
        dto.setDiscountValue(parseParam(request.getParameter("discountValue"), 0));
        dto.setMinOrderAmount(parseParam(request.getParameter("minOrderAmount"), 0));
        
        if (dto.getDiscountType() == 0) {
            dto.setMaxDiscountAmount(null);
        } else {
            dto.setMaxDiscountAmount(parseParam(request.getParameter("maxDiscountAmount"), 0));
        }
        
        dto.setValidFrom(request.getParameter("validFrom"));
        dto.setValidUntil(request.getParameter("validUntil"));
        
        return dto;
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