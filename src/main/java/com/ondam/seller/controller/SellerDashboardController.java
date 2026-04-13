package com.ondam.seller.controller;

import com.ondam.common.controller.Controller;
import com.ondam.seller.dao.SellerOrderDAO;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.seller.dto.SellerOrderListDTO;
import com.ondam.seller.service.SellerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.Vector;

/**
 * 판매자 대시보드 — 로그인 세션 필요
 */
public class SellerDashboardController implements Controller {
    private final SellerService sellerService = new SellerService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginSeller") == null) {
			return "redirect:/seller/auth";
		}

		SellerDTO seller = (SellerDTO) session.getAttribute("loginSeller");
		String vendorName = (String) session.getAttribute("vendorName");
		String displayName = null;
		if (vendorName != null && !vendorName.isBlank()) {
			displayName = vendorName.trim();
		} else if (seller != null && seller.getSellerName() != null && !seller.getSellerName().isBlank()) {
			displayName = seller.getSellerName().trim();
		} else {
			displayName = "판매자";
		}
		request.setAttribute("sellerName", displayName);
		request.setAttribute("sellerPageTitle", "대시보드");
		request.setAttribute("sellerActiveMenu", "dashboard");
		request.setAttribute("sellerContentPage", "/WEB-INF/views/seller/dashboard-content.jsp");
		request.setAttribute("sellerExtraCss", "/css/seller/seller-dashboard.css");
		request.setAttribute("sellerExtraJs", "/js/seller/dashboard.js");

        int vendorNo = seller.getVendorNo();
        Map<String, Integer> stats = sellerService.getDashboardStats(vendorNo);
        SellerOrderDAO sellerOrderDAO = new SellerOrderDAO();
        Vector<SellerOrderListDTO> recentOrders = sellerOrderDAO.getSellerOrderList(vendorNo, 0, 5);
        request.setAttribute("stats", stats);
        request.setAttribute("recentOrders", recentOrders);
        return "seller/dashboard";
    }
}
