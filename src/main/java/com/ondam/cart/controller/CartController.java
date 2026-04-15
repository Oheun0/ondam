package com.ondam.cart.controller;

import java.util.Vector;
import com.ondam.cart.dto.CartItemDTO;
import com.ondam.cart.service.CartService;
import com.ondam.common.controller.Controller;
import com.ondam.user.dto.UserDTO;

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
        
        if ("getCartCount".equals(action)) {
            int count = 0;
            if (session.getAttribute("cartCount") != null) {
                count = (Integer) session.getAttribute("cartCount");
            }
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"count\": " + count + "}");
            return null;
        }
        
     //리뷰에서 해당 옵션으로 장바구니 담기 (AJAX)
        if ("addFromReview".equals(action)) {
            response.setContentType("application/json;charset=UTF-8");
            java.io.PrintWriter out = response.getWriter();
            
            UserDTO loginUserForAjax = (UserDTO) session.getAttribute("loginUser");
            if (loginUserForAjax == null) {
                out.print("{\"status\":\"login_required\"}");
                return null;
            }

            try {
                int productNo = Integer.parseInt(request.getParameter("productNo"));
                String color = request.getParameter("color");
                String size = request.getParameter("size");
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                // DB에서 색상과 사이즈로 정확한 '옵션 번호' 찾아오기
                int productOptionNo = cartService.findOptionNoByColorAndSize(productNo, color, size);

                if (productOptionNo > 0) {
                    cartService.addItemToCart(loginUserForAjax.getUserNo(), productNo, productOptionNo, quantity);
                    syncCartSession(request, loginUserForAjax.getUserNo());
                    out.print("{\"status\":\"success\"}");
                } else {
                    out.print("{\"status\":\"error\", \"message\":\"해당 옵션이 품절되었거나 존재하지 않습니다.\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.print("{\"status\":\"error\", \"message\":\"서버 오류가 발생했습니다.\"}");
            } finally {
                out.flush();
                out.close();
            }
            return null;
        }
        
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

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
                return clear(userNo, request);
            case "update": 
                return update(request, userNo);
            case "deleteSelected":
                return deleteSelected(request, userNo);
            case "updateOption":
                return updateOption(request, userNo);
            default:
                return "redirect:/main";
        }
    }

    private String list(HttpServletRequest request, int userNo) {
        Vector<CartItemDTO> cartList = cartService.getCartList(userNo);
        request.setAttribute("cartList", cartList);
        return "/product/cart/cart";
    }
    
    private void syncCartSession(HttpServletRequest request, int userNo) {
        int totalQty = cartService.refreshCartTotalQuantity(userNo);
        request.getSession().setAttribute("cartCount", totalQty);
    }

    private String add(HttpServletRequest request, int userNo) {
        int productNo = Integer.parseInt(request.getParameter("productNo"));
        int productOptionNo = Integer.parseInt(request.getParameter("productOptionNo"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        cartService.addItemToCart(userNo, productNo, productOptionNo, quantity);
        syncCartSession(request, userNo); // [추가] 세션 갱신
        return "redirect:/cart?action=list";
    }

    private String delete(HttpServletRequest request) {
        int cartItemNo = Integer.parseInt(request.getParameter("cartItemNo"));
        cartService.removeItem(cartItemNo);

        com.ondam.user.dto.UserDTO loginUser = (com.ondam.user.dto.UserDTO) request.getSession().getAttribute("loginUser");
        if(loginUser != null) syncCartSession(request, loginUser.getUserNo());
        
        return "redirect:/cart?action=list";
    }

    private String clear(int userNo, HttpServletRequest request) {
        cartService.clearCart(userNo);
        
        syncCartSession(request, userNo);
        
        return "redirect:/cart?action=list";
    }
    // 수량 갱신 컨트롤러 메서드
    private String update(HttpServletRequest request, int userNo) {
        int cartItemNo = Integer.parseInt(request.getParameter("cartItemNo"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        
        // 1. 수량 업데이트 (기존 로직)
        cartService.updateItemQuantity(userNo, cartItemNo, quantity);
        
        // 2. [추가] 변경된 총 수량을 다시 계산하여 세션에 덮어쓰기
        syncCartSession(request, userNo);
        
        return "redirect:/cart?action=list";
    }
    
    private String deleteSelected(HttpServletRequest request, int userNo) {
        String[] selectedItems = request.getParameterValues("selectedItems");
        if (selectedItems != null && selectedItems.length > 0) {
            cartService.removeSelectedItems(selectedItems);
        }
        
        // 일괄 삭제 후 헤더 장바구니 숫자 동기화
        syncCartSession(request, userNo);
        
        return "redirect:/cart?action=list";
    }
    
    private String updateOption(HttpServletRequest request, int userNo) {
        int cartItemNo = Integer.parseInt(request.getParameter("cartItemNo"));
        int productOptionNo = Integer.parseInt(request.getParameter("productOptionNo"));
        int quantity = request.getParameter("quantity") != null
            ? Integer.parseInt(request.getParameter("quantity")) : 1;  // 수량 추가

        cartService.updateItemOption(userNo, cartItemNo, productOptionNo);
        cartService.updateItemQuantity(userNo, cartItemNo, quantity);  // 수량도 반영
        syncCartSession(request, userNo);

        return "redirect:/cart?action=list";
    }
}