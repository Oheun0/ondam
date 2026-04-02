package com.ondam.group.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.group.dto.FamilyGroupDTO;
import com.ondam.group.dto.FamilyMemberDTO;
import com.ondam.group.service.FamilyGroupService;
import com.ondam.group.service.FamilyMemberService;
import com.ondam.user.dto.UserDTO;
import com.ondam.wallet.dto.WalletDTO;
import com.ondam.wallet.service.WalletService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class FamilyGroupController implements Controller {

	private FamilyGroupService familyGroupService = new FamilyGroupService();
	private FamilyMemberService familyMemberService = new FamilyMemberService();
	private WalletService walletService = new WalletService();

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
		case "invite":
			return invite(request, response);
		case "join":
			return "group/group-join";
		case "joinSubmit":
			return joinSubmit(request, response);
		default:
			return "redirect:/group";
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
			return "group/group-empty";
		}

		FamilyGroupDTO myGroup = familyGroupService.getFamilyGroupByNo(myMember.getFamilyNo());
		Vector<FamilyMemberDTO> memberList = familyMemberService.getFamilyMembersByFamilyNo(myMember.getFamilyNo());

		request.setAttribute("myGroup", myGroup);
		request.setAttribute("myMember", myMember); // familyAuth 노출 여부 제어용
		request.setAttribute("memberList", memberList);

		return "group/group";
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

		return "redirect:/group";
	}

	// 3. 가족 그룹 삭제 (관리자 버튼에서만 호출 → 권한 분기 불필요)
	private String delete(HttpServletRequest request, HttpServletResponse response) {
		String familyNoParam = request.getParameter("familyNo");
		if (familyNoParam == null)
			return "redirect:/group";

		familyGroupService.removeFamilyGroup(Integer.parseInt(familyNoParam));
		// ON DELETE CASCADE → FamilyMember, FamilyInvite, Poke, Wallet 자동 삭제

		return "redirect:/group";
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
			return "redirect:/group";
		int targetMemberNo = Integer.parseInt(familyMemberNoParam);

		FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(myUserNo);
		if (myMember == null)
			return "redirect:/group";

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
				return "redirect:/group";
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

		return "redirect:/group";
	}

	// 초대 코드 페이지
	private String invite(HttpServletRequest request, HttpServletResponse response) {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
	    FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());

	    if (myMember == null) {
	        // 1. 초대 코드 먼저 생성
	        String inviteCode = generateInviteCode();

	        // 2. 그룹 INSERT (inviteCode 포함)
	        FamilyGroupDTO groupDto = new FamilyGroupDTO();
	        groupDto.setFamilyName("temp");
	        groupDto.setFamilyInviteCode(inviteCode); // ← 여기서 같이 넣음
	        groupDto.setFamilyDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());

	        int newFamilyNo = familyGroupService.createFamilyGroupAndGetNo(groupDto);
	        if (newFamilyNo == -1) return "redirect:/group";

	        // 3. familyName을 PK값으로 UPDATE
	        FamilyGroupDTO updateDto = new FamilyGroupDTO();
	        updateDto.setFamilyName(String.valueOf(newFamilyNo));
	        updateDto.setFamilyInviteCode(inviteCode);
	        updateDto.setFamilyDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	        familyGroupService.modifyFamilyGroup(updateDto, newFamilyNo);

	        // 4. 관리자(auth=1)로 멤버 등록
	        FamilyMemberDTO memberDto = new FamilyMemberDTO();
	        memberDto.setFamilyNo(newFamilyNo);
	        memberDto.setUserNo(loginUser.getUserNo());
	        memberDto.setFamilyAuth(1);
	        memberDto.setUserName(loginUser.getUserName());
	        familyMemberService.createFamilyMember(memberDto);
	        
	        // ── 그룹 고유 지갑 자동 생성 ──────────────────
	        WalletDTO walletDto = new WalletDTO();
	        walletDto.setFamilyNo(newFamilyNo);
	        walletDto.setBalance(0);
	        walletDto.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	        walletService.createWallet(walletDto);

	        FamilyGroupDTO myGroup = familyGroupService.getFamilyGroupByNo(newFamilyNo);
	        request.setAttribute("myGroup", myGroup);

	    } else {
	        FamilyGroupDTO myGroup = familyGroupService.getFamilyGroupByNo(myMember.getFamilyNo());
	        request.setAttribute("myGroup", myGroup);
	    }

	    return "group/group-invite";
	}

	// 초대 코드 생성 (XXXX-XXXX 형식)
	private String generateInviteCode() {
	    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    java.util.Random random = new java.util.Random();
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < 4; i++) sb.append(chars.charAt(random.nextInt(chars.length())));
	    sb.append("-");
	    for (int i = 0; i < 4; i++) sb.append(chars.charAt(random.nextInt(chars.length())));
	    return sb.toString();
	}
	
	private String joinSubmit(HttpServletRequest request, HttpServletResponse response) {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

	    // 이미 그룹에 속해있으면 차단(방어 코드, 일어날 일 없을듯)
	    FamilyMemberDTO already = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
	    if (already != null) {
	        request.setAttribute("errorMsg", "이미 그룹에 속해있어요.");
	        return "group/group-join";
	    }

	    String inviteCode = request.getParameter("inviteCode");
	    if (inviteCode == null || inviteCode.trim().isEmpty()) {
	        request.setAttribute("errorMsg", "초대 코드를 입력해주세요.");
	        return "group/group-join";
	    }

	    // 초대 코드로 그룹 조회
	    FamilyGroupDTO targetGroup = familyGroupService.getFamilyGroupByInviteCode(inviteCode.trim());
	    if (targetGroup == null) {
	        request.setAttribute("errorMsg", "유효하지 않은 초대 코드예요.");
	        return "group/group-join";
	    }

	    // 일반 멤버(auth=0)로 등록
	    FamilyMemberDTO memberDto = new FamilyMemberDTO();
	    memberDto.setFamilyNo(targetGroup.getFamilyNo());
	    memberDto.setUserNo(loginUser.getUserNo());
	    memberDto.setFamilyAuth(0); // 0: 일반 멤버
	    memberDto.setUserName(loginUser.getUserName());
	    familyMemberService.createFamilyMember(memberDto);

	    return "redirect:/group";
	}
}