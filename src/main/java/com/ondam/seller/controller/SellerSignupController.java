package com.ondam.seller.controller;

import com.ondam.common.controller.Controller;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.seller.dto.VendorDTO;
import com.ondam.seller.service.SellerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SellerSignupController implements Controller {

    private final SellerService sellerService = new SellerService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod(); // GET 또는 POST 확인

        // 1. 회원가입 페이지 접속 (GET)
        if (method.equals("GET")) {
            String action = request.getParameter("action");
            if ("complete".equals(action)) {
                return "seller/auth/signup-complete"; // 가입 완료 페이지
            }
            return "seller/auth/signup"; // 가입 폼 페이지
        }

        // 2. 회원가입 처리 (POST)
        if (method.equals("POST")) {
            try {
                // [Vendor 정보 수집]
                VendorDTO vDto = new VendorDTO();
                vDto.setVendorName(request.getParameter("storeName"));
                vDto.setBizRegNo(request.getParameter("bizNo"));
                vDto.setRepName(request.getParameter("managerName"));
                vDto.setBizTel(request.getParameter("phone"));
                vDto.setContactEmail(request.getParameter("email"));
   
                String bizTypeStr = request.getParameter("bizType");

                if (bizTypeStr != null && !bizTypeStr.trim().isEmpty()) {
                    // 문자열 "2"를 숫자 2로 변환해서 int 타입에 세팅!
                    vDto.setBizType(Integer.parseInt(bizTypeStr)); 
                } else {
                    // 혹시라도 값이 안 넘어왔을 때 에러 나지 않게 기본값 1(개인) 세팅
                    vDto.setBizType(1); 
                }

                // 💡 [수정 1] 출고지 주소 합치기 (우편번호에 괄호 추가)
                String shipZip = request.getParameter("shipZip");
                String shipAddr1 = request.getParameter("shipAddr1");
                String shipAddr2 = request.getParameter("shipAddr2");
                String fullAddr = "(" + shipZip + ") " + shipAddr1 + " " + shipAddr2;
                vDto.setBizAddr(fullAddr);

                // 💡 [추가 2] 반품지 주소 가져와서 합치기 (우편번호 괄호 포함)
                String returnZip = request.getParameter("returnZip");
                String returnAddr1 = request.getParameter("returnAddr1");
                String returnAddr2 = request.getParameter("returnAddr2");
                
                // 반품지가 정상적으로 넘어왔다면 합치고, 혹시라도 비어있다면 출고지와 동일하게 복사 (안전장치)
                if (returnZip != null && !returnZip.trim().isEmpty()) {
                    String fullReturnAddr = "(" + returnZip + ") " + returnAddr1 + " " + returnAddr2;
                    vDto.setBizReturnAddr(fullReturnAddr);
                } else {
                    vDto.setBizReturnAddr(fullAddr); 
                }

                // [Seller 계정 정보 수집]
                SellerDTO sDto = new SellerDTO();
                sDto.setSellerId(request.getParameter("sellerId"));
                sDto.setSellerPwd(request.getParameter("sellerPw"));
                sDto.setSellerName(request.getParameter("managerName"));

                // 서비스 호출 (업체등록 + 계정등록 한꺼번에)
                boolean result = sellerService.registerSeller(vDto, sDto);

                if (result) {
                    // 💡 [수정 2] 404 에러 해결! redirect 주소를 /seller/auth/signup으로 정확히 맞춤
                    return "redirect:/seller/auth/signup?action=complete&storeName=" + vDto.getVendorName() + "&sellerId=" + sDto.getSellerId();
                } else {
                    request.setAttribute("signupError", "회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
                    return "seller/auth/signup";
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("signupError", "서버 오류가 발생했습니다.");
                return "seller/auth/signup";
            }
        }
        return null;
    }
}