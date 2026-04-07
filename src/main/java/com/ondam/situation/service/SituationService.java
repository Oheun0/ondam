package com.ondam.situation.service;

import java.util.Vector;

import com.ondam.situation.dao.SituationDAO;
import com.ondam.situation.dto.SituationDTO;

public class SituationService {

	private SituationDAO dao;

	public SituationService() {
		this.dao = new SituationDAO();
	}

	public Vector<SituationDTO> getSituationList() {
		return dao.getSituation();
	}

	public boolean createSituation(SituationDTO dto) {
		return dao.insertSituation(dto);
	}

	public boolean modifySituation(SituationDTO dto, int situationNo) {
		return dao.updateSituation(dto, situationNo);
	}

	public boolean removeSituation(int situationNo) {
		return dao.deleteSituation(situationNo);
	}
}