package com.ondam.poke.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.ondam.cart.dto.CartItemDTO;
import com.ondam.common.controller.Controller;
import com.ondam.notification.dto.NotificationDTO;
import com.ondam.notification.service.NotificationService;
import com.ondam.poke.dto.PokeDTO;
import com.ondam.poke.service.PokeService;
import com.ondam.product.dto.ProductDTO;
import com.ondam.product.dto.ProductImageDTO;
import com.ondam.product.dto.ProductOptionDTO;
import com.ondam.product.service.ProductImageService;
import com.ondam.product.service.ProductOptionService;
import com.ondam.product.service.ProductService;
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
		case "giftOrder":
		    return giftOrder(request, response);
		default:
			return "redirect:/poke";
		}
	}

	// 1. 받은 조르기 목록
	private String list(HttpServletRequest request, HttpServletResponse response) {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");

	    String fromNoParam = request.getParameter("fromNo");
	    String pokeNoParam  = request.getParameter("pokeNo");

	    // pokeNo로 진입 시 → 해당 poke의 senderNo를 fromNo로 사용
	    if (fromNoParam == null && pokeNoParam != null && !pokeNoParam.trim().isEmpty()) {
	        PokeDTO poke = pokeService.getPokeById(Integer.parseInt(pokeNoParam));
	        if (poke != null && poke.getReceiverNo() == loginUser.getUserNo()) {
	            fromNoParam = String.valueOf(poke.getSenderNo());  // senderNo로 필터
	        }
	    }

	    Vector<PokeDTO> receivedList;
	    if (fromNoParam != null && !fromNoParam.trim().isEmpty()) {
	        int fromNo = Integer.parseInt(fromNoParam);
	        receivedList = pokeService.getPokesFromSender(loginUser.getUserNo(), fromNo);
	        request.setAttribute("fromNo", fromNo);
	    } else {
	        receivedList = pokeService.getReceivedPokeList(loginUser.getUserNo());
	    }

	    // 상품 정보 Map
	    ProductService productService = new ProductService();
	    Map<Integer, ProductDTO> productMap = new HashMap<>();
	    Map<Integer, String> imageMap = new HashMap<>();

	    for (PokeDTO poke : receivedList) {
	        int pNo = poke.getProductNo();
	        if (!productMap.containsKey(pNo)) {
	            ProductDTO product = productService.getProductById(pNo);
	            if (product != null) productMap.put(pNo, product);
	            String img = productService.getProductImage(pNo);
	            imageMap.put(pNo, img != null ? img : "");
	        }
	    }

	    request.setAttribute("receivedList", receivedList);
	    request.setAttribute("productMap", productMap);
	    request.setAttribute("imageMap", imageMap);
	    return "poke/poke-list";
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
		String productOptionNoParam = request.getParameter("productOptionNo");
		String pokeQuantityParam = request.getParameter("pokeQuantity");

		// 필수값 검증
		if (isEmpty(productNoParam) || isEmpty(receiverNoParam)) {
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
		dto.setFamilyNo(isEmpty(familyNoParam) ? 0 : Integer.parseInt(familyNoParam));
		dto.setPokeMsg(pokeMsg != null ? pokeMsg : "");
		dto.setSendState(0); // 대기중
		dto.setSendDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());
		dto.setConnectedOrderNo(null); // 아직 주문 없음

		int productOptionNo = (productOptionNoParam != null && !productOptionNoParam.isEmpty()) ? Integer.parseInt(productOptionNoParam) : 0;
		int pokeQuantity = (pokeQuantityParam != null && !pokeQuantityParam.isEmpty()) ? Integer.parseInt(pokeQuantityParam) : 1; // 수량 기본값 1
		
		dto.setProductOptionNo(isEmpty(productOptionNoParam) ? 0 : Integer.parseInt(productOptionNoParam));
	    dto.setPokeQuantity(isEmpty(pokeQuantityParam) ? 1 : Integer.parseInt(pokeQuantityParam));
		
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

		return "redirect:/main";
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
	
	private String giftOrder(HttpServletRequest request, HttpServletResponse response) {
	    UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
	    String[] pokeNos = request.getParameterValues("pokeNo");
	    if (pokeNos == null || pokeNos.length == 0) return "redirect:/poke";

	    ProductService productService = new ProductService();
	    ProductOptionService optionService = new ProductOptionService();
	    ProductImageService imageService = new ProductImageService();

	    Vector<CartItemDTO> orderItems = new Vector<>();
	    int receiverNo = -1;

	    for (String pokeNoStr : pokeNos) {
	        PokeDTO poke = pokeService.getPokeById(Integer.parseInt(pokeNoStr));
	        if (poke == null || poke.getReceiverNo() != loginUser.getUserNo()) continue;
	        if (poke.getSendState() != 0) continue;

	        if (receiverNo == -1) receiverNo = poke.getSenderNo();

	        ProductDTO product = productService.getProductById(poke.getProductNo());
	        ProductOptionDTO option = optionService.getProductOptionByNo(poke.getProductOptionNo());
	        ProductImageDTO image = imageService.getProductImageById(poke.getProductNo());
	        if (product == null) continue;

	        CartItemDTO item = new CartItemDTO();
	        item.setProductNo(poke.getProductNo());
	        item.setProductOptionNo(poke.getProductOptionNo());
	        item.setProductName(product.getProductName());
	        item.setProductOriginPrice(product.getProductOriginPrice());
	        item.setCartQuantity(poke.getPokeQuantity());
	        if (image != null) item.setProductImg(image.getImgFile());
	        if (option != null) {
	            item.setOptionColor(option.getOptionColor());
	            item.setOptionSize(option.getOptionSize());
	            item.setProductPrice(product.getProductPrice() + option.getOptionAddPrice());
	        } else {
	            item.setProductPrice(product.getProductPrice());
	            item.setOptionColor("기본");
	            item.setOptionSize("N/A");
	        }
	        orderItems.add(item);
	    }

	    if (orderItems.isEmpty() || receiverNo == -1) return "redirect:/poke";

	    request.getSession().setAttribute("pokeOrderItems",    orderItems);
	    request.getSession().setAttribute("pokeOrderReceiver", receiverNo);
	    request.getSession().setAttribute("pendingPokeNos",    String.join(",", pokeNos));

	    return "redirect:/payment?buyType=poke&isGift=true&receiverNo=" + receiverNo;
	}
	
	// null, 공백 검증
	private boolean isEmpty(String val) {
	    return val == null || val.trim().isEmpty();
	}
}