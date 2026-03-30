package com.ondam.shorts.service;

import java.util.Vector;

import com.ondam.shorts.dao.ShortsDAO;
import com.ondam.shorts.dto.ShortsDTO;

public class ShortsService {

	private ShortsDAO dao;

	public ShortsService() {
		this.dao = new ShortsDAO();
	}

	public Vector<ShortsDTO> getShortsList() {
		return dao.getShorts();
	}

	public boolean createShorts(ShortsDTO dto) {
		return dao.insertShorts(dto);
	}

	public boolean modifyShorts(ShortsDTO dto, int shortsNo) {
		return dao.updateShorts(dto, shortsNo);
	}

	public boolean removeShorts(int shortsNo) {
		return dao.deleteShorts(shortsNo);
	}
}

