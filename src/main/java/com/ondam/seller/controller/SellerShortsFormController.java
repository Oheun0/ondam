package com.ondam.seller.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dto.ProductDTO;
import com.ondam.seller.dto.SellerDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SellerShortsFormController implements Controller {

    private final ProductDAO productDao = new ProductDAO();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        SellerDTO loginSeller = (SellerDTO) session.getAttribute("loginSeller");

        // [핵심] 로그인 세션 검증
        if (loginSeller == null) {
            return "redirect:/seller/auth";
        }

        int vendorNo = loginSeller.getVendorNo();

        // 폼 화면 Select Box 렌더링을 위해 현재 접속한 판매자의 상품만 가져옴
        Vector<ProductDTO> productList = productDao.getProductsByVendor(vendorNo);

        request.setAttribute("productList", productList);

        // /WEB-INF/views/seller/shorts/form.jsp 로 포워딩
        return "seller/shorts/form";
    }
}