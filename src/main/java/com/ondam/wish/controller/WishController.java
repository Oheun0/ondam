package com.ondam.wish.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.user.dto.UserDTO;
import com.ondam.wish.dto.WishDTO;
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
        	String sort = request.getParameter("sort");
        	String part = request.getParameter("part");
        	if (sort == null) sort = "담은순";
        	Vector<WishDTO> wishList = wishService.getMyWishList(userNo, sort, part);

            Set<Integer> wishSet = new HashSet<>();
            Map<Integer, String> thumbnailMap = new HashMap<>();
            for (WishDTO w : wishList) {
                wishSet.add(w.getProductNo());
                if (w.getProductImg() != null)
                    thumbnailMap.put(w.getProductNo(), w.getProductImg());
            }

            request.setAttribute("productList",  wishList);   // product-grid.jsp용
            request.setAttribute("wishSet",       wishSet);   // 찜 버튼 is-active용
            request.setAttribute("thumbnailMap",  thumbnailMap); // 썸네일용
            request.setAttribute("currentSort", sort); // 현재 정렬 상태
            request.setAttribute("currentPart", part); // 필터
            return "/product/favorite/favorite-list";

        } else if ("toggle".equals(action)) {
            int pNo = Integer.parseInt(request.getParameter("productNo"));
            boolean wished = wishService.toggleWish(userNo, pNo);

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"wished\":" + wished + ",\"productNo\":" + pNo + "}");
            return null;
        }

        return "redirect:/main";
    }
}