package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.notification.dto.NotificationDTO;
import com.ondam.notification.service.NotificationService;
import com.ondam.user.dao.UserAddressDAO;
import com.ondam.user.dto.UserAddressDTO;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddressDeleteController implements Controller {

	private NotificationService notificationService = new NotificationService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		if (loginUser == null)
			return "redirect:/login";

		// 배송지 수정 도와주기에서 추가된 코드 시작
		String targetUserNoParam = request.getParameter("targetUserNo");
		int targetUserNo = (targetUserNoParam != null && !targetUserNoParam.isEmpty())
				? Integer.parseInt(targetUserNoParam)
				: loginUser.getUserNo();
		boolean isHelperMode = (targetUserNo != loginUser.getUserNo());

		String addressIdStr = request.getParameter("addressId");

		if (addressIdStr != null && !addressIdStr.isEmpty()) {
			int addressNo = Integer.parseInt(addressIdStr);
			UserAddressDAO dao = new UserAddressDAO();

			UserAddressDTO addr = dao.getAddressByNo(addressNo);

			if (addr != null && addr.getIsDefault() == 1) {
				return isHelperMode ? "redirect:/profile-address?targetUserNo=" + targetUserNo
						: "redirect:/profile-address";
			}
			dao.deleteUserAddress(addressNo);
			if (isHelperMode) {
				NotificationDTO notiDto = new NotificationDTO();
				notiDto.setUserNo(targetUserNo);
				notiDto.setNotificationType(4);
				notiDto.setNotificationContent("\"" + loginUser.getUserName() + "\"님이 배송지를 삭제해주셨어요!");
				notiDto.setRefNo(0);
				notiDto.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toString());
				notificationService.createNotification(notiDto);
			}
		}

		return isHelperMode ? "redirect:/profile-address?targetUserNo=" + targetUserNo : "redirect:/profile-address";
	}
}