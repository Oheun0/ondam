package com.ondam.wallet.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.group.dto.FamilyMemberDTO;
import com.ondam.group.service.FamilyMemberService;
import com.ondam.notification.dto.NotificationDTO;
import com.ondam.notification.service.NotificationService;
import com.ondam.user.dto.UserDTO;
import com.ondam.wallet.dto.WalletDTO;
import com.ondam.wallet.dto.WalletTransactionDTO;
import com.ondam.wallet.service.WalletService;
import com.ondam.wallet.service.WalletTransactionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class WalletController implements Controller {
	
	private FamilyMemberService familyMemberService = new FamilyMemberService();
	private WalletTransactionService walletTransactionService = new WalletTransactionService();
	private WalletService walletService = new WalletService();
	private NotificationService notificationService = new NotificationService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}

		String action = request.getParameter("action");
		if (action == null)
			action = "manage";

		switch (action) {
	    case "manage":       // 지갑 메인 (잔액 + 최근 거래 내역)
	        return manage(request, response);
	    case "history":      // 전체 거래 내역 조회
	        return history(request, response);
	    case "charge":       // 충전 폼
	        return charge(request, response);
	    case "withdraw":     // 출금 폼
	        return withdraw(request, response);
	    case "chargeSubmit": // 충전 처리
	        return chargeSubmit(request, response);
	    case "withdrawSubmit": // 출금 처리
	        return withdrawSubmit(request, response);
	    default:
	        return "redirect:/wallet";
	}
	}
	
	private String manage(HttpServletRequest request, HttpServletResponse response) {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

	    FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
	    if (myMember == null) {
	        // 그룹 없음 → 지갑 없음
	        request.setAttribute("wallet", null);
	        request.setAttribute("recentList", null);
	        return "wallet/wallet-manage";
	    }

	    WalletDTO wallet = walletService.getWalletByFamilyNo(myMember.getFamilyNo());
	    if (wallet == null) {
	        request.setAttribute("wallet", null);
	        request.setAttribute("recentList", null);
	        return "wallet/wallet-manage";
	    }

	    Vector<WalletTransactionDTO> recentList =
	        walletTransactionService.getRecentTransactions(wallet.getWalletNo());

	    request.setAttribute("wallet", wallet);
	    request.setAttribute("recentList", recentList);
	    return "wallet/wallet-manage";
	}
	
	private String history(HttpServletRequest request, HttpServletResponse response) {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

	    FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
	    if (myMember == null) {
	        request.setAttribute("wallet", null);
	        request.setAttribute("historyList", null);
	        return "wallet/wallet-history";
	    }

	    WalletDTO wallet = walletService.getWalletByFamilyNo(myMember.getFamilyNo());
	    if (wallet == null) {
	        request.setAttribute("wallet", null);
	        request.setAttribute("historyList", null);
	        return "wallet/wallet-history";
	    }

	    Vector<WalletTransactionDTO> historyList =
	        walletTransactionService.getTransactionsByWalletNo(wallet.getWalletNo());

	    request.setAttribute("wallet", wallet);
	    request.setAttribute("historyList", historyList);
	    return "wallet/wallet-history";
	}
	
	private String charge(HttpServletRequest request, HttpServletResponse response) {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

	    FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
	    if (myMember != null) {
	        WalletDTO wallet = walletService.getWalletByFamilyNo(myMember.getFamilyNo());
	        request.setAttribute("wallet", wallet);
	    }

	    return "wallet/wallet-charge";
	}

	private String withdraw(HttpServletRequest request, HttpServletResponse response) {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

	    FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
	    if (myMember != null) {
	        WalletDTO wallet = walletService.getWalletByFamilyNo(myMember.getFamilyNo());
	        request.setAttribute("wallet", wallet);
	    }

	    return "wallet/wallet-withdraw";
	}
	
	private String chargeSubmit(HttpServletRequest request, HttpServletResponse response) {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

	    String amountParam = request.getParameter("amount");
	    if (amountParam == null || amountParam.trim().isEmpty()) {
	        return "redirect:/wallet?action=charge";
	    }

	    int amount;
	    try {
	        amount = Integer.parseInt(amountParam.trim());
	    } catch (NumberFormatException e) {
	        return "redirect:/wallet?action=charge";
	    }

	    if (amount <= 0) return "redirect:/wallet?action=charge";

	    FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
	    if (myMember == null) return "redirect:/wallet?action=charge";

	    WalletDTO wallet = walletService.getWalletByFamilyNo(myMember.getFamilyNo());
	    if (wallet == null) return "redirect:/wallet?action=charge";

	    int newBalance = wallet.getBalance() + amount;

	    // 1. 잔액 업데이트
	    walletService.updateBalance(wallet.getWalletNo(), newBalance);

	    // 2. 거래 내역 INSERT
	    WalletTransactionDTO tx = new WalletTransactionDTO();
	    tx.setWalletNo(wallet.getWalletNo());
	    tx.setUserNo(loginUser.getUserNo());
	    tx.setUserName(loginUser.getUserName());
	    tx.setTransactionType(0); // 0: 충전
	    tx.setAmount(amount);
	    tx.setBalanceSnapshot(newBalance);
	    tx.setTransactionDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	    walletTransactionService.createWalletTransaction(tx);

	    // 3. 그룹 전원에게 충전 알림
	    Vector<FamilyMemberDTO> memberList = familyMemberService.getFamilyMembersByFamilyNo(myMember.getFamilyNo());
	    String content = "\"" + loginUser.getUserName() + "\"님이 함께 지갑에 " + amount + "원을 충전하셨어요!";
	    for (FamilyMemberDTO m : memberList) {
	        NotificationDTO notiDto = new NotificationDTO();
	        notiDto.setUserNo(m.getUserNo());
	        notiDto.setNotificationType(5);
	        notiDto.setNotificationContent(content);
	        notiDto.setRefNo(0);
	        notiDto.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	        notificationService.createNotification(notiDto);
	    }
	    return "redirect:/wallet?action=manage";
	}
	
	private String withdrawSubmit(HttpServletRequest request, HttpServletResponse response) {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

	    String amountParam = request.getParameter("amount");
	    if (amountParam == null || amountParam.trim().isEmpty()) {
	        return "redirect:/wallet?action=withdraw";
	    }

	    int amount;
	    try {
	        amount = Integer.parseInt(amountParam.trim());
	    } catch (NumberFormatException e) {
	        return "redirect:/wallet?action=withdraw";
	    }

	    if (amount <= 0) return "redirect:/wallet?action=withdraw";

	    FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
	    if (myMember == null) return "redirect:/wallet?action=withdraw";

	    WalletDTO wallet = walletService.getWalletByFamilyNo(myMember.getFamilyNo());
	    if (wallet == null) return "redirect:/wallet?action=withdraw";

	    // 잔액 부족 시 차단
	    if (wallet.getBalance() < amount) return "redirect:/wallet?action=withdraw";

	    int newBalance = wallet.getBalance() - amount;

	    // 1. 잔액 차감
	    walletService.updateBalance(wallet.getWalletNo(), newBalance);

	    // 2. 거래 내역 INSERT
	    WalletTransactionDTO tx = new WalletTransactionDTO();
	    tx.setWalletNo(wallet.getWalletNo());
	    tx.setUserNo(loginUser.getUserNo());
	    tx.setUserName(loginUser.getUserName());
	    tx.setTransactionType(2); // 2: 잔액 꺼내기
	    tx.setAmount(amount);
	    tx.setBalanceSnapshot(newBalance);
	    tx.setTransactionDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	    walletTransactionService.createWalletTransaction(tx);

	    // 3. 그룹 전원에게 사용 알림
	    Vector<FamilyMemberDTO> memberList = familyMemberService.getFamilyMembersByFamilyNo(myMember.getFamilyNo());
	    String content = "\"" + loginUser.getUserName() + "\"님이 함께 지갑에서 " + amount + "원을 사용하셨어요!";
	    for (FamilyMemberDTO m : memberList) {
	        NotificationDTO notiDto = new NotificationDTO();
	        notiDto.setUserNo(m.getUserNo());
	        notiDto.setNotificationType(5);
	        notiDto.setNotificationContent(content);
	        notiDto.setRefNo(0);
	        notiDto.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	        notificationService.createNotification(notiDto);
	    }
	    
	    return "redirect:/wallet?action=manage";
	}
}