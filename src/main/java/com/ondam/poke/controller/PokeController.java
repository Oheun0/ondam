package com.ondam.poke.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.notification.dto.NotificationDTO;
import com.ondam.notification.service.NotificationService;
import com.ondam.poke.dto.PokeDTO;
import com.ondam.poke.service.PokeService;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PokeController implements Controller {

	private PokeService pokeService = new PokeService();

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
		case "list":
			return list(request, response);
		case "sent":
			return sent(request, response);
		case "send":
			return send(request, response);
		case "respond":
			return respond(request, response);
		case "cancel":
			return cancel(request, response);
		case "detail":
			return detail(request, response);
		default:
			return "redirect:/poke";
		}
	}

	// 1. 받은 조르기 목록
	private String list(HttpServletRequest request, HttpServletResponse response) {
		UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
		Vector<PokeDTO> receivedList = pokeService.getReceivedPokeList(loginUser.getUserNo());
		request.setAttribute("receivedList", receivedList);
		return "poke/list";
	}

	// 2. 보낸 조르기 목록
	private String sent(HttpServletRequest request, HttpServletResponse response) {
		UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
		Vector<PokeDTO> sentList = pokeService.getSentPokeList(loginUser.getUserNo());
		request.setAttribute("sentList", sentList);
		return "poke/sent";
	}

	// 3. 조르기 보내기 (상품 상세 페이지 폼에서 POST)
	private String send(HttpServletRequest request, HttpServletResponse response) {
		UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

		String productNoParam = request.getParameter("productNo");
		String receiverNoParam = request.getParameter("receiverNo");
		String familyNoParam = request.getParameter("familyNo");
		String pokeMsg = request.getParameter("pokeMsg");

		// 필수값 검증
		if (productNoParam == null || receiverNoParam == null || familyNoParam == null) {
			return "redirect:/product";
		}

		// 자기 자신에게 조르기 방지
		int receiverNo = Integer.parseInt(receiverNoParam);
		if (receiverNo == loginUser.getUserNo()) {
			return "redirect:/product?action=detail&productNo=" + productNoParam;
		}

		PokeDTO dto = new PokeDTO();
		dto.setProductNo(Integer.parseInt(productNoParam));
		dto.setSenderNo(loginUser.getUserNo());
		dto.setReceiverNo(receiverNo);
		dto.setFamilyNo(Integer.parseInt(familyNoParam));
		dto.setPokeMsg(pokeMsg != null ? pokeMsg : "");
		dto.setSendState(0); // 대기중
		dto.setSendDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());
		dto.setConnectedOrderNo(null); // 아직 주문 없음

		// pokeNo 받아오기
		int newPokeNo = pokeService.createPokeAndGetNo(dto);

		// 알림 생성
		if (newPokeNo != -1) {
			NotificationService notificationService = new NotificationService();
			NotificationDTO noti = new NotificationDTO();
			noti.setUserNo(receiverNo);
			noti.setNotificationType(1); // 조르기
			noti.setNotificationContent(loginUser.getUserName() + " 님이 조르기 요청을 보냈어요!");
			noti.setIsRead(0);
			noti.setRefNo(newPokeNo); // pokeNo 세팅
			noti.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toString());
			notificationService.createNotification(noti);
		}

		return "redirect:/poke";
	}

	// 4. 수락 / 거절 (receiverNo가 처리)
	private String respond(HttpServletRequest request, HttpServletResponse response) {
		UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

		String pokeNoParam = request.getParameter("pokeNo");
		String respondAction = request.getParameter("respondAction"); // "accept" or "reject"

		if (pokeNoParam == null || respondAction == null)
			return "redirect:/poke";

		int pokeNo = Integer.parseInt(pokeNoParam);
		PokeDTO poke = pokeService.getPokeById(pokeNo);

		if (poke == null)
			return "redirect:/poke";

		// 본인이 receiverNo인지 권한 체크
		if (poke.getReceiverNo() != loginUser.getUserNo()) {
			return "redirect:/poke";
		}

		// sendState: 1=수락, 2=거절
		int newState = "accept".equals(respondAction) ? 1 : 2;
		pokeService.updateSendState(pokeNo, newState);

		// 수락 시 → 장바구니 자동 담기 or 바로 주문 페이지로
		// TODO : 수락이면 CartController로 redirect하거나 상품 상세로 이동 (아직 안 됨)
		if (newState == 1) {
			return "redirect:/product?action=detail&productNo=" + poke.getProductNo();
		}

		return "redirect:/poke";
	}

	// 5. 조르기 취소 (senderNo가 삭제)
	private String cancel(HttpServletRequest request, HttpServletResponse response) {
		UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

		String pokeNoParam = request.getParameter("pokeNo");
		if (pokeNoParam == null)
			return "redirect:/poke";

		int pokeNo = Integer.parseInt(pokeNoParam);
		PokeDTO poke = pokeService.getPokeById(pokeNo);

		if (poke == null)
			return "redirect:/poke";

		// 본인이 senderNo인지 권한 체크
		if (poke.getSenderNo() != loginUser.getUserNo()) {
			return "redirect:/poke";
		}

		pokeService.removePoke(pokeNo);
		return "redirect:/poke?action=sent";
	}
	
	// 6. 알림 클릭 → 조르기 상세 (수락/거절 or 결과)
	private String detail(HttpServletRequest request, HttpServletResponse response) {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

	    String pokeNoParam = request.getParameter("pokeNo");
	    if (pokeNoParam == null) return "redirect:/poke";

	    int pokeNo = Integer.parseInt(pokeNoParam);
	    PokeDTO poke = pokeService.getPokeById(pokeNo);

	    if (poke == null) return "redirect:/poke";

	    // 본인이 receiverNo인지 확인
	    if (poke.getReceiverNo() != loginUser.getUserNo()) {
	        return "redirect:/poke";
	    }

	    request.setAttribute("poke", poke);
	    return "poke/detail"; // sendState에 따라 수락/거절 or 결과 표시
	}
}