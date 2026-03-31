package com.ondam.admin.controller;

import com.ondam.admin.dto.AdminDTO;
import com.ondam.admin.service.AdminService;
import com.ondam.common.controller.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminController implements Controller {

	private AdminService adminService = new AdminService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String action = request.getParameter("action");
		if (action == null)
			action = "view"; // 기본값: 내 정보 보기

		switch (action) {
		case "view":
			return view(request, response);
		case "update":
			return update(request, response);
		case "delete":
            return delete(request, response);
		default:
			return "redirect:/main";
		}
	}

	// 1. 관리자 자기 정보 조회 (getAdminById 활용)
	private String view(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		// 세션에 저장된 로그인 아이디를 가져온다고 가정합니다 (예: "adminId")
		String loginId = (String) session.getAttribute("adminId");

		if (loginId == null) {
			return "redirect:/login"; // 로그인 안 되어 있으면 로그인 페이지로
		}

		// Service -> DAO (getAdminById) 호출
		AdminDTO admin = adminService.getAdminInfo(loginId);
		request.setAttribute("admin", admin);

		return "admin/adminDetail"; // /WEB-INF/views/admin/adminDetail.jsp
	}

	// 2. 관리자 자기 정보 수정
	private String update(HttpServletRequest request, HttpServletResponse response) {
		// JSP 폼에서 넘어온 데이터 받기
		int adminNo = Integer.parseInt(request.getParameter("adminNo"));
		String adminName = request.getParameter("adminName");
		String adminPwd = request.getParameter("adminPwd");

		AdminDTO dto = new AdminDTO();
		dto.setAdminName(adminName);
		dto.setAdminPwd(adminPwd); // Service에서 BCrypt 암호화 수행

		boolean result = adminService.modifyAdmin(dto, adminNo);

		if (result) {
			// 수정 성공 시 다시 조회 페이지로
			return "redirect:/admin?action=view";
		} else {
			return "redirect:/main"; // 실패 시 예외 처리
		}
	}

	// 3. 관리자 삭제
	private String delete(HttpServletRequest request, HttpServletResponse response) {
		int adminNo = Integer.parseInt(request.getParameter("adminNo"));
		adminService.removeAdmin(adminNo);

		return "redirect:/admin?action=list";
	}
}