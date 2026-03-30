package com.ondam.group.service;

import java.util.Vector;

import com.ondam.group.dao.FamilyInviteDAO;
import com.ondam.group.dto.FamilyInviteDTO;

public class FamilyInviteService {

	private FamilyInviteDAO dao;

	public FamilyInviteService() {
		this.dao = new FamilyInviteDAO();
	}

	public Vector<FamilyInviteDTO> getFamilyInviteList() {
		return dao.getFamilyInvite();
	}

	public boolean createFamilyInvite(FamilyInviteDTO dto) {
		return dao.insertFamilyInvite(dto);
	}

	public boolean modifyFamilyInvite(FamilyInviteDTO dto, int invitationNo) {
		return dao.updateFamilyInvite(dto, invitationNo);
	}

	public boolean removeFamilyInvite(int invitationNo) {
		return dao.deleteFamilyInvite(invitationNo);
	}
}

