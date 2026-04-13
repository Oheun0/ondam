package com.ondam.seller.controller;

import com.ondam.common.controller.Controller;
import com.ondam.seller.service.SellerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SellerResetPwController implements Controller {

	private final SellerService sellerService = new SellerService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();

		if (method.equals("GET")) {
			return "seller/auth/reset-password";
		}

		if (method.equals("POST")) {
			String sellerId = trim(request.getParameter("sellerId"));
			String sellerEmail = trim(request.getParameter("sellerEmail"));
			String sellerCode = trim(request.getParameter("sellerCode"));

			if (!sellerService.verifySellerForReset(sellerId, sellerEmail)) {
				request.setAttribute("resetError", "가입된 정보가 없거나 이메일이 일치하지 않습니다.");
				return "seller/auth/reset-password";
			}

			HttpSession session = request.getSession();
			String expectId = (String) session.getAttribute(SellerResetSendCodeController.SESS_SELLER_ID);
			String expectEmail = (String) session.getAttribute(SellerResetSendCodeController.SESS_EMAIL);
			String savedCode = (String) session.getAttribute(SellerResetSendCodeController.SESS_CODE);
			Long expiry = (Long) session.getAttribute(SellerResetSendCodeController.SESS_EXPIRY);

			if (savedCode == null || expiry == null || expectId == null || expectEmail == null) {
				request.setAttribute("resetError", "먼저 인증코드를 받아 주세요.");
				return "seller/auth/reset-password";
			}
			if (System.currentTimeMillis() > expiry.longValue()) {
				clearResetSession(session);
				request.setAttribute("resetError", "인증코드가 만료되었습니다. 다시 받아 주세요.");
				return "seller/auth/reset-password";
			}
			if (!sellerId.equals(expectId) || !sellerEmail.trim().equalsIgnoreCase(expectEmail.trim())) {
				request.setAttribute("resetError", "요청 정보가 일치하지 않습니다. 처음부터 다시 시도해 주세요.");
				return "seller/auth/reset-password";
			}
			if (sellerCode.isEmpty() || !savedCode.equals(sellerCode)) {
				request.setAttribute("resetError", "인증코드가 올바르지 않습니다.");
				return "seller/auth/reset-password";
			}

			clearResetSession(session);
			session.setAttribute("resetTargetId", sellerId);
			return "redirect:/seller/auth/reset-password-form";
		}
		return null;
	}

	private static void clearResetSession(HttpSession session) {
		session.removeAttribute(SellerResetSendCodeController.SESS_CODE);
		session.removeAttribute(SellerResetSendCodeController.SESS_EXPIRY);
		session.removeAttribute(SellerResetSendCodeController.SESS_SELLER_ID);
		session.removeAttribute(SellerResetSendCodeController.SESS_EMAIL);
		session.removeAttribute(SellerResetSendCodeController.SESS_LAST_SEND);
	}

	private static String trim(String s) {
		return s == null ? "" : s.trim();
	}
}
