package com.ondam.seller.controller;

import com.ondam.common.controller.Controller;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.seller.service.SellerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

public class SellerDashboardController implements Controller {

    private final SellerService sellerService = new SellerService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        SellerDTO loginSeller = (SellerDTO) session.getAttribute("loginSeller");

        if (loginSeller == null) {
            return "redirect:/seller/auth";
        }

        int vendorNo = loginSeller.getVendorNo();
        Map<String, Integer> stats = sellerService.getDashboardStats(vendorNo);
        request.setAttribute("stats", stats);
        return "seller/dashboard"; 
    }
}