package com.ondam.admin.service;

import java.util.Vector;

import com.ondam.admin.dao.AdminDAO;
import com.ondam.admin.dto.AdminDTO;

public class AdminService {

	private AdminDAO dao;

	public AdminService() {
		this.dao = new AdminDAO();
	}

	public Vector<AdminDTO> getAdminList() {
		return dao.getAdmin();
	}

	public boolean createAdmin(AdminDTO dto) {
		return dao.insertAdmin(dto);
	}

	public boolean modifyAdmin(AdminDTO dto, int adminNo) {
		return dao.updateAdmin(dto, adminNo);
	}

	public boolean removeAdmin(int adminNo) {
		return dao.deleteAdmin(adminNo);
	}
}

