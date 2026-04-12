package com.ondam.shorts.controller;

import java.util.Set;
import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.shorts.service.ShortsService;
import com.ondam.shorts.dto.ShortsDTO;
import com.ondam.wish.service.WishService;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ShortsController implements Controller {
    
    private final ShortsService shortsService = new ShortsService();
    private final WishService wishService = new WishService(); 

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }
        // 로그인한 경우, 회원이 찜한 상품 번호 목록(Set)을 wishSet으로 전달
        Set<Integer> wishSet = wishService.getWishedProductNos(loginUser.getUserNo());
        request.setAttribute("wishSet", wishSet);
        // 공개된 쇼츠 목록 로드 및 셔플
        Vector<ShortsDTO> publicShorts = shortsService.getPublicAndShuffledShorts();
        request.setAttribute("shortsList", publicShorts);
        return "shorts/shorts";
    }
}