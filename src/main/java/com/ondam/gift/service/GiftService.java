package com.ondam.gift.service;

import java.util.Vector;

import com.ondam.gift.dao.GiftDAO;
import com.ondam.gift.dto.GiftDTO;

public class GiftService {

	private GiftDAO dao;

	public GiftService() {
		this.dao = new GiftDAO();
	}

	public Vector<GiftDTO> getGiftList() {
		return dao.getGift();
	}

	public boolean createGift(GiftDTO dto) {
		return dao.insertGift(dto);
	}

	public boolean modifyGift(GiftDTO dto, int giftNo) {
		return dao.updateGift(dto, giftNo);
	}

	public boolean removeGift(int giftNo) {
		return dao.deleteGift(giftNo);
	}
}

