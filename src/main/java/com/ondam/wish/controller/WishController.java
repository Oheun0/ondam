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
            // ── targetUserNo 분기 (내 사람 찜 목록 보기) ──
            String targetUserNoParam = request.getParameter("targetUserNo");
            int targetUserNo = (targetUserNoParam != null)
                ? Integer.parseInt(targetUserNoParam)
                : userNo;
            boolean isHelperMode = (targetUserNo != userNo);

            String sort = request.getParameter("sort");
            String part = request.getParameter("part");
            if (sort == null) sort = "담은순";

            // targetUserNo 기준으로 찜 목록 조회
            Vector<WishDTO> wishList = wishService.getMyWishList(targetUserNo, sort, part);

            Set<Integer> wishSet = new HashSet<>();
            Map<Integer, String> thumbnailMap = new HashMap<>();
            for (WishDTO w : wishList) {
                wishSet.add(w.getProductNo());
                if (w.getProductImg() != null)
                    thumbnailMap.put(w.getProductNo(), w.getProductImg());
            }

            request.setAttribute("productList",   wishList);
            request.setAttribute("wishSet",        wishSet);
            request.setAttribute("thumbnailMap",   thumbnailMap);
            request.setAttribute("currentSort",    sort);
            request.setAttribute("currentPart",    part);
            request.setAttribute("isHelperMode",   isHelperMode);
            request.setAttribute("targetUserNo",   targetUserNo);
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