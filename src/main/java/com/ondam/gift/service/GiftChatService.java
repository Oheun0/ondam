package com.ondam.gift.service;

import java.util.Vector;

import com.ondam.gift.dao.GiftChatDAO;
import com.ondam.gift.dto.GiftChatDTO;
import com.ondam.user.dao.UserDAO;

public class GiftChatService {

    private final GiftChatDAO dao;
    private final UserDAO userDao;

    // 선물카드 이미지 목록 (8장)
    private static final String[] GIFT_CARD_IMAGES = {
        "gift-card1.png",
        "gift-card2.png",
        "gift-card3.png",
        "gift-card4.png",
        "gift-card5.png",
        "gift-card6.png",
        "gift-card7.png",
        "gift-card8.png"
    };

    // 감사카드 이미지 목록 (2장)
    private static final String[] THANKS_CARD_IMAGES = {
        "thanks_card_01.png",
        "thanks_card_02.png"
    };

    public GiftChatService() {
        this.dao = new GiftChatDAO();
        this.userDao = new UserDAO();
    }

    // [생성] 선물 보낼 때 선물카드 랜덤 배정 후 INSERT
    // GiftController의 handleSendGift에서 gift INSERT 직후 호출
    public boolean createGiftCard(int giftNo, int senderNo, int receiverNo) {
        String cardImg = GIFT_CARD_IMAGES[(int) (Math.random() * GIFT_CARD_IMAGES.length)];

        GiftChatDTO dto = new GiftChatDTO();
        dto.setGiftNo(giftNo);
        dto.setSenderNo(senderNo);
        dto.setReceiverNo(receiverNo);
        dto.setChatType(0);
        dto.setCardImg(cardImg);

        return dao.insertGiftChat(dto);
    }

    // [생성] 고마움 표시하기 클릭 시 감사카드 랜덤 배정 후 INSERT
    // 이미 감사카드가 존재하면 false 반환 (중복 방지)
    public boolean createThanksCard(int giftNo, int senderNo, int receiverNo) {
        // 중복 방지: 이미 감사카드 보낸 경우 차단
        if (dao.existsThanksCard(giftNo)) {
            System.out.println("[GiftChatService] 이미 감사카드를 보낸 선물입니다. giftNo=" + giftNo);
            return false;
        }

        String cardImg = THANKS_CARD_IMAGES[(int) (Math.random() * THANKS_CARD_IMAGES.length)];

        GiftChatDTO dto = new GiftChatDTO();
        dto.setGiftNo(giftNo);
        dto.setSenderNo(senderNo);
        dto.setReceiverNo(receiverNo);
        dto.setChatType(1);
        dto.setCardImg(cardImg);

        return dao.insertGiftChat(dto);
    }

    // [조회] A↔B 대화방 전체 채팅 목록 (gift-chat.jsp 메인 조회)
    // senderName, receiverName 부가 정보도 채워서 반환
    public Vector<GiftChatDTO> getChatListBetween(int myNo, int otherNo) {
        Vector<GiftChatDTO> list = dao.getChatListBetween(myNo, otherNo);

        for (GiftChatDTO chat : list) {
            chat.setSenderName(userDao.getUserName(chat.getSenderNo()));
            chat.setReceiverName(userDao.getUserName(chat.getReceiverNo()));
        }
        return list;
    }

    // [조회] 특정 선물의 채팅 목록 (단일 선물 기준 조회가 필요할 때)
    public Vector<GiftChatDTO> getChatListByGiftNo(int giftNo) {
        Vector<GiftChatDTO> list = dao.getChatListByGiftNo(giftNo);

        for (GiftChatDTO chat : list) {
            chat.setSenderName(userDao.getUserName(chat.getSenderNo()));
            chat.setReceiverName(userDao.getUserName(chat.getReceiverNo()));
        }
        return list;
    }

    // [조회] 감사카드 존재 여부 확인 (JSP 버튼 활성/비활성 판단용)
    public boolean hasThanksCard(int giftNo) {
        return dao.existsThanksCard(giftNo);
    }

    // [삭제] 관리자용
    public boolean removeGiftChat(int chatNo) {
        return dao.deleteGiftChat(chatNo);
    }
}