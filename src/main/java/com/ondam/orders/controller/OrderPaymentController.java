package com.ondam.orders.controller;

import java.util.List;
import java.util.Vector;

import com.ondam.cart.dto.CartItemDTO;
import com.ondam.cart.service.CartService;
import com.ondam.common.controller.Controller;
import com.ondam.group.dto.FamilyMemberDTO;
import com.ondam.group.service.FamilyMemberService;
import com.ondam.orders.dto.OrdersDTO;
import com.ondam.orders.dto.OrdersProductDTO;
import com.ondam.orders.service.OrdersProductService;
import com.ondam.orders.service.OrdersService;
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
            // 케이스 1: 바로 구매 (나중에)
            return "order/order-payment";
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
            int salePrice   = item.getProductPrice();
            int qty         = item.getCartQuantity();

            if (originPrice > 0) {
                totalProductPrice    += originPrice * qty;
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
		    if (wallet != null) walletBalance = wallet.getBalance();
		}
		request.setAttribute("familyNo", familyNo);
		request.setAttribute("walletBalance", walletBalance);
        	
        return "order/order-payment";
    }
    
    private String handleSubmit(HttpServletRequest request, UserDTO loginUser) {
        try {
            // ── 1. 요청 파라미터 수집 ──────────────────────────
            String receiverName    = request.getParameter("receiverName");
            String receiverTel     = request.getParameter("receiverTel");
            String deliveryAddr    = request.getParameter("deliveryAddr");
            String deliveryContent = request.getParameter("deliveryContent");
            int paymentMethod      = Integer.parseInt(request.getParameter("paymentMethod") != null ? request.getParameter("paymentMethod") : "1");
            int couponDiscount     = Integer.parseInt(request.getParameter("couponDiscount") != null ? request.getParameter("couponDiscount") : "0");
            int paymentAmount      = Integer.parseInt(request.getParameter("paymentAmount") != null ? request.getParameter("paymentAmount") : "0");
            String selectedCouponId = request.getParameter("selectedCouponId");
            int userCouponNo       = (selectedCouponId != null && !selectedCouponId.isEmpty()) ? Integer.parseInt(selectedCouponId) : 0;

            String[] cartItemNos = request.getParameterValues("cartItemNo");
            if (cartItemNos == null || cartItemNos.length == 0) return "redirect:/cart";

            // ── 2. 선택된 장바구니 아이템 조회 ───────────────────
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
            if (selectedItems.isEmpty()) return "redirect:/cart";

            // ── 3. 금액 계산 ──────────────────────────────────
            int orderPrice = selectedItems.stream()
                    .mapToInt(i -> i.getProductOriginPrice() > 0
                            ? i.getProductOriginPrice() * i.getCartQuantity()
                            : i.getProductPrice() * i.getCartQuantity())
                    .sum();

            int productDiscount = selectedItems.stream()
                    .mapToInt(i -> i.getProductOriginPrice() > 0
                            ? (i.getProductOriginPrice() - i.getProductPrice()) * i.getCartQuantity()
                            : 0)
                    .sum();

            int walletUsedAmount = (paymentMethod == 0) ? paymentAmount : 0; // 0 = 함께지갑

            // ── 4. 주문 코드 생성 (ORD + timestamp + 랜덤 4자리) ──
            String orderCode = "ORD" + System.currentTimeMillis()
                    + String.format("%04d", (int)(Math.random() * 10000));

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
            ordersDto.setOrderType(0); // 일반 주문

            // ── 6. Orders INSERT → orderNo 획득 ──────────────
            int orderNo = ordersService.createOrdersAndGetNo(ordersDto);
            if (orderNo == 0) return "redirect:/cart"; // INSERT 실패

            // ── 7. OrdersProduct INSERT (아이템별 스냅샷) ────────
            for (CartItemDTO item : selectedItems) {
                OrdersProductDTO opDto = new OrdersProductDTO();
                opDto.setOrderNo(orderNo);
                opDto.setProductNo(item.getProductNo());
                opDto.setProductOptionNo(item.getProductOptionNo());
                opDto.setSnapProductName(item.getProductName());
                opDto.setSnapProductPrice(item.getProductPrice()); // 실제 결제가 (할인 후)
                opDto.setSnapOptionSize(item.getOptionSize());
                opDto.setSnapOptionColor(item.getOptionColor());
                opDto.setOrderQuantity(item.getCartQuantity());
                ordersProductService.createOrdersProduct(opDto);
            }

            // ── 8. 장바구니 아이템 삭제 (주문된 항목만) ───────────
            for (String no : cartItemNos) {
                cartService.removeItem(Integer.parseInt(no));
            }
            // 세션 장바구니 카운트 갱신
            int totalQty = cartService.refreshCartTotalQuantity(loginUser.getUserNo());
            request.getSession().setAttribute("cartCount", totalQty);

            // ── 9. 쿠폰 사용 처리 ────────────────────────────
            if (userCouponNo > 0) {
                userCouponService.useUserCoupon(userCouponNo, orderNo);
            }

         // 10. 함께지갑 차감
            if (paymentMethod == 0 && walletUsedAmount > 0) {
                FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
                if (myMember != null) {
                    walletService.deductBalance(myMember.getFamilyNo(), walletUsedAmount, orderNo, loginUser);
                }
            }

            // ── 11. 주문 완료 페이지로 이동 ───────────────────
            return "redirect:/order/order-detail?orderNo=" + orderNo;

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/cart";
        }
    }
}