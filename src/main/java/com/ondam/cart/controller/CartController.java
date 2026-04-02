package com.ondam.cart.controller;

import java.util.Vector;
import com.ondam.cart.dto.CartItemDTO;
import com.ondam.cart.service.CartService;
import com.ondam.common.controller.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CartController implements Controller {

    private CartService cartService = new CartService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String action = request.getParameter("action");
        if (action == null) action = "list";

        HttpSession session = request.getSession();
        
        // 수정: "loginUser" 이름으로 UserDTO 객체를 꺼냅니다.
        com.ondam.user.dto.UserDTO loginUser = (com.ondam.user.dto.UserDTO) session.getAttribute("loginUser");

        // 로그인 체크
        if (loginUser == null) {
            return "redirect:/login"; 
        }

        // 객체에서 userNo를 추출합니다.
        int userNo = loginUser.getUserNo();

        switch (action) {
            case "list":
                return list(request, userNo);
            case "add":
                return add(request, userNo);
            case "delete":
                return delete(request);
            case "clear":
                return clear(userNo);
            case "update": 
                return update(request, userNo);
            case "deleteSelected":
                String[] selectedItems = request.getParameterValues("selectedItems");
                if (selectedItems != null && selectedItems.length > 0) {
                    cartService.removeSelectedItems(selectedItems);
                }
                return "redirect:/cart?action=list";
            default:
                return "redirect:/main";
        }
    }

    private String list(HttpServletRequest request, int userNo) {
        Vector<CartItemDTO> cartList = cartService.getCartList(userNo);
        request.setAttribute("cartList", cartList);
        return "/cart/cart";
    }

    private String add(HttpServletRequest request, int userNo) {
        int productNo = Integer.parseInt(request.getParameter("productNo"));
        int productOptionNo = Integer.parseInt(request.getParameter("productOptionNo"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        cartService.addItemToCart(userNo, productNo, productOptionNo, quantity);
        return "redirect:/cart?action=list";
    }

    private String delete(HttpServletRequest request) {
        int cartItemNo = Integer.parseInt(request.getParameter("cartItemNo"));
        cartService.removeItem(cartItemNo);
        return "redirect:/cart?action=list";
    }

    private String clear(int userNo) {
        cartService.clearCart(userNo);
        return "redirect:/cart?action=list";
    }
    // 수량 갱신 컨트롤러 메서드
    private String update(HttpServletRequest request, int userNo) {
        int cartItemNo = Integer.parseInt(request.getParameter("cartItemNo"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        
        cartService.updateItemQuantity(userNo, cartItemNo, quantity);
        return "redirect:/cart?action=list";
    }
}