package com.ondam.seller.controller;

import com.ondam.common.controller.Controller;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.seller.service.SellerService;
import com.ondam.seller.dao.VendorDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SellerAuthController implements Controller {

    /** 로그인 유지 미체크 시(브라우저 닫기 전까지) 세션 유효 시간 */
    private static final int SESSION_DEFAULT_SECONDS = 30 * 60;
    /** 로그인 상태 유지 체크 시(예: 7일) */
    private static final int SESSION_REMEMBER_SECONDS = 7 * 24 * 60 * 60;

    private final SellerService sellerService = new SellerService();
    private final VendorDAO vendorDAO = new VendorDAO();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String action = request.getParameter("action");

        // 1. action 파라미터가 없으면 기본적으로 로그인 폼 화면(JSP)을 띄움
        if (action == null || action.trim().isEmpty()) {
            // DispatcherServlet에 의해 /WEB-INF/views/seller/auth/login.jsp 로 포워딩 됨
            return "seller/auth/login"; 
        }

        try {
            switch (action.trim()) {
                case "login":
                    // 폼에서 전송된 아이디와 비밀번호 가져오기
                    String sellerId = request.getParameter("sellerId");
                    String sellerPw = request.getParameter("sellerPw");
                    if (sellerId != null) {
                        sellerId = sellerId.trim();
                    }
                    if (sellerPw != null) {
                        sellerPw = sellerPw.trim();
                    }

                    // 입력값 검증 (빈 칸 제출 시)
                    if (sellerId == null || sellerId.isEmpty() || sellerPw == null || sellerPw.isEmpty()) {
                        request.setAttribute("loginError", "아이디와 비밀번호를 모두 입력해주세요.");
                        return "seller/auth/login"; // 다시 로그인 화면으로 포워딩
                    }

                    // 서비스 단에서 DB 조회 및 로그인 시도
                    SellerDTO loginSeller = sellerService.login(sellerId, sellerPw);

                    if (loginSeller != null) {
                        // 로그인 성공 시, 연결된 Vendor(업체) 이름 가져오기
                        String vendorName = vendorDAO.getVendorName(loginSeller.getVendorNo());

                        // 세션에 통합 정보 저장 (이후 모든 페이지에서 접근 가능)
                        HttpSession session = request.getSession();
                        boolean remember = "on".equalsIgnoreCase(request.getParameter("sellerRemember"));
                        session.setMaxInactiveInterval(remember ? SESSION_REMEMBER_SECONDS : SESSION_DEFAULT_SECONDS);

                        session.setAttribute("loginSeller", loginSeller);
                        
                        if (vendorName != null) {
                            session.setAttribute("vendorName", vendorName);
                        }

                        return "redirect:/seller/dashboard";
                    } else {
                        // 로그인 실패 (DB에 정보가 없거나 비밀번호가 틀림)
                    	HttpSession session = request.getSession();
                        session.setAttribute("loginError", "아이디 또는 비밀번호가 일치하지 않습니다.");
                        return "redirect:/seller/auth"; // 다시 로그인 화면으로 포워딩
                    }

                case "logout":
                    // 세션 초기화 (로그아웃 처리)
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        session.invalidate(); 
                    }
                    // 로그아웃 후 다시 판매자 로그인 페이지로 리다이렉트
                    return "redirect:/seller/auth";

                default:
                    // 알 수 없는 action 값이 들어오면 기본 로그인 창으로
                    return "seller/auth/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 서버 내부 에러 발생 시
            request.setAttribute("loginError", "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            return "seller/auth/login";
        }
    }
}