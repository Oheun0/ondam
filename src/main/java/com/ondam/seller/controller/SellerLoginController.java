package com.ondam.seller.controller;

import com.ondam.common.controller.Controller;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.seller.service.SellerService;
import com.ondam.seller.dao.VendorDAO; // VendorDAO 추가

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SellerLoginController implements Controller {
    private SellerService sellerService;
    private VendorDAO vendorDAO; // VendorDAO 인스턴스 변수 추가
    
    public SellerLoginController() {
        sellerService = new SellerService();
        vendorDAO = new VendorDAO(); // 초기화
    }
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();

        if (method.equals("GET")) {
            return "seller/auth/login";
        }

        if (method.equals("POST")) {
            String sellerId = request.getParameter("sellerId");
            String sellerPwd = request.getParameter("sellerPwd");

            SellerDTO loginSeller = sellerService.login(sellerId, sellerPwd);

            if (loginSeller != null) {
                HttpSession session = request.getSession();
                session.setAttribute("loginSeller", loginSeller);
                
                // 로그인 성공 시, 연결된 Vendor(업체) 이름 가져오기
                String vendorName = vendorDAO.getVendorName(loginSeller.getVendorNo());
                if (vendorName != null) {
                    session.setAttribute("vendorName", vendorName); // 화면 표출용 업체명
                }
                
                return "redirect:/dashboard";
            } else {
                request.setAttribute("loginError", "아이디 또는 비밀번호가 일치하지 않습니다."); // "오류" 대신 "loginError" 권장
                return "seller/auth/login";
            }
        }
        return null;
    }
}