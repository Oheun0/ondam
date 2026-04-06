package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.group.dto.FamilyMemberDTO;
import com.ondam.group.service.FamilyMemberService;
import com.ondam.user.dto.UserDTO;
import com.ondam.wallet.dto.WalletDTO;
import com.ondam.wallet.service.WalletService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MyPageController implements Controller {
	
	private FamilyMemberService familyMemberService = new FamilyMemberService();
	private WalletService walletService = new WalletService();
	
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        UserDTO sessionUser = (UserDTO) session.getAttribute("loginUser"); 
        
        // 1. 로그인 체크
        if (sessionUser == null) {
            return "redirect:/login"; 
        }
      
        // 2. 가족 구성원 정보 조회
        FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(sessionUser.getUserNo());
        
        // 3. 가족 가입 여부에 따른 안전한 분기 처리 (Null 체크)
        if (myMember != null) {
            // [가족이 있는 경우]
            // 가족 번호가 존재하므로 안전하게 지갑 정보를 가져옵니다.
            WalletDTO wallet = walletService.getWalletByFamilyNo(myMember.getFamilyNo());
            request.setAttribute("wallet", wallet);
            request.setAttribute("hasFamily", true); // 프론트엔드에 가족이 있음을 알림
            
        } else {
            // [가족이 없는 경우]
            // 지갑 조회를 건너뛰고, 지갑 정보를 null로 명시합니다.
            request.setAttribute("wallet", null);
            request.setAttribute("hasFamily", false); // 프론트엔드에 가족이 없음을 알림
        }
        
        return "mypage/mypage"; 
    }
}