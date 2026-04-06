package com.ondam.group.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.group.dto.FamilyGroupDTO;
import com.ondam.group.dto.FamilyHelpDTO;
import com.ondam.group.dto.FamilyMemberDTO;
import com.ondam.group.service.FamilyGroupService;
import com.ondam.group.service.FamilyHelpService;
import com.ondam.group.service.FamilyMemberService;
import com.ondam.notification.dto.NotificationDTO;
import com.ondam.notification.service.NotificationService;
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
	private FamilyHelpService familyHelpService = new FamilyHelpService();
	private NotificationService notificationService = new NotificationService();

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
	    case "list":         // 내 사람 그룹 조회
	        return list(request, response);
	    case "delete":       // 그룹 해산
	        return delete(request, response);
	    case "memberDelete": // 멤버 연결 끊기 (강제 퇴장 / 본인 탈퇴)
	        return memberDelete(request, response);
	    case "groupName":    // 그룹명 입력 폼
	        return groupName(request, response);
	    case "invite":       // 초대 코드 발급 및 그룹 생성
	        return invite(request, response);
	    case "join":         // 초대 코드 입력 폼
	        return "group/group-join";
	    case "joinSubmit":   // 초대 코드로 그룹 참여
	        return joinSubmit(request, response);
	    case "manage":       // 멤버 관리 페이지 (권한에 따라 owner/member 분기)
	        return manage(request, response);
	    case "changeOwner":  // 그룹장 위임
	        return changeOwner(request, response);
	    case "helpAdd":      // 도움 주기 등록
	        return helpAdd(request, response);
	    case "helpCancel":   // 도움 주기 취소
	        return helpCancel(request, response);
	    case "updateGroupName": // 그룹명 수정
	        return updateGroupName(request, response);
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

	// 2. 가족 그룹 삭제 (관리자 버튼에서만 호출 → 권한 분기 불필요)
	private String delete(HttpServletRequest request, HttpServletResponse response) {
		String familyNoParam = request.getParameter("familyNo");
		if (familyNoParam == null)
			return "redirect:/group";
		
		int familyNo = Integer.parseInt(familyNoParam);

	    // 삭제 전에 먼저 조회 (CASCADE 전에 데이터 확보)
	    FamilyGroupDTO group = familyGroupService.getFamilyGroupByNo(familyNo);
	    Vector<FamilyMemberDTO> memberList = familyMemberService.getFamilyMembersByFamilyNo(familyNo);

		familyGroupService.removeFamilyGroup(Integer.parseInt(familyNoParam));
		// ON DELETE CASCADE → FamilyMember, FamilyInvite, Poke, Wallet 자동 삭제
		
		// 전체 멤버에게 알림 발송
	    if (group != null && memberList != null) {
	        String content = "\"" + group.getFamilyName() + "\" 그룹이 해산되었어요.";
	        for (FamilyMemberDTO m : memberList) {
	            NotificationDTO notiDto = new NotificationDTO();
	            notiDto.setUserNo(m.getUserNo());
	            notiDto.setNotificationType(0);
	            notiDto.setNotificationContent(content);
	            notiDto.setRefNo(0);
	            notiDto.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	            notificationService.createNotification(notiDto);
	        }
	    }

		return "redirect:/group";
	}

	// 3. 멤버 퇴장
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
	
	private String groupName(HttpServletRequest request, HttpServletResponse response) {
	    String name = request.getParameter("groupName");

	    // POST: 이름만 세션에 저장 후 invite로 이동
	    if (name != null && !name.trim().isEmpty()) {
	        request.getSession().setAttribute("pendingGroupName", name.trim());
	        return "redirect:/group?action=invite";
	    }

	    // GET: 그룹명 입력 폼 표시
	    return "group/group-name";
	}

	// 초대 코드 페이지
	private String invite(HttpServletRequest request, HttpServletResponse response) {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
	    FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());

	    if (myMember == null) {
	        // 세션에서 이름 꺼내기
	        String pendingName = (String) request.getSession().getAttribute("pendingGroupName");
	        if (pendingName == null || pendingName.isEmpty()) {
	            // 이름 없이 들어오면 groupName으로
	            return "redirect:/group?action=groupName";
	        }

	        // 초대 코드 생성
	        String inviteCode = generateInviteCode();

	        // 그룹 생성
	        FamilyGroupDTO groupDto = new FamilyGroupDTO();
	        groupDto.setFamilyName(pendingName);
	        groupDto.setFamilyInviteCode(inviteCode);
	        groupDto.setFamilyDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());

	        int newFamilyNo = familyGroupService.createFamilyGroupAndGetNo(groupDto);
	        if (newFamilyNo == -1) return "redirect:/group";

	        // 그룹장 멤버 등록
	        FamilyMemberDTO memberDto = new FamilyMemberDTO();
	        memberDto.setFamilyNo(newFamilyNo);
	        memberDto.setUserNo(loginUser.getUserNo());
	        memberDto.setFamilyAuth(1);
	        memberDto.setUserName(loginUser.getUserName());
	        familyMemberService.createFamilyMember(memberDto);

	        // 지갑 생성
	        WalletDTO walletDto = new WalletDTO();
	        walletDto.setFamilyNo(newFamilyNo);
	        walletDto.setBalance(0);
	        walletDto.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	        walletService.createWallet(walletDto);
	        
	        // 세션에서 임시 이름 제거
	        request.getSession().removeAttribute("pendingGroupName");

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
	    
	    // 알림 발송
	    Vector<FamilyMemberDTO> memberList = familyMemberService.getFamilyMembersByFamilyNo(targetGroup.getFamilyNo());
	    String content = "\"" + loginUser.getUserName() + "\"님이 \"" + targetGroup.getFamilyName() + "\"에서 함께하게 되었어요!";

	    for (FamilyMemberDTO m : memberList) {
	        if (m.getUserNo() == loginUser.getUserNo()) continue; // 본인 제외

	        NotificationDTO notiDto = new NotificationDTO();
	        notiDto.setUserNo(m.getUserNo());
	        notiDto.setNotificationType(0);
	        notiDto.setNotificationContent(content);
	        notiDto.setRefNo(0);
	        notiDto.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	        notificationService.createNotification(notiDto);
	    }

	    return "redirect:/group";
	}
	
	private String manage(HttpServletRequest request, HttpServletResponse response) {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

	    FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
	    if (myMember == null) return "redirect:/group";

	    FamilyGroupDTO myGroup = familyGroupService.getFamilyGroupByNo(myMember.getFamilyNo());
	    Vector<FamilyMemberDTO> memberList = familyMemberService.getFamilyMembersByFamilyNo(myMember.getFamilyNo());
	    
	    java.util.HashSet<Integer> helpeeSet = new java.util.HashSet<>(
	            familyHelpService.getHelpeeUserNosByHelper(loginUser.getUserNo(), myMember.getFamilyNo())
	        );

	    request.setAttribute("myGroup", myGroup);
	    request.setAttribute("myMember", myMember);
	    request.setAttribute("memberList", memberList);
	    request.setAttribute("helpeeSet", helpeeSet);

	    // 관리자(1) → owner 페이지, 일반(0) → member 페이지
	    return myMember.getFamilyAuth() == 1
	        ? "group/group-manage-owner"
	        : "group/group-manage-member";
	}
	
	private String changeOwner(HttpServletRequest request, HttpServletResponse response) {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

	    String targetMemberNoParam = request.getParameter("familyMemberNo");
	    if (targetMemberNoParam == null) return "redirect:/group";

	    int targetMemberNo = Integer.parseInt(targetMemberNoParam);

	    // 현재 로그인 유저의 멤버 정보 조회
	    FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
	    if (myMember == null || myMember.getFamilyAuth() != 1) return "redirect:/group"; // 관리자 아니면 차단

	    // 대상 멤버를 관리자(1)로 승격
	    familyMemberService.changeFamilyAuth(targetMemberNo, 1);

	    // 나는 일반 멤버(0)로 강등
	    familyMemberService.changeFamilyAuth(myMember.getFamilyMemberNo(), 0);

	    return "redirect:/group";
	}
	
	private String helpAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
	    int helpeeUserNo = Integer.parseInt(request.getParameter("helpeeUserNo"));
	    int familyNo = Integer.parseInt(request.getParameter("familyNo"));

	    FamilyHelpDTO dto = new FamilyHelpDTO();
	    dto.setFamilyNo(familyNo);
	    dto.setHelperUserNo(loginUser.getUserNo());
	    dto.setHelpeeUserNo(helpeeUserNo);
	    familyHelpService.createFamilyHelp(dto);

	    response.setContentType("text/plain;charset=UTF-8");
	    response.getWriter().write("ok");
	    return null;
	}

	private String helpCancel(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
	    int helpeeUserNo = Integer.parseInt(request.getParameter("helpeeUserNo"));

	    familyHelpService.removeHelpByHelperAndHelpee(loginUser.getUserNo(), helpeeUserNo);

	    response.setContentType("text/plain;charset=UTF-8");
	    response.getWriter().write("ok");
	    return null;
	}
	
	private String updateGroupName(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

	    String familyName = request.getParameter("familyName");
	    if (familyName == null || familyName.trim().isEmpty()) {
	        response.setContentType("text/plain;charset=UTF-8");
	        response.getWriter().write("error");
	        return null;
	    }

	    FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
	    if (myMember == null) {
	        response.setContentType("text/plain;charset=UTF-8");
	        response.getWriter().write("error");
	        return null;
	    }

	    familyGroupService.modifyFamilyName(myMember.getFamilyNo(), familyName.trim());

	    response.setContentType("text/plain;charset=UTF-8");
	    response.getWriter().write("ok");
	    return null;
	}
}