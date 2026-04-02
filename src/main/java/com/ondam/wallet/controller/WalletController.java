package com.ondam.wallet.controller;

import com.ondam.common.controller.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class WalletController implements Controller {

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
		default:
			return "redirect:/wallet";
		}
	}
	
	private String manage(HttpServletRequest request, HttpServletResponse response) {
		return "wallet/wallet-manage";
	}
	
	private String history(HttpServletRequest request, HttpServletResponse response) {
		return "wallet/wallet-history";
	}
	
	private String charge(HttpServletRequest request, HttpServletResponse response) {
		return "wallet/wallet-charge";
	}
	
	private String withdraw(HttpServletRequest request, HttpServletResponse response) {
		return "wallet/wallet-withdraw";
	}
}