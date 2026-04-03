package com.ondam.group.service;

import java.util.Vector;

import com.ondam.group.dao.FamilyHelpDAO;
import com.ondam.group.dto.FamilyHelpDTO;

public class FamilyHelpService {

	private FamilyHelpDAO dao;

	public FamilyHelpService() {
		this.dao = new FamilyHelpDAO();
	}

	public Vector<FamilyHelpDTO> getFamilyHelpList() {
		return dao.getFamilyHelpList();
	}

	public boolean createFamilyHelp(FamilyHelpDTO dto) {
		return dao.insertFamilyHelp(dto);
	}

	public boolean modifyFamilyHelp(FamilyHelpDTO dto, int familyHelpNo) {
		return dao.updateFamilyHelp(dto, familyHelpNo);
	}

	public boolean removeFamilyHelp(int familyHelpNo) {
		return dao.deleteFamilyHelp(familyHelpNo);
	}
	
	public Vector<Integer> getHelpeeUserNosByHelper(int helperUserNo, int familyNo) {
	    return dao.getHelpeeUserNosByHelper(helperUserNo, familyNo);
	}

	public boolean removeHelpByHelperAndHelpee(int helperUserNo, int helpeeUserNo) {
	    return dao.deleteByHelperAndHelpee(helperUserNo, helpeeUserNo);
	}
}