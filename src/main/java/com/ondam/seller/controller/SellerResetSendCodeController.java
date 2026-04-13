package com.ondam.seller.controller;

import java.security.SecureRandom;

import com.ondam.common.controller.Controller;
import com.ondam.common.mail.SmtpMailSender;
import com.ondam.seller.service.SellerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * POST /seller/auth/reset-password/send-code — 가입 이메일로 인증코드 발송 (세션에 저장)
 */
public class SellerResetSendCodeController implements Controller {

	private static final int CODE_TTL_MS = 10 * 60 * 1000;
	private static final long RESEND_COOLDOWN_MS = 60_000L;

	static final String SESS_CODE = "sellerPwResetCode";
	static final String SESS_EXPIRY = "sellerPwResetCodeExpiry";
	static final String SESS_SELLER_ID = "sellerPwResetSellerId";
	static final String SESS_EMAIL = "sellerPwResetEmail";
	static final String SESS_LAST_SEND = "sellerPwResetLastSendMs";

	private final SellerService sellerService = new SellerService();
	private final SecureRandom random = new SecureRandom();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");

		try {
			if (!"POST".equalsIgnoreCase(request.getMethod())) {
				response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				writeJson(response, false, "POST만 허용됩니다.");
				return null;
			}

			String sellerId = trim(request.getParameter("sellerId"));
			String sellerEmail = trim(request.getParameter("sellerEmail"));

			if (sellerId.isEmpty() || sellerEmail.isEmpty()) {
				writeJson(response, false, "아이디와 이메일을 입력해 주세요.");
				return null;
			}

			if (!sellerService.verifySellerForReset(sellerId, sellerEmail)) {
				writeJson(response, false, "가입된 정보가 없거나 이메일이 일치하지 않습니다.");
				return null;
			}

			HttpSession session = request.getSession();
			Long last = (Long) session.getAttribute(SESS_LAST_SEND);
			long now = System.currentTimeMillis();
			if (last != null && now - last < RESEND_COOLDOWN_MS) {
				int waitSec = (int) Math.ceil((RESEND_COOLDOWN_MS - (now - last)) / 1000.0);
				writeJson(response, false, waitSec + "초 후에 다시 요청할 수 있어요.");
				return null;
			}

			String code = String.format("%06d", random.nextInt(1_000_000));
			session.setAttribute(SESS_CODE, code);
			session.setAttribute(SESS_EXPIRY, now + CODE_TTL_MS);
			session.setAttribute(SESS_SELLER_ID, sellerId);
			session.setAttribute(SESS_EMAIL, sellerEmail);
			session.setAttribute(SESS_LAST_SEND, now);

			try {
				String html = ""
						+ "<p>온담 파트너 비밀번호 재설정 인증코드입니다.</p>"
						+ "<p style=\"font-size:22px;font-weight:bold;letter-spacing:4px;\">" + code + "</p>"
						+ "<p>코드는 10분간 유효합니다. 본인이 요청하지 않았다면 이 메일을 무시해 주세요.</p>";
				SmtpMailSender.sendHtml(request.getServletContext(), sellerEmail, "[온담 파트너] 비밀번호 재설정 인증코드", html);
			} catch (Exception e) {
				e.printStackTrace();
				session.removeAttribute(SESS_CODE);
				session.removeAttribute(SESS_EXPIRY);
				session.removeAttribute(SESS_SELLER_ID);
				session.removeAttribute(SESS_EMAIL);
				session.removeAttribute(SESS_LAST_SEND);
				String msg = e.getMessage();
				if (msg != null && msg.contains("메일 발송 설정이 없습니다")) {
					writeJson(response, false,
							"메일 계정이 설정되지 않았습니다. WEB-INF/web.xml의 mailSmtpUser·mailSmtpPassword를 확인하세요.");
				} else {
					writeJson(response, false, "메일 발송에 실패했습니다. 앱 비밀번호·네트워크를 확인해 주세요.");
				}
				return null;
			}

			writeJson(response, true, "인증코드를 이메일로 보냈어요.");
			return null;
		} catch (Throwable t) {
			t.printStackTrace();
			writeJson(response, false, "서버 오류가 났습니다. WEB-INF/lib에 메일 JAR(angus-mail 등)이 있는지 확인하세요.");
			return null;
		}
	}

	private static String trim(String s) {
		return s == null ? "" : s.trim();
	}

	private static void writeJson(HttpServletResponse response, boolean ok, String message) throws Exception {
		response.getWriter().write("{\"ok\":" + ok + ",\"message\":\"" + jsonEscape(message) + "\"}");
	}

	private static String jsonEscape(String s) {
		if (s == null) {
			return "";
		}
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\\':
				b.append("\\\\");
				break;
			case '"':
				b.append("\\\"");
				break;
			case '\n':
				b.append("\\n");
				break;
			case '\r':
				b.append("\\r");
				break;
			default:
				b.append(c);
			}
		}
		return b.toString();
	}
}
