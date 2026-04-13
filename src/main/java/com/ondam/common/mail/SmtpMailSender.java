package com.ondam.common.mail;

import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletContext;

/**
 * Gmail 등 SMTP 발송. {@code web.xml} {@code context-param}으로 계정을 넣습니다.
 * <ul>
 * <li>{@code mailSmtpHost} — 기본 {@code smtp.gmail.com}</li>
 * <li>{@code mailSmtpPort} — 기본 {@code 587}</li>
 * <li>{@code mailSmtpUser} — Gmail 주소</li>
 * <li>{@code mailSmtpPassword} — Google 앱 비밀번호(16자)</li>
 * <li>{@code mailFrom} — 보낸 사람 표시 주소(보통 {@code mailSmtpUser}와 동일)</li>
 * </ul>
 */
public final class SmtpMailSender {

	private SmtpMailSender() {}

	public static void sendHtml(ServletContext ctx, String toAddress, String subject, String htmlBody) throws Exception {
		String host = param(ctx, "mailSmtpHost", "smtp.gmail.com");
		int port = parsePort(param(ctx, "mailSmtpPort", "587"));
		String user = param(ctx, "mailSmtpUser", null);
		String password = param(ctx, "mailSmtpPassword", null);
		String from = param(ctx, "mailFrom", user);

		if (user == null || user.isBlank() || password == null || password.isBlank()) {
			throw new IllegalStateException("메일 발송 설정이 없습니다. web.xml의 mailSmtpUser, mailSmtpPassword를 확인하세요.");
		}

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", String.valueOf(port));
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", host);

		Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
			@Override
			protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
				return new jakarta.mail.PasswordAuthentication(user.trim(), password.trim());
			}
		});

		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from.trim(), "온담 파트너", "UTF-8"));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress.trim(), false));
		msg.setSubject(subject, "UTF-8");
		msg.setContent(htmlBody, "text/html; charset=UTF-8");

		Transport.send(msg);
	}

	private static String param(ServletContext ctx, String name, String defaultVal) {
		String v = ctx.getInitParameter(name);
		if (v == null) {
			return defaultVal;
		}
		v = v.trim();
		return v.isEmpty() ? defaultVal : v;
	}

	private static int parsePort(String s) {
		try {
			return Integer.parseInt(s.trim());
		} catch (Exception e) {
			return 587;
		}
	}
}
