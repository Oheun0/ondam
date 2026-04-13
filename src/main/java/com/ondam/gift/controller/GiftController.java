package com.ondam.gift.controller;

import java.util.List;
import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.gift.dto.GiftChatDTO;
import com.ondam.gift.dto.GiftDTO;
import com.ondam.gift.service.GiftChatService;
import com.ondam.gift.service.GiftService;
import com.ondam.notification.dto.NotificationDTO;
import com.ondam.notification.service.NotificationService;
import com.ondam.orders.service.OrdersService;
import com.ondam.poke.service.PokeService;
import com.ondam.user.dao.UserAddressDAO;
import com.ondam.user.dao.UserDAO;
import com.ondam.user.dto.UserAddressDTO;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GiftController implements Controller {

    private final GiftService giftService = new GiftService();
    private final GiftChatService giftChatService = new GiftChatService();
    private final UserDAO userDao = new UserDAO();
    private final OrdersService ordersService = new OrdersService();
    private final NotificationService notificationService = new NotificationService();

    private static final String VIEW_PREFIX = "gift/";
    private static final String REDIRECT_MAIN = "redirect:/gift";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }

        int userNo = loginUser.getUserNo();

        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            action = "main";
        }

        switch (action.trim()) {
            case "main":
            case "received":
            case "sent":
                return handleGiftMain(request, userNo);
            case "chat":
                return handleChat(request, userNo);
            case "detail":
                return handleDetail(request, userNo);
            case "sendProc":
                return handleSendGift(request, userNo);
            case "accept":
                return handleAccept(request, userNo);
            case "reject":
                return handleReject(request, userNo);
            case "thanks":
                return handleThanks(request, userNo);
            default:
                return REDIRECT_MAIN;
        }
    }

    // -------------------------
    // [기존] 선물함 메인
    // -------------------------
    private String handleGiftMain(HttpServletRequest request, int userNo) {
        UserAddressDAO addressDAO = new UserAddressDAO();
        List<UserAddressDTO> addressList = addressDAO.getAddressListByUser(userNo);

        Vector<GiftDTO> receivedList = giftService.getMyReceivedGifts(userNo);
        Vector<GiftDTO> sentList = giftService.getMySentGifts(userNo);

        request.setAttribute("addressList", addressList);
        request.setAttribute("receivedList", receivedList);
        request.setAttribute("sentList", sentList);

        return VIEW_PREFIX + "gift-box";
    }

    // -------------------------
    // gift-chat.jsp 진입
    // -------------------------
    private String handleChat(HttpServletRequest request, int myUserNo) {
        String otherNoParam = request.getParameter("receiverNo");
        if (otherNoParam == null) return REDIRECT_MAIN;

        int otherNo = parseParam(otherNoParam, -1);
        if (otherNo == -1) return REDIRECT_MAIN;

        // 상대방 이름
        String otherName = userDao.getUserName(otherNo);

        // A↔B 전체 채팅 목록 (선물카드 + 감사카드 sentAt ASC)
        Vector<GiftChatDTO> chatList = giftChatService.getChatListBetween(myUserNo, otherNo);

        // gift 정보도 같이 넘겨야 선물 상품명/이미지/상태 렌더링 가능
        java.util.Map<Integer, GiftDTO> giftMap = new java.util.HashMap<>();
        for (GiftChatDTO chat : chatList) {
            int giftNo = chat.getGiftNo();
            if (!giftMap.containsKey(giftNo)) {
                GiftDTO gift = giftService.getGiftById(giftNo);
                if (gift != null) {
                    giftMap.put(giftNo, gift);
                }
            }
        }

        // JSP에서 ${thanksMap[gift.giftNo] != null} 로 고마움 버튼 활성/비활성 분기
        java.util.Map<Integer, GiftChatDTO> thanksMap = new java.util.HashMap<>();
        for (GiftChatDTO chat : chatList) {
            if (chat.getChatType() == 1) {
                thanksMap.put(chat.getGiftNo(), chat);
            }
        }

        request.setAttribute("otherNo", otherNo);
        request.setAttribute("otherName", otherName);
        request.setAttribute("chatList", chatList);
        request.setAttribute("giftMap", giftMap);
        request.setAttribute("thanksMap", thanksMap);
        request.setAttribute("myUserNo", myUserNo);

        return VIEW_PREFIX + "gift-chat";
    }

    // -------------------------
    // [추가] 고마움 표시하기
    // -------------------------
    private String handleThanks(HttpServletRequest request, int myUserNo) {
        int giftNo  = parseParam(request.getParameter("giftNo"), -1);
        int otherNo = parseParam(request.getParameter("otherNo"), -1);

        if (giftNo == -1 || otherNo == -1) {
            return REDIRECT_MAIN;
        }

        // 내가 receiverNo인 선물인지 권한 체크
        GiftDTO gift = giftService.getGiftById(giftNo);
        if (gift == null || gift.getReceiverNo() != myUserNo) {
            return REDIRECT_MAIN;
        }

        // 감사카드 INSERT (중복이면 service에서 false 반환 후 그냥 redirect)
        giftChatService.createThanksCard(giftNo, myUserNo, otherNo);

        return "redirect:/gift?action=chat&receiverNo=" + otherNo;
    }

    // -------------------------
    // 선물 보내기
    // giftChat INSERT 추가됨
    // -------------------------
    private String handleSendGift(HttpServletRequest request, int senderNo) {
        String orderNoStr    = request.getParameter("orderNo");
        String receiverNoStr = request.getParameter("receiverNo");
        String pokeNoStr     = request.getParameter("pokeNo");

        // receiverNo 없으면 처리 불가
        if (orderNoStr == null || receiverNoStr == null) {
            return "redirect:/main";
        }

        int orderNo    = Integer.parseInt(orderNoStr);
        int receiverNo = Integer.parseInt(receiverNoStr);

        // ── 1. orders 테이블 giftReceiverNo 업데이트 ──
        ordersService.updateGiftReceiverNo(orderNo, receiverNo);

        // ── 2. gift 테이블 INSERT ──
        GiftDTO dto = new GiftDTO();
        dto.setOrderNo(orderNo);
        dto.setSenderNo(senderNo);
        dto.setReceiverNo(receiverNo);
        dto.setGiftState(0);  // 0: 수락 대기
        giftService.createGift(dto);
        
     // ── 3. giftChat INSERT (선물 카드 버블) ──
        GiftDTO insertedGift = giftService.getGiftInfoByOrder(orderNo);
        if (insertedGift != null) {
            giftChatService.createGiftCard(insertedGift.getGiftNo(), senderNo, receiverNo);
        }
        
        
        PokeService pokeService = new PokeService();
        
     // 조르기에서 온 선물이면 sendState 수락됨(1)으로 업데이트
        if (pokeNoStr != null && !pokeNoStr.isEmpty()) {
            pokeService.updateSendState(Integer.parseInt(pokeNoStr), 1);
        }
        
        String pendingPokeNos = (String) request.getSession().getAttribute("pendingPokeNos");
        if (pendingPokeNos != null && !pendingPokeNos.isEmpty()) {
            for (String pNo : pendingPokeNos.split(",")) {
                if (!pNo.trim().isEmpty()) {
                    pokeService.removePoke(Integer.parseInt(pNo.trim()));
                }
            }
            request.getSession().removeAttribute("pendingPokeNos");
        }

        // ── 3. 받는 사람에게 알림 발송 ──
        NotificationDTO noti = new NotificationDTO();
        noti.setUserNo(receiverNo);
        noti.setNotificationContent("새로운 선물이 도착했어요! 선물함을 확인해보세요 🎁");
        noti.setNotificationType(6);
        noti.setRefNo(orderNo);
        noti.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toString());
        notificationService.createNotification(noti);

        return "redirect:/gift?action=sent";
    }

    // -------------------------
    // 기존 핸들러 (변경 없음)
    // -------------------------
    private String handleAccept(HttpServletRequest request, int userNo) {
        int giftNo    = parseParam(request.getParameter("giftNo"), -1);
        int addressNo = parseParam(request.getParameter("addressNo"), -1);

        if (addressNo <= 0) return REDIRECT_MAIN;

        GiftDTO gift = giftService.getGiftById(giftNo);
        if (gift != null && gift.getReceiverNo() == userNo && gift.getGiftState() == 0) {
            giftService.acceptGift(giftNo, addressNo);
        }
        return REDIRECT_MAIN;
    }

    private String handleReject(HttpServletRequest request, int userNo) {
        int giftNo = parseParam(request.getParameter("giftNo"), -1);
        GiftDTO gift = giftService.getGiftById(giftNo);

        if (gift != null && gift.getReceiverNo() == userNo && gift.getGiftState() == 0) {
            giftService.rejectGift(giftNo);
        }
        return REDIRECT_MAIN;
    }

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

    // -------------------------
    // [유틸리티]
    // -------------------------
    private int parseParam(String param, int defaultValue) {
        if (param == null || param.trim().isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(param.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}