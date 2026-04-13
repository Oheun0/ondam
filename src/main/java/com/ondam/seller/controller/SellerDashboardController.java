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
        SellerDTO loginUser = (SellerDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/seller/auth";
        }

        int vendorNo = loginUser.getVendorNo();
        Map<String, Integer> stats = sellerService.getDashboardStats(vendorNo);
        request.setAttribute("stats", stats);
        return "seller/dashboard"; 
    }
}