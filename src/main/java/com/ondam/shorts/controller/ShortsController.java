package com.ondam.shorts.controller;


import java.util.Collections;
import java.util.Vector;
import com.ondam.common.controller.Controller;

import com.ondam.shorts.service.ShortsService;
import com.ondam.user.dto.UserDTO;
import com.ondam.shorts.dto.ShortsDTO;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class ShortsController implements Controller {
    private ShortsService shortsService = new ShortsService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 세션에서 로그인 유저 정보 가져오기
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        // 로그인 여부 체크
        if (loginUser == null) {
            // 로그인 안 되어 있으면 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }
    	
    	// 1. DB에서 리스트 가져오기
        Vector<ShortsDTO> allShorts = shortsService.getShortsList();
            
            // 2. 프론트에 넘겨줄 '공개(1)' 상태의 영상만 담을 리스트
            Vector<ShortsDTO> publicShorts = new Vector<>();
            for (ShortsDTO dto : allShorts) {
                if (dto.getShortsState() == 1) { 
                    publicShorts.add(dto);
                }
            }

            // 3. [핵심 로직] 리스트를 무작위로 섞어줍니다. (적당한 유기성 부여)
            // 매번 접속하거나 새로고침할 때마다 영상 순서가 바뀝니다.
            Collections.shuffle(publicShorts);

            // 4. JSP(프론트엔드)에서 쓸 수 있도록 request 객체에 데이터를 담아줍니다.
            request.setAttribute("shortsList", publicShorts);

            // 5. 영상을 보여줄 실제 JSP 페이지로 포워딩합니다.
            // (경로는 프로젝트의 실제 JSP 파일 위치에 맞게 수정해주세요)
            
            return "shorts";
    }
}