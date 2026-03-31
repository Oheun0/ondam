package com.ondam.shorts.service;

import java.io.File;
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

	// 기존 DB만 삭제하는 기본 메서드
	public boolean removeShorts(int shortsNo) {
		return dao.deleteShorts(shortsNo);
	}
	
	// [추가] 공개(1) <-> 비공개(2) 상태를 토글하는 메서드
	public boolean toggleVisibility(int productNo) {
	    ShortsDTO dto = dao.getShortByProductNo(productNo);
	    if (dto == null) return false;
	    
	    int newState = (dto.getShortsState() == 1) ? 2 : 1;
	    return dao.updateShortsState(productNo, newState);
	}

	// [추가] DB 기록과 함께 물리적 파일(MP4, JPG)까지 삭제하는 메서드
	public boolean removeShortsWithFiles(int productNo, String realPath) {
	    ShortsDTO target = dao.getShortByProductNo(productNo);
	    if (target != null) {
	        String path = realPath + "uploads" + File.separator + "shorts" + File.separator;
	        
	        if (target.getVideoFile() != null) {
	            new File(path + target.getVideoFile()).delete();
	        }
	        if (target.getThumbnailImg() != null) {
	            new File(path + target.getThumbnailImg()).delete();
	        }
	        return dao.deleteShorts(target.getShortsNo());
	    }
	    return false;
	}
}