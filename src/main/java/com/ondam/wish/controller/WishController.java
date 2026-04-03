package com.ondam.wish.controller;

import com.ondam.common.controller.Controller;
import com.ondam.user.dto.UserDTO;
import com.ondam.wish.service.WishService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class WishController implements Controller {
    private final WishService wishService = new WishService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        if (loginUser == null) return "redirect:/login";
        int userNo = loginUser.getUserNo();

        if ("list".equals(action)) {
            request.setAttribute("wishList", wishService.getMyWishList(userNo));
            return "/wish/wishList"; 
        } else if ("toggle".equals(action)) {
            int pNo = Integer.parseInt(request.getParameter("productNo"));
            // 전달받은 상품번호(pNo)만 넘겨서 토글 실행
            wishService.toggleWish(userNo, pNo);
            return "redirect:/wish?action=list";
        }
        return "redirect:/main";
    }
}