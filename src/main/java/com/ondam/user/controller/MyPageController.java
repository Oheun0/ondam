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
        
        if (sessionUser == null) {
            return "redirect:/login"; 
        }
      
        FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(sessionUser.getUserNo());
	    WalletDTO wallet = walletService.getWalletByFamilyNo(myMember.getFamilyNo());
	    request.setAttribute("wallet", wallet);
        
        return "mypage/mypage"; 
    }
}