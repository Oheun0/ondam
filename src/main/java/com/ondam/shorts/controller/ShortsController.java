package com.ondam.shorts.controller;

import java.util.Set;
import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.group.dto.FamilyMemberDTO;
import com.ondam.group.service.FamilyMemberService;
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
    private final FamilyMemberService familyMemberService = new FamilyMemberService(); // 추가된 서비스

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }
        
        // 1. 회원이 찜한 상품 번호 목록
        Set<Integer> wishSet = wishService.getWishedProductNos(loginUser.getUserNo());
        request.setAttribute("wishSet", wishSet);
        
        // 2. 공개된 쇼츠 목록 로드 및 셔플
        Vector<ShortsDTO> publicShorts = shortsService.getPublicAndShuffledShorts();
        request.setAttribute("shortsList", publicShorts);

        // 3. [추가] 조르기 및 선물하기 모달용 그룹(가족) 멤버 리스트 조회 (ProductController와 동일 로직)
        FamilyMemberDTO myMember = familyMemberService.getFamilyMemberByUserNo(loginUser.getUserNo());
        if (myMember != null) {
            Vector<FamilyMemberDTO> memberList = familyMemberService.getFamilyMembersByFamilyNo(myMember.getFamilyNo());
            // 본인 제외
            memberList.removeIf(m -> m.getUserNo() == loginUser.getUserNo());
            // JSP 모달(poke-modal, gift-modal)에서 사용하도록 세팅
            request.setAttribute("pokeMemberList", memberList);
        }

        java.util.Properties prop = new java.util.Properties();

        try (java.io.InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                prop.load(input);
                String kakaoKeyFromConfig = prop.getProperty("kakao.javascript.key");
                request.setAttribute("kakaoKey", kakaoKeyFromConfig);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "shorts/shorts";
    }
}