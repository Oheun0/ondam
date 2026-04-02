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
    
    // 뷰(JSP) 경로의 앞부분과 기본 리다이렉트 주소를 상수로 지정
    private static final String VIEW_PREFIX = "gift/"; 
    private static final String REDIRECT_RECEIVED = "redirect:/gift?action=received";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // 1. 로그인 확인 (네가 작성한 완벽한 뼈대!)
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }
    	
        // 2. 현재 로그인한 사용자의 고유 번호(userNo) 추출
        int userNo = loginUser.getUserNo(); 
        
        // 3. action 파라미터 확인 (어떤 페이지를 띄워야 할지, 어떤 작업을 할지 결정)
        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            action = "received"; // 파라미터가 없으면 '받은 선물함'을 기본값
        }

        // 4. 라우팅 (action 값에 따라 각자 담당하는 도우미 메서드로 토스)
        switch (action.trim()) {
            case "received":
                return handleReceivedList(request, userNo);
            case "sent":
                return handleSentList(request, userNo);
            case "detail":
                return handleDetail(request, userNo);
            case "sendProc":
                return handleSendGift(request, userNo);
            case "accept":
                return handleAccept(request, userNo);
            case "reject":
                return handleReject(request, userNo);
            default:
                return REDIRECT_RECEIVED;
        }
    }

    // ==========================================
    // [Handler Methods] 기능별 세부 처리 로직
    // ==========================================

    // 1. 내가 받은 선물함 보기
    private String handleReceivedList(HttpServletRequest request, int userNo) {
        Vector<GiftDTO> receivedList = giftService.getMyReceivedGifts(userNo);
        request.setAttribute("giftList", receivedList);
        request.setAttribute("listType", "received"); // 화면(JSP)에서 받은/보낸 탭 구분용 데이터
        return VIEW_PREFIX + "giftList"; // => /WEB-INF/views/gift/giftList.jsp
    }

    // 2. 내가 보낸 선물함 보기
    private String handleSentList(HttpServletRequest request, int userNo) {
        Vector<GiftDTO> sentList = giftService.getMySentGifts(userNo);
        request.setAttribute("giftList", sentList);
        request.setAttribute("listType", "sent");
        return VIEW_PREFIX + "giftList"; 
    }

    // 3. 선물 상세 보기
    private String handleDetail(HttpServletRequest request, int userNo) {
        int giftNo = parseParam(request.getParameter("giftNo"), -1);
        if (giftNo == -1) return REDIRECT_RECEIVED;

        GiftDTO gift = giftService.getGiftById(giftNo);
        
        // 임의접근 방지
        if (gift == null || (gift.getSenderNo() != userNo && gift.getReceiverNo() != userNo)) {
            return REDIRECT_RECEIVED;
        }

        request.setAttribute("gift", gift);
        return VIEW_PREFIX + "giftDetail";
    }

    // 4. 선물 보내기 처리 (주문 및 결제 완료 직후에 호출된다고 가정)
    private String handleSendGift(HttpServletRequest request, int senderNo) {
        GiftDTO dto = new GiftDTO();
        dto.setOrderNo(parseParam(request.getParameter("orderNo"), -1));
        dto.setSenderNo(senderNo); // 보내는 사람은 무조건 현재 세션의 로그인한 사용자
        dto.setReceiverNo(parseParam(request.getParameter("receiverNo"), -1));
        dto.setGiftMsg(request.getParameter("giftMsg"));

        boolean isSuccess = giftService.createGift(dto);
        
        if (isSuccess) {
            return "redirect:/gift?action=sent"; // 성공하면 '보낸 선물함'으로 이동
        } else {
            return "redirect:/error?msg=gift_failed"; // 실패 시 에러 페이지 (프로젝트 상황에 맞게 변경 가능)
        }
    }

    // 5. 선물 수락 처리
    private String handleAccept(HttpServletRequest request, int userNo) {
        int giftNo = parseParam(request.getParameter("giftNo"), -1);
        
        GiftDTO gift = giftService.getGiftById(giftNo);
        // 내가 '받은' 선물이고, 아직 수락/거절을 안 한 '대기 상태(0)'일 때만 수락 가능
        if (gift != null && gift.getReceiverNo() == userNo && gift.getGiftState() == 0) {
            giftService.acceptGift(giftNo);
        }
        return "redirect:/gift?action=detail&giftNo=" + giftNo; // 수락 후 다시 상세 페이지로 돌아감
    }

    // 6. 선물 거절 처리
    private String handleReject(HttpServletRequest request, int userNo) {
        int giftNo = parseParam(request.getParameter("giftNo"), -1);
        
        GiftDTO gift = giftService.getGiftById(giftNo);
        // 내가 '받은' 선물이고, '대기 상태(0)'일 때만 거절 가능
        if (gift != null && gift.getReceiverNo() == userNo && gift.getGiftState() == 0) {
            giftService.rejectGift(giftNo);
        }
        return "redirect:/gift?action=detail&giftNo=" + giftNo;
    }

    // ==========================================
    // [Utility Methods]
    // ==========================================

    // 파라미터를 받을 때 null이나 빈 문자열 에러를 방지해 주는 안전한 변환 도우미 메서드
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