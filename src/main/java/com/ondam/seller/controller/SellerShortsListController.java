package com.ondam.seller.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dto.ProductDTO;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.shorts.dao.ShortsDAO;
import com.ondam.shorts.dto.ShortsDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SellerShortsListController implements Controller {

    private final ShortsDAO shortsDao = new ShortsDAO();
    private final ProductDAO productDao = new ProductDAO();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        SellerDTO loginSeller = (SellerDTO) session.getAttribute("loginUser");

        // [핵심] 로그인 세션 검증: 판매자가 아니면 로그인 페이지로 리다이렉트
        if (loginSeller == null) {
            return "redirect:/seller/auth";
        }

        int vendorNo = loginSeller.getVendorNo();
        
        // 해당 벤더의 쇼츠 목록 조회
        Vector<ShortsDTO> shortsList = shortsDao.getShortsByVendor(vendorNo);

        // JSP에서 보여줄 상품명과 찜 갯수(wishCount) 조립
        for (ShortsDTO dto : shortsList) {
            ProductDTO pDto = productDao.getProductById(dto.getProductNo());
            if (pDto != null) {
                dto.setProductName(pDto.getProductName());
                dto.setWishCount(pDto.getWishCount());
            } else {
                dto.setProductName("알 수 없는 상품");
                dto.setWishCount(0);
            }
        }

        request.setAttribute("shortsList", shortsList);

        // /WEB-INF/views/seller/shorts/list.jsp 로 포워딩
        return "seller/shorts/list";
    }
}