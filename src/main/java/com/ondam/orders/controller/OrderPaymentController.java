package com.ondam.orders.controller;

import java.util.List;
import java.util.Vector;

import com.ondam.cart.dto.CartItemDTO;
import com.ondam.cart.service.CartService;
import com.ondam.common.controller.Controller;
import com.ondam.group.dto.FamilyMemberDTO;
import com.ondam.group.service.FamilyMemberService;
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

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        String productNo = request.getParameter("productNo");

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
}