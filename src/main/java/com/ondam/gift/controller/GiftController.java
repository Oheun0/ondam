package com.ondam.gift.controller;

import java.util.List;
import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.gift.dto.GiftDTO;
import com.ondam.gift.service.GiftService;
import com.ondam.user.dao.UserAddressDAO;
import com.ondam.user.dto.UserAddressDTO;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GiftController implements Controller {
    
    private final GiftService giftService = new GiftService();
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
        
        // 2. action 파라미터 확인
        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            action = "main";
        }

        // 3. 라우팅
        switch (action.trim()) {
            case "main":
            case "received":
            case "sent":
                return handleGiftMain(request, userNo);
            case "detail":
                return handleDetail(request, userNo);
            case "sendProc":
                return handleSendGift(request, userNo);
            case "accept":
                return handleAccept(request, userNo); // request 추가 전달
            case "reject":
                return handleReject(request, userNo);
            default:
                return REDIRECT_MAIN;
        }
    }
    // [Handler Methods]
    // 선물함 메인 (받은/보낸 목록 + 배송지 목록)
    private String handleGiftMain(HttpServletRequest request, int userNo) {
        // 배송지 선택 모달을 위해 주소 목록 조회
        UserAddressDAO addressDAO = new UserAddressDAO();
        List<UserAddressDTO> addressList = addressDAO.getAddressListByUser(userNo);
        
        Vector<GiftDTO> receivedList = giftService.getMyReceivedGifts(userNo);
        Vector<GiftDTO> sentList = giftService.getMySentGifts(userNo);
        
        request.setAttribute("addressList", addressList);
        request.setAttribute("receivedList", receivedList);
        request.setAttribute("sentList", sentList);
        
        return VIEW_PREFIX + "gift-box"; 
    }

    // 선물 수락 처리 (배송지 번호 포함)
    private String handleAccept(HttpServletRequest request, int userNo) {
        int giftNo = parseParam(request.getParameter("giftNo"), -1);
        int addressNo = parseParam(request.getParameter("addressNo"), -1); // 모달에서 선택한 번호
        
        if (addressNo <= 0) {
            return REDIRECT_MAIN;
        }
        
        GiftDTO gift = giftService.getGiftById(giftNo);
        if (gift != null && gift.getReceiverNo() == userNo && gift.getGiftState() == 0) {
            // 서비스에서 수락 상태 업데이트와 배송지 번호 기록을 동시에 처리
            giftService.acceptGift(giftNo, addressNo);
        }
        return REDIRECT_MAIN; 
    }

    // 선물 거절 처리
    private String handleReject(HttpServletRequest request, int userNo) {
        int giftNo = parseParam(request.getParameter("giftNo"), -1);
        GiftDTO gift = giftService.getGiftById(giftNo);
        
        if (gift != null && gift.getReceiverNo() == userNo && gift.getGiftState() == 0) {
            giftService.rejectGift(giftNo);
        }
        return REDIRECT_MAIN;
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

    // 선물 보내기 처리 (결제 후 호출됨)
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


    // [Utility Methods]
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