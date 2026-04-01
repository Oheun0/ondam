package com.ondam.group.service;

import java.util.Vector;

import com.ondam.group.dao.FamilyMemberDAO;
import com.ondam.group.dto.FamilyMemberDTO;

public class FamilyMemberService {

	private FamilyMemberDAO dao;

	public FamilyMemberService() {
		this.dao = new FamilyMemberDAO();
	}

	public FamilyMemberDTO getFamilyMemberByUserNo(int userNo) {
	    return dao.getFamilyMemberByUserNo(userNo);
	}
	
	public Vector<FamilyMemberDTO> getFamilyMembersByFamilyNo(int familyNo) {
	    return dao.getFamilyMembersByFamilyNo(familyNo);
	}

	public boolean createFamilyMember(FamilyMemberDTO dto) {
		return dao.insertFamilyMember(dto);
	}

	public boolean modifyFamilyMember(FamilyMemberDTO dto, int familyMemberNo) {
		return dao.updateFamilyMember(dto, familyMemberNo);
	}
	
	public boolean changeFamilyAuth(int familyMemberNo, int familyAuth) {
	    return dao.updateFamilyAuth(familyMemberNo, familyAuth);
	}

	public boolean removeFamilyMember(int familyMemberNo) {
		return dao.deleteFamilyMember(familyMemberNo);
	}
}

