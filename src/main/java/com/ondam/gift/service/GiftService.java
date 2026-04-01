package com.ondam.gift.service;

import java.util.Vector;

import com.ondam.gift.dao.GiftDAO;
import com.ondam.gift.dto.GiftDTO;

public class GiftService {

	private final GiftDAO dao;

	public GiftService() {
		this.dao = new GiftDAO();
	}

	// [조회] 관리자용 전체 선물 목록
	public Vector<GiftDTO> getGiftList() {
		return dao.getGift();
	}

	// [조회] 특정 선물 단건 조회
	public GiftDTO getGiftById(int giftNo) {
		return dao.getGiftById(giftNo);
	}

	// [조회] 주문 번호로 선물 정보 조회
	public GiftDTO getGiftInfoByOrder(int orderNo) {
		return dao.getGiftByOrderNo(orderNo);
	}

	// [조회] 내가 받은 선물 목록
	public Vector<GiftDTO> getMyReceivedGifts(int userNo) {
		return dao.getReceivedGifts(userNo);
	}

	// [조회] 내가 보낸 선물 목록
	public Vector<GiftDTO> getMySentGifts(int userNo) {
		return dao.getSentGifts(userNo);
	}

	// [생성] 선물 보내기 로직 (유효성 검증 포함)
	public boolean createGift(GiftDTO dto) {
		// 1. 자기 자신에게 선물 보내기 방지
		if (dto.getSenderNo() == dto.getReceiverNo()) {
			System.out.println("[GiftService] 에러: 자기 자신에게는 선물할 수 없습니다.");
			return false;
		}
		
		// 2. 1주문 1선물 제약조건 사전 검사 (동일 주문 번호 존재 여부 확인)
		if (dao.getGiftByOrderNo(dto.getOrderNo()) != null) {
			System.out.println("[GiftService] 에러: 해당 주문 번호로 이미 선물이 존재합니다.");
			return false;
		}
		
		return dao.insertGift(dto);
	}

	// [상태 변경] 선물 수락 (상태값: 1)
	public boolean acceptGift(int giftNo) {
		return dao.updateGiftState(giftNo, 1);
	}

	// [상태 변경] 선물 거절 (상태값: 2)
	public boolean rejectGift(int giftNo) {
		return dao.updateGiftState(giftNo, 2);
	}

	// [상태 변경] 선물 기한 만료 처리 (상태값: 3)
	public boolean expireGift(int giftNo) {
		return dao.updateGiftState(giftNo, 3);
	}

	// [수정] 전체 내용 수정 (관리자용)
	public boolean modifyGift(GiftDTO dto, int giftNo) {
		return dao.updateGift(dto, giftNo);
	}

	// [삭제] 선물 삭제
	public boolean removeGift(int giftNo) {
		return dao.deleteGift(giftNo);
	}
}