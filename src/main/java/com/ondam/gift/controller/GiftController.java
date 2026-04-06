package com.ondam.gift.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.gift.dto.GiftDTO;
import com.ondam.gift.service.GiftService;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GiftController implements Controller {
    
    private final GiftService giftService = new GiftService();
    
    // [수정됨] DispatcherServlet에서 "/WEB-INF/views/"와 ".jsp"를 자동으로 붙여주므로 "gift/"만 명시합니다.
    private static final String VIEW_PREFIX = "gift/"; 
    private static final String REDIRECT_MAIN = "redirect:/gift";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // 1. 로그인 확인
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }
    	
        int userNo = loginUser.getUserNo(); 
        
        // 2. action 파라미터 확인 (없으면 main으로 간주)
        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            action = "main";
        }

        // 3. 라우팅
        switch (action.trim()) {
            case "main":
            case "received": // 기존 탭 링크와의 호환성을 위해 received, sent도 main으로 보냅니다.
            case "sent":
                return handleGiftMain(request, userNo);
            case "detail":
                return handleDetail(request, userNo);
            case "sendProc":
                return handleSendGift(request, userNo);
            case "accept":
                return handleAccept(request, userNo);
            case "reject":
                return handleReject(request, userNo);
            default:
                return REDIRECT_MAIN;
        }
    }

    // ==========================================
    // [Handler Methods]
    // ==========================================

    // [핵심 변경] 하나의 페이지에 두 개의 탭이 있으므로, 두 데이터를 한 번에 담아서 보냅니다.
    private String handleGiftMain(HttpServletRequest request, int userNo) {
        Vector<GiftDTO> receivedList = giftService.getMyReceivedGifts(userNo);
        Vector<GiftDTO> sentList = giftService.getMySentGifts(userNo);
        
        request.setAttribute("receivedList", receivedList);
        request.setAttribute("sentList", sentList);
        
        // 최종 경로: /WEB-INF/views/gift/gift-box.jsp
        return VIEW_PREFIX + "gift-box"; 
    }

    // 선물 상세 보기
    private String handleDetail(HttpServletRequest request, int userNo) {
        int giftNo = parseParam(request.getParameter("giftNo"), -1);
        if (giftNo == -1) return REDIRECT_MAIN;

        GiftDTO gift = giftService.getGiftById(giftNo);
        
        if (gift == null || (gift.getSenderNo() != userNo && gift.getReceiverNo() != userNo)) {
            return REDIRECT_MAIN;
        }

        request.setAttribute("gift", gift);
        return VIEW_PREFIX + "giftDetail";
    }

    // 선물 보내기 처리
    private String handleSendGift(HttpServletRequest request, int senderNo) {
        GiftDTO dto = new GiftDTO();
        dto.setOrderNo(parseParam(request.getParameter("orderNo"), -1));
        dto.setSenderNo(senderNo); 
        dto.setReceiverNo(parseParam(request.getParameter("receiverNo"), -1));
        dto.setGiftMsg(request.getParameter("giftMsg"));

        boolean isSuccess = giftService.createGift(dto);
        
        if (isSuccess) {
            return REDIRECT_MAIN; 
        } else {
            return "redirect:/error?msg=gift_failed"; 
        }
    }

    // 선물 수락 처리
    private String handleAccept(HttpServletRequest request, int userNo) {
        int giftNo = parseParam(request.getParameter("giftNo"), -1);
        GiftDTO gift = giftService.getGiftById(giftNo);
        
        if (gift != null && gift.getReceiverNo() == userNo && gift.getGiftState() == 0) {
            giftService.acceptGift(giftNo);
        }
        return "redirect:/gift"; 
    }

    // 선물 거절 처리
    private String handleReject(HttpServletRequest request, int userNo) {
        int giftNo = parseParam(request.getParameter("giftNo"), -1);
        GiftDTO gift = giftService.getGiftById(giftNo);
        
        if (gift != null && gift.getReceiverNo() == userNo && gift.getGiftState() == 0) {
            giftService.rejectGift(giftNo);
        }
        return "redirect:/gift";
    }

    // ==========================================
    // [Utility Methods]
    // ==========================================
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