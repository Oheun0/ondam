package com.ondam.orders.controller;

import java.util.List;
import java.util.Vector;

import com.ondam.cart.dto.CartItemDTO;
import com.ondam.cart.service.CartService;
import com.ondam.common.controller.Controller;
import com.ondam.group.dto.FamilyMemberDTO;
import com.ondam.group.service.FamilyMemberService;
import com.ondam.notification.dto.NotificationDTO;
import com.ondam.notification.service.NotificationService;
import com.ondam.orders.dto.OrdersDTO;
import com.ondam.orders.dto.OrdersProductDTO;
import com.ondam.orders.service.OrdersProductService;
import com.ondam.orders.service.OrdersService;
import com.ondam.product.dto.ProductDTO;
import com.ondam.product.dto.ProductImageDTO;
import com.ondam.product.dto.ProductOptionDTO;
import com.ondam.product.service.ProductImageService;
import com.ondam.product.service.ProductOptionService;
import com.ondam.product.service.ProductService;
import com.ondam.user.dto.UserAddressDTO;
import com.ondam.user.dto.UserCouponDTO;
import com.ondam.user.dto.UserDTO;
import com.ondam.user.service.UserAddressService;
import com.ondam.user.service.UserCouponService;
import com.ondam.wallet.dto.WalletDTO;
import com.ondam.wallet.service.WalletService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class OrderPaymentController implements Controller {

	private CartService cartService = new CartService();
	private UserAddressService userAddressService = new UserAddressService();
	private UserCouponService userCouponService = new UserCouponService();
	private FamilyMemberService familyMemberService = new FamilyMemberService();
	private WalletService walletService = new WalletService();
	private OrdersService ordersService = new OrdersService();
	private OrdersProductService ordersProductService = new OrdersProductService();
	private ProductService productService = new ProductService();
	private ProductImageService productImageService = new ProductImageService();
	private ProductOptionService productOptionService = new ProductOptionService();
	private NotificationService notificationService = new NotificationService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

		String productNo = request.getParameter("productNo");

		String action = request.getParameter("action");
		if ("submit".equals(action)) {
			return handleSubmit(request, loginUser);
		}

		if (productNo != null && !productNo.trim().isEmpty()) {
			// 케이스 1: 바로 구매
			return handleDirectBuy(request, loginUser);
		} else {
			// 케이스 2: 장바구니에서 구매
			return handleCartBuy(request, loginUser);
		}
	}

	private String handleCartBuy(HttpServletRequest request, UserDTO loginUser) {
		String[] cartItemNos = request.getParameterValues("cartItemNo");
		if (cartItemNos == null || cartItemNos.length == 0) {
			return "redirect:/cart";
		}

		// 전체 장바구니 목록에서 선택된 항목만 필터링
		Vector<CartItemDTO> allItems = cartService.getCartList(loginUser.getUserNo());
		Vector<CartItemDTO> selectedItems = new Vector<>();

		for (CartItemDTO item : allItems) {
			for (String no : cartItemNos) {
				if (item.getCartItemNo() == Integer.parseInt(no)) {
					selectedItems.add(item);
					break;
				}
			}
		}

		request.setAttribute("orderItems", selectedItems);
		request.setAttribute("orderItemCount", selectedItems.size());

		// 원가, 할인가, 수량 구하기
		int totalProductPrice = 0;
		int totalProductDiscount = 0;

		for (CartItemDTO item : selectedItems) {
			int originPrice = item.getProductOriginPrice();
			int salePrice = item.getProductPrice();
			int qty = item.getCartQuantity();

			if (originPrice > 0) {
				totalProductPrice += originPrice * qty;
				totalProductDiscount += (originPrice - salePrice) * qty;
			} else {
				totalProductPrice += salePrice * qty;
			}
		}

		request.setAttribute("totalProductPrice", totalProductPrice);
		request.setAttribute("totalProductDiscount", totalProductDiscount);

		List<UserAddressDTO> addressList = userAddressService.getAddressListByUser(loginUser.getUserNo());
		request.setAttribute("addressList", addressList);

		UserAddressDTO defaultAddress = userAddressService.getDefaultAddress(loginUser.getUserNo());
		request.setAttribute("defaultAddress", defaultAddress);

		int orderAmount = selectedItems.stream().mapToInt(i -> i.getProductPrice() * i.getCartQuantity()).sum();
		List<UserCouponDTO> availableCoupons = userCouponService.getAvailableCoupons(loginUser.getUserNo(),
				orderAmount);
		request.setAttribute("availableCoupons", availableCoupons);
		request.setAttribute("preferPayment", loginUser.getPreferPayment());

		FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
		int familyNo = (myMember != null) ? myMember.getFamilyNo() : 0;
		int walletBalance = 0;
		if (familyNo > 0) {
			WalletDTO wallet = walletService.getWalletByFamilyNo(familyNo);
			if (wallet != null)
				walletBalance = wallet.getBalance();
		}
		request.setAttribute("familyNo", familyNo);
		request.setAttribute("walletBalance", walletBalance);

		return "order/order-payment";
	}

	private String handleDirectBuy(HttpServletRequest request, UserDTO loginUser) {
		// ── 1. 파라미터 수집 ──────────────────────────
		int productNo = Integer.parseInt(request.getParameter("productNo"));
		int optionNo = Integer.parseInt(
				request.getParameter("productOptionNo") != null ? request.getParameter("productOptionNo") : "0");
		int quantity = Integer
				.parseInt(request.getParameter("quantity") != null ? request.getParameter("quantity") : "1");

		// ── 2. 상품 정보 조회 ─────────────────────────
		ProductDTO pDto = productService.getProductById(productNo);
		ProductImageDTO pIDto = productImageService.getProductImageById(productNo);
		ProductOptionDTO optDto = productOptionService.getProductOptionByNo(optionNo);

		if (pDto == null)
			return "redirect:/main";

		// ── 3. CartItemDTO 형태로 조립 (order-payment.jsp 재사용) ──
		CartItemDTO item = new CartItemDTO();
		item.setProductNo(productNo);
		item.setProductOptionNo(optionNo);
		item.setProductName(pDto.getProductName());
		item.setProductOriginPrice(pDto.getProductOriginPrice());
		item.setCartQuantity(quantity);

		if (pIDto != null)
			item.setProductImg(pIDto.getImgFile());

		if (optDto != null) {
			item.setOptionSize(optDto.getOptionSize());
			item.setOptionColor(optDto.getOptionColor());
			item.setOptionStock(optDto.getOptionStock());
			int salePrice = pDto.getProductPrice() + optDto.getOptionAddPrice();
			item.setProductPrice(salePrice);
		} else {
			item.setProductPrice(pDto.getProductPrice());
			item.setOptionSize("N/A");
			item.setOptionColor("기본");
		}

		Vector<CartItemDTO> selectedItems = new Vector<>();
		selectedItems.add(item);

		// ── 4. 금액 계산 ──────────────────────────────
		int originPrice = item.getProductOriginPrice();
		int salePrice = item.getProductPrice();
		int totalProductPrice = originPrice * quantity;
		int totalProductDiscount = (originPrice - salePrice) * quantity;

		request.setAttribute("orderItems", selectedItems);
		request.setAttribute("orderItemCount", 1);
		request.setAttribute("totalProductPrice", totalProductPrice);
		request.setAttribute("totalProductDiscount", totalProductDiscount);

		// ── 5. 배송지, 쿠폰, 결제수단 (handleCartBuy와 동일) ──
		
		String isGift        = request.getParameter("isGift");
		String receiverNoStr = request.getParameter("receiverNo");

		int addressUserNo = loginUser.getUserNo(); // 기본: 내 배송지

		if ("true".equals(isGift) && receiverNoStr != null && !receiverNoStr.isEmpty()) {
		    addressUserNo = Integer.parseInt(receiverNoStr);
		}
		
		List<UserAddressDTO> addressList = userAddressService.getAddressListByUser(addressUserNo);
		request.setAttribute("addressList", addressList);

		UserAddressDTO defaultAddress    = userAddressService.getDefaultAddress(addressUserNo);
		request.setAttribute("defaultAddress", defaultAddress);

		int orderAmount = salePrice * quantity;
		List<UserCouponDTO> availableCoupons = userCouponService.getAvailableCoupons(loginUser.getUserNo(),
				orderAmount);
		request.setAttribute("availableCoupons", availableCoupons);
		request.setAttribute("preferPayment", loginUser.getPreferPayment());

		FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
		int familyNo = (myMember != null) ? myMember.getFamilyNo() : 0;
		int walletBalance = 0;
		if (familyNo > 0) {
			WalletDTO wallet = walletService.getWalletByFamilyNo(familyNo);
			if (wallet != null)
				walletBalance = wallet.getBalance();
		}
		request.setAttribute("familyNo", familyNo);
		request.setAttribute("walletBalance", walletBalance);
		request.setAttribute("buyType", "direct");
		request.setAttribute("directProductNo", productNo);
		request.setAttribute("directOptionNo", optionNo);
		request.setAttribute("directQuantity", quantity);
		request.setAttribute("isGift",     request.getParameter("isGift"));
		request.setAttribute("receiverNo", request.getParameter("receiverNo"));

		return "order/order-payment";
	}

	private String handleSubmit(HttpServletRequest request, UserDTO loginUser) {
	    try {
			// ── 1. 요청 파라미터 수집 ──────────────────────────
			String receiverName = request.getParameter("receiverName");
			String receiverTel = request.getParameter("receiverTel");
			String deliveryAddr = request.getParameter("deliveryAddr");
			String deliveryContent = request.getParameter("deliveryContent");
			int paymentMethod = Integer.parseInt(
					request.getParameter("paymentMethod") != null ? request.getParameter("paymentMethod") : "1");
			int couponDiscount = Integer.parseInt(
					request.getParameter("couponDiscount") != null ? request.getParameter("couponDiscount") : "0");
			int paymentAmount = Integer.parseInt(
					request.getParameter("paymentAmount") != null ? request.getParameter("paymentAmount") : "0");
			String selectedCouponId = request.getParameter("selectedCouponId");
			int userCouponNo = (selectedCouponId != null && !selectedCouponId.isEmpty())
					? Integer.parseInt(selectedCouponId)
					: 0;

			String buyType = request.getParameter("buyType");
			String[] cartItemNos = request.getParameterValues("cartItemNo");

			// ── 2. 아이템 조회 (바로구매 / 장바구니 분기) ──────────
			Vector<CartItemDTO> selectedItems = new Vector<>();

			if ("direct".equals(buyType)) {
				// 바로구매
				int productNo = Integer.parseInt(request.getParameter("directProductNo"));
				int optionNo = request.getParameter("directOptionNo") != null
						? Integer.parseInt(request.getParameter("directOptionNo"))
						: 0;
				int quantity = request.getParameter("directQuantity") != null
						? Integer.parseInt(request.getParameter("directQuantity"))
						: 1;

				ProductDTO pDto = productService.getProductById(productNo);
				ProductOptionDTO optDto = productOptionService.getProductOptionByNo(optionNo);
				ProductImageDTO pIDto = productImageService.getProductImageById(productNo);

				if (pDto == null)
					return "redirect:/main";

				CartItemDTO item = new CartItemDTO();
				item.setProductNo(productNo);
				item.setProductOptionNo(optionNo);
				item.setProductName(pDto.getProductName());
				item.setProductOriginPrice(pDto.getProductOriginPrice());
				item.setCartQuantity(quantity);
				if (pIDto != null)
					item.setProductImg(pIDto.getImgFile());
				if (optDto != null) {
					item.setOptionSize(optDto.getOptionSize());
					item.setOptionColor(optDto.getOptionColor());
					item.setProductPrice(pDto.getProductPrice() + optDto.getOptionAddPrice());
				} else {
					item.setProductPrice(pDto.getProductPrice());
					item.setOptionSize("N/A");
					item.setOptionColor("기본");
				}
				selectedItems.add(item);

			} else {
				// 장바구니 구매
				if (cartItemNos == null || cartItemNos.length == 0)
					return "redirect:/cart";
				Vector<CartItemDTO> allItems = cartService.getCartList(loginUser.getUserNo());
				for (CartItemDTO item : allItems) {
					for (String no : cartItemNos) {
						if (item.getCartItemNo() == Integer.parseInt(no)) {
							selectedItems.add(item);
							break;
						}
					}
				}
				if (selectedItems.isEmpty())
					return "redirect:/cart";
			}

			// ── 3. 금액 계산 ──────────────────────────────────
			int orderPrice = selectedItems.stream()
					.mapToInt(i -> i.getProductOriginPrice() > 0 ? i.getProductOriginPrice() * i.getCartQuantity()
							: i.getProductPrice() * i.getCartQuantity())
					.sum();

			int productDiscount = selectedItems.stream()
					.mapToInt(i -> i.getProductOriginPrice() > 0
							? (i.getProductOriginPrice() - i.getProductPrice()) * i.getCartQuantity()
							: 0)
					.sum();

			int walletUsedAmount = (paymentMethod == 0) ? paymentAmount : 0;

			// ── 4. 주문 코드 생성 ──────────────────────────────
			String orderCode = "ORD" + System.currentTimeMillis()
					+ String.format("%04d", (int) (Math.random() * 10000));

			// ── 5. OrdersDTO 구성 ─────────────────────────────
			OrdersDTO ordersDto = new OrdersDTO();
			ordersDto.setUserNo(loginUser.getUserNo());
			ordersDto.setOrderCode(orderCode);
			ordersDto.setReceiverName(receiverName);
			ordersDto.setReceiverTel(receiverTel);
			ordersDto.setDeliveryAddr(deliveryAddr);
			ordersDto.setDeliveryContent(deliveryContent);
			ordersDto.setOrderPrice(orderPrice);
			ordersDto.setProductDiscount(productDiscount);
			ordersDto.setCouponDiscount(couponDiscount);
			ordersDto.setPaymentAmount(paymentAmount);
			ordersDto.setPaymentMethod(paymentMethod);
			ordersDto.setUserCouponNo(userCouponNo);
			ordersDto.setOrderType(0);

			// ── 6. Orders INSERT → orderNo 획득 ──────────────
			int orderNo = ordersService.createOrdersAndGetNo(ordersDto);
			if (orderNo == 0)
				return "redirect:/cart";

			// ── 7. OrdersProduct INSERT + 재고 검증 및 차감 ──────────
			for (CartItemDTO item : selectedItems) {

			    // 재고 검증
			    ProductOptionDTO optDto = productOptionService.getProductOptionByNo(item.getProductOptionNo());
			    if (optDto == null || optDto.getOptionStock() < item.getCartQuantity()) {
			        ordersService.removeOrders(orderNo);
			        request.getSession().setAttribute("errorMsg",
			        	    "[" + item.getProductName() + "] 재고가 부족합니다.\n(남은 재고: " +
			        	    (optDto != null ? optDto.getOptionStock() : 0) + "개)");

			        // 바로구매 → 상품 상세 / 장바구니 구매 → 장바구니
			        if ("direct".equals(buyType)) {
			        	return "redirect:/product?action=detail&productNo=" + item.getProductNo();
			        }
			        return "redirect:/cart";
			    }

			    // OrdersProduct INSERT
			    OrdersProductDTO opDto = new OrdersProductDTO();
			    opDto.setOrderNo(orderNo);
			    opDto.setProductNo(item.getProductNo());
			    opDto.setProductOptionNo(item.getProductOptionNo());
			    opDto.setSnapProductName(item.getProductName());
			    opDto.setSnapProductPrice(item.getProductPrice());
			    opDto.setSnapOptionSize(item.getOptionSize());
			    opDto.setSnapOptionColor(item.getOptionColor());
			    opDto.setOrderQuantity(item.getCartQuantity());
			    ordersProductService.createOrdersProduct(opDto);

			    // 재고 차감
			    boolean decreased = productOptionService.decreaseStock(item.getProductOptionNo(), item.getCartQuantity());
			    if (!decreased) {
			        ordersService.removeOrders(orderNo);
			        request.getSession().setAttribute("errorMsg",
			            "[" + item.getProductName() + "] 재고 처리 중 오류가 발생했습니다.");

			        // 동일하게 분기
			        if ("direct".equals(buyType)) {
			        	return "redirect:/product?action=detail&productNo=" + item.getProductNo();
			        }
			        return "redirect:/cart";
			    }
			}

			// ── 8. 장바구니 삭제 (장바구니 구매만) ────────────────
			if (!"direct".equals(buyType) && cartItemNos != null) {
				for (String no : cartItemNos) {
					cartService.removeItem(Integer.parseInt(no));
				}
				int totalQty = cartService.refreshCartTotalQuantity(loginUser.getUserNo());
				request.getSession().setAttribute("cartCount", totalQty);
			}

			// ── 9. 쿠폰 사용 처리 ─────────────────────────────
			if (userCouponNo > 0) {
				userCouponService.useUserCoupon(userCouponNo, orderNo);
			}

			// ── 10. 함께지갑 차감 ─────────────────────────────
			if (paymentMethod == 0 && walletUsedAmount > 0) {
				FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
				if (myMember != null) {
					walletService.deductBalance(myMember.getFamilyNo(), walletUsedAmount, orderNo, loginUser);
				}
			}
			
			// ── 11. 알림 발송 ─────────────────────────────
			String firstProductName = selectedItems.get(0).getProductName();
			String notiMsg;
			if (selectedItems.size() == 1) {
			    notiMsg = firstProductName + " " + selectedItems.get(0).getCartQuantity() + "개가 결제 완료되었어요!";
			} else {
			    notiMsg = firstProductName + " 외 " + (selectedItems.size() - 1) + "건이 결제 완료되었어요!";
			}

			NotificationDTO notiDto = new NotificationDTO();
			notiDto.setUserNo(loginUser.getUserNo());
			notiDto.setNotificationContent(notiMsg);
			notiDto.setNotificationType(2);   // 주문/배송
			notiDto.setRefNo(orderNo);        // 주문번호 참조
			notiDto.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toString());
			notificationService.createNotification(notiDto);

			// ── 12. 선물 여부 분기 ─────────────────────────────
			String isGift        = request.getParameter("isGift");
			String receiverNoStr = request.getParameter("receiverNo");

			System.out.println("[handleSubmit] isGift=" + isGift);
			System.out.println("[handleSubmit] receiverNo=" + receiverNoStr);

			if ("true".equals(isGift) && receiverNoStr != null && !receiverNoStr.isEmpty()) {
			    System.out.println("[handleSubmit] → 선물 분기 진입");
			    return "redirect:/gift?action=sendProc"
			         + "&orderNo="    + orderNo
			         + "&receiverNo=" + receiverNoStr;
			} else {
			    System.out.println("[handleSubmit] → 일반 결제 분기");
			    return "redirect:/order/order-detail?orderNo=" + orderNo;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/cart";
		}
	}
}