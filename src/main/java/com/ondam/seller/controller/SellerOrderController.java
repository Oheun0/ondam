package com.ondam.seller.controller;

import com.ondam.common.controller.Controller;
import com.ondam.seller.dao.SellerOrderDAO;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.seller.dto.SellerOrderDetailDTO;
import com.ondam.seller.dto.SellerOrderListDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;

public class SellerOrderController implements Controller {

    private final SellerOrderDAO sellerOrderDAO = new SellerOrderDAO();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        HttpSession session = request.getSession();
        SellerDTO loginUser = (SellerDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/seller/auth";
        }

        int vendorNo = loginUser.getVendorNo();

        switch (action) {
            case "list":
                Vector<SellerOrderListDTO> orderList = sellerOrderDAO.getSellerOrderList(vendorNo);
                request.setAttribute("orderList", orderList);
                return "seller/order/list";
                
            case "detail":
                String orderNoStr = request.getParameter("orderNo");
                if (orderNoStr == null || orderNoStr.isEmpty()) {
                    return "redirect:/seller/order?action=list";
                }
                
                int orderNo = Integer.parseInt(orderNoStr);
                SellerOrderDetailDTO detail = sellerOrderDAO.getSellerOrderDetail(vendorNo, orderNo);
                request.setAttribute("detail", detail);
                
                return "seller/order/detail";
                
            default:
                return "redirect:/seller/order?action=list";
        }
    }
}