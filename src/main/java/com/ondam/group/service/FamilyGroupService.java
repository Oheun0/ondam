package com.ondam.group.service;

import java.util.Vector;
import com.ondam.group.dao.FamilyGroupDAO;
import com.ondam.group.dto.FamilyGroupDTO;

public class FamilyGroupService {

    private FamilyGroupDAO dao;

    public FamilyGroupService() {
        this.dao = new FamilyGroupDAO();
    }

    public Vector<FamilyGroupDTO> getFamilyGroupList() {
        return dao.getFamilyGroup();
    }
    
    public FamilyGroupDTO getFamilyGroupByNo(int familyNo) {
        return dao.getFamilyGroupByNo(familyNo);
    }

    public boolean createFamilyGroup(FamilyGroupDTO dto) {
        return dao.insertFamilyGroup(dto);
    }
    
    public int createFamilyGroupAndGetNo(FamilyGroupDTO dto) {
        return dao.insertFamilyGroupAndGetNo(dto);
    }

    public boolean modifyFamilyGroup(FamilyGroupDTO dto, int familyNo) {
        return dao.updateFamilyGroup(dto, familyNo);
    }

    public boolean removeFamilyGroup(int familyNo) {
        return dao.deleteFamilyGroup(familyNo);
    }
}