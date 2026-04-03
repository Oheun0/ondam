package com.ondam.wallet.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.group.dto.FamilyMemberDTO;
import com.ondam.group.service.FamilyMemberService;
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
		case "manage":
			return manage(request, response);
		case "history":
			return history(request, response);
		case "charge":
			return charge(request, response);
		case "withdraw":
			return withdraw(request, response);
		case "chargeSubmit":
		    return chargeSubmit(request, response);
		case "withdrawSubmit":
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
	    tx.setTransactionType(0); // 0: 충전
	    tx.setAmount(amount);
	    tx.setBalanceSnapshot(newBalance);
	    tx.setTransactionDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	    walletTransactionService.createWalletTransaction(tx);

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
	    tx.setTransactionType(2); // 2: 잔액 꺼내기
	    tx.setAmount(amount);
	    tx.setBalanceSnapshot(newBalance);
	    tx.setTransactionDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	    walletTransactionService.createWalletTransaction(tx);

	    return "redirect:/wallet?action=manage";
	}
}