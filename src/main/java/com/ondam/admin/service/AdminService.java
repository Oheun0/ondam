package com.ondam.admin.service;

import java.util.Vector;

import org.mindrot.jbcrypt.BCrypt;

import com.ondam.admin.dao.AdminDAO;
import com.ondam.admin.dto.AdminDTO;

public class AdminService {

	private AdminDAO dao;

	public AdminService() {
		this.dao = new AdminDAO();
	}
	
	public AdminDTO getAdminInfo(String adminId) {
	    return dao.getAdminById(adminId);
	}

	public boolean loginAdmin(String adminId, String inputPwd) {
         AdminDTO admin = dao.getAdminById(adminId);
         if (admin != null) {
             // BCrypt.checkpw(평문, DB에저장된암호문)
             return BCrypt.checkpw(inputPwd, admin.getAdminPwd());
         }
        return false;
    }

	public boolean createAdmin(AdminDTO dto) {
        // 1. 사용자가 입력한 평문 비밀번호 가져오기
        String rawPassword = dto.getAdminPwd();
        
        // 2. BCrypt로 해싱 (Salt는 내부에서 자동으로 생성됨)
        String encodedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        
        // 3. DTO에 암호화된 비밀번호로 덮어쓰기
        dto.setAdminPwd(encodedPassword);
        
        // 4. DB에는 이제 암호문이 저장됨
        return dao.insertAdmin(dto);
    }

	public boolean modifyAdmin(AdminDTO dto, int adminNo) {
        // 수정 시에도 비밀번호가 변경되었다면 암호화 로직 사용
        if (dto.getAdminPwd() != null && !dto.getAdminPwd().isEmpty()) {
            dto.setAdminPwd(BCrypt.hashpw(dto.getAdminPwd(), BCrypt.gensalt()));
        }
        return dao.updateAdmin(dto, adminNo);
    }

	public boolean removeAdmin(int adminNo) {
		return dao.deleteAdmin(adminNo);
	}
}