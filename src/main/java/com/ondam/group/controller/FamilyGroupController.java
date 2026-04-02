package com.ondam.group.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.group.dto.FamilyGroupDTO;
import com.ondam.group.dto.FamilyMemberDTO;
import com.ondam.group.service.FamilyGroupService;
import com.ondam.group.service.FamilyMemberService;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class FamilyGroupController implements Controller {

	private FamilyGroupService familyGroupService = new FamilyGroupService();
	private FamilyMemberService familyMemberService = new FamilyMemberService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}

		String action = request.getParameter("action");
		if (action == null)
			action = "list";

		switch (action) {
		case "list":
			return list(request, response);
		case "create":
			return create(request, response);
		case "delete":
			return delete(request, response);
		case "memberDelete":
			return memberDelete(request, response);
		default:
			return "redirect:/family";
		}
	}

	// ──────────────────────────────────────────────
	// 1. 내 가족 그룹 조회
	// ──────────────────────────────────────────────
	private String list(HttpServletRequest request, HttpServletResponse response) {
		UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
		int userNo = loginUser.getUserNo();

		FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(userNo);

		if (myMember == null) {
			// 아직 그룹에 속하지 않은 상태
			request.setAttribute("myGroup", null);
			request.setAttribute("memberList", null);
			return "group/family";
		}

		FamilyGroupDTO myGroup = familyGroupService.getFamilyGroupByNo(myMember.getFamilyNo());
		Vector<FamilyMemberDTO> memberList = familyMemberService.getFamilyMembersByFamilyNo(myMember.getFamilyNo());

		request.setAttribute("myGroup", myGroup);
		request.setAttribute("myMember", myMember); // familyAuth 노출 여부 제어용
		request.setAttribute("memberList", memberList);

		return "group/family";
	}

	// ──────────────────────────────────────────────
	// 2. 가족 그룹 생성 → 생성자를 관리자(auth=1)로 자동 등록
	// ──────────────────────────────────────────────
	private String create(HttpServletRequest request, HttpServletResponse response) {
		UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
		int userNo = loginUser.getUserNo();

		String familyName = request.getParameter("familyName");
		if (familyName == null || familyName.trim().isEmpty()) {
			request.setAttribute("errorMsg", "가족 그룹 이름을 입력해주세요.");
			return list(request, response);
		}

		// 그룹 생성 + 생성된 familyNo 반환
		FamilyGroupDTO groupDto = new FamilyGroupDTO();
		groupDto.setFamilyName(familyName);
		groupDto.setFamilyDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());

		int newFamilyNo = familyGroupService.createFamilyGroupAndGetNo(groupDto);

		if (newFamilyNo == -1) {
			request.setAttribute("errorMsg", "가족 그룹 생성에 실패했습니다.");
			return list(request, response);
		}

		// 생성자를 관리자(familyAuth=1)로 FamilyMember에 등록
		FamilyMemberDTO memberDto = new FamilyMemberDTO();
		memberDto.setFamilyNo(newFamilyNo);
		memberDto.setUserNo(userNo);
		memberDto.setFamilyAuth(1); // 1: 관리자
		familyMemberService.createFamilyMember(memberDto);

		return "redirect:/family";
	}

	// 3. 가족 그룹 삭제 (관리자 버튼에서만 호출 → 권한 분기 불필요)
	private String delete(HttpServletRequest request, HttpServletResponse response) {
		String familyNoParam = request.getParameter("familyNo");
		if (familyNoParam == null)
			return "redirect:/family";

		familyGroupService.removeFamilyGroup(Integer.parseInt(familyNoParam));
		// ON DELETE CASCADE → FamilyMember, FamilyInvite, Poke, Wallet 자동 삭제

		return "redirect:/family";
	}

	// 4. 멤버 퇴장
//        시나리오 A - 관리자가 타인 강제 퇴장 (관리자 버튼에서만 호출)
//        시나리오 B - 관리자 본인 탈퇴 시 관리자 위임 or 그룹 삭제
//        시나리오 C - 일반 멤버 본인 탈퇴 (그냥 삭제)
	private String memberDelete(HttpServletRequest request, HttpServletResponse response) {
		UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
		int myUserNo = loginUser.getUserNo();

		String familyMemberNoParam = request.getParameter("familyMemberNo");
		if (familyMemberNoParam == null)
			return "redirect:/family";
		int targetMemberNo = Integer.parseInt(familyMemberNoParam);

		FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(myUserNo);
		if (myMember == null)
			return "redirect:/family";

		boolean isSelfLeave = (myMember.getFamilyMemberNo() == targetMemberNo);
		boolean iAmAdmin = (myMember.getFamilyAuth() == 1);

		// 시나리오 B: 관리자가 본인 탈퇴
		if (isSelfLeave && iAmAdmin) {
			Vector<FamilyMemberDTO> others = new Vector<>();
			for (FamilyMemberDTO m : familyMemberService.getFamilyMembersByFamilyNo(myMember.getFamilyNo())) {
				if (m.getFamilyMemberNo() != targetMemberNo)
					others.add(m);
			}

			if (others.isEmpty()) {
				// 혼자 남은 경우 → 그룹 자체 삭제
				familyGroupService.removeFamilyGroup(myMember.getFamilyNo());
				return "redirect:/family";
			}

			// 다른 멤버 있으면 → 가장 먼저 가입한 멤버에게 관리자 위임
			FamilyMemberDTO nextAdmin = others.firstElement();
			for (FamilyMemberDTO m : others) {
				if (m.getFamilyMemberNo() < nextAdmin.getFamilyMemberNo())
					nextAdmin = m;
			}
			familyMemberService.changeFamilyAuth(nextAdmin.getFamilyMemberNo(), 1);
		}

		// 시나리오 A, C: 퇴장 처리
		familyMemberService.removeFamilyMember(targetMemberNo);

		return "redirect:/family";
	}
}