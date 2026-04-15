package com.ondam.situation.service;

import java.util.Vector;

import com.ondam.situation.dao.SituationMappingDAO;
import com.ondam.situation.dto.SituationMappingDTO;

public class SituationMappingService {

	private SituationMappingDAO dao;

	public SituationMappingService() {
		this.dao = new SituationMappingDAO();
	}

	public Vector<SituationMappingDTO> getSituationMappingList() {
		return dao.getSituationMapping();
	}

	public boolean createSituationMapping(SituationMappingDTO dto) {
		return dao.insertSituationMapping(dto);
	}

	public boolean modifySituationMapping(SituationMappingDTO dto, int situationMapNo) {
		return dao.updateSituationMapping(dto, situationMapNo);
	}

	public boolean removeSituationMapping(int situationMapNo) {
		return dao.deleteSituationMapping(situationMapNo);
	}

	public boolean removeByProductNo(int productNo) {
		return dao.deleteByProductNo(productNo);
	}

	public boolean syncSingleMapping(int productNo, int situationNo) {
		return dao.syncSingleMapping(productNo, situationNo);
	}
}