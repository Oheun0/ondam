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
        SellerDTO loginSeller = (SellerDTO) session.getAttribute("loginSeller");

        if (loginSeller == null) {
            return "redirect:/seller/auth";
        }

        int vendorNo = loginSeller.getVendorNo();

        switch (action) {
        case "list":
            int currentPage = 1;
            String pageStr = request.getParameter("page");
            if (pageStr != null) {
                currentPage = Integer.parseInt(pageStr);
            }
            int pageSize = 10; 
            int startRow = (currentPage - 1) * pageSize; 
            int totalOrderCount = sellerOrderDAO.getTotalOrderCount(vendorNo);
            int shippingCount = sellerOrderDAO.getOrderCountByState(vendorNo, 2);
            int cancelCount = sellerOrderDAO.getOrderCountByState(vendorNo, 4);

            Vector<SellerOrderListDTO> orderList = sellerOrderDAO.getSellerOrderList(vendorNo, startRow, pageSize);
            int totalPage = (int) Math.ceil((double) totalOrderCount / pageSize);
            int pageBlock = 5; 
            int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
            int endPage = startPage + pageBlock - 1;
            if (endPage > totalPage) endPage = totalPage;
            
            request.setAttribute("orderList", orderList);
            request.setAttribute("totalOrderCount", totalOrderCount);
            request.setAttribute("shippingCount", shippingCount);
            request.setAttribute("cancelCount", cancelCount);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("startPage", startPage);
            request.setAttribute("endPage", endPage);
            
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
                
            case "updateStatus":
                String orderNoParam = request.getParameter("orderNo");
                String statusParam = request.getParameter("status");
                
                if (orderNoParam == null || statusParam == null) {
                    return "redirect:/seller/order?action=list";
                }
                
                int targetOrderNo = Integer.parseInt(orderNoParam);
                int newState = 0;
                
                switch(statusParam) {
                    case "paid": newState = 0; break;
                    case "ready": newState = 1; break;
                    case "shipping": newState = 2; break;
                    case "done": newState = 3; break;
                    case "cancel": newState = 4; break;
                }
                sellerOrderDAO.updateDeliveryState(vendorNo, targetOrderNo, newState);
                
                return "redirect:/seller/order?action=detail&orderNo=" + targetOrderNo;
                
            case "updateItemStatus":
                String itemOrderNoParam = request.getParameter("orderNo");
                String itemStatusParam = request.getParameter("status");
                String itemNosStr = request.getParameter("itemNos");
                                
                if (itemOrderNoParam == null || itemStatusParam == null || itemNosStr == null || itemNosStr.isEmpty()) {
                    return "redirect:/seller/order?action=list";
                }
                
                int itemOrderNo = Integer.parseInt(itemOrderNoParam);
                int itemNewState = 0;
                
                switch(itemStatusParam) {
                    case "paid": itemNewState = 0; break;
                    case "ready": itemNewState = 1; break;
                    case "shipping": itemNewState = 2; break;
                    case "done": itemNewState = 3; break;
                    case "cancel": itemNewState = 4; break;
                }
                sellerOrderDAO.updateItemDeliveryState(vendorNo, itemOrderNo, itemNewState, itemNosStr);
                
                return "redirect:/seller/order?action=detail&orderNo=" + itemOrderNo;
                
            case "updateStatusFromList":
                String listOrderNoStr = request.getParameter("orderNo");
                String listStatusParam = request.getParameter("status");
                
                if (listOrderNoStr == null || listStatusParam == null) {
                    return "redirect:/seller/order?action=list";
                }
                
                int listOrderNo = Integer.parseInt(listOrderNoStr);
                int listNewState = 0;
                
                switch(listStatusParam) {
                    case "ready": listNewState = 1; break;
                    case "shipping": listNewState = 2; break;
                    case "done": listNewState = 3; break;
                }
                sellerOrderDAO.updateDeliveryState(vendorNo, listOrderNo, listNewState);
                return "redirect:/seller/order?action=list";

            case "updateInvoice": { 
                String invOrderNoStr = request.getParameter("orderNo");
                String carrier = request.getParameter("carrier");
                String tracking = request.getParameter("tracking");
                if (invOrderNoStr == null || carrier == null || tracking == null) {
                    return "redirect:/seller/order?action=list";
                }
                int invOrderNo = Integer.parseInt(invOrderNoStr);
                sellerOrderDAO.updateInvoice(vendorNo, invOrderNo, carrier, tracking);
                return "redirect:/seller/order?action=detail&orderNo=" + invOrderNo;
            } 

            case "updateItemInvoice": {
                String invOrderNoStr = request.getParameter("orderNo");
                String invCarrier = request.getParameter("carrier");
                String invTracking = request.getParameter("tracking");
                String invItemNos = request.getParameter("itemNos");

                if (invOrderNoStr == null || invCarrier == null || invTracking == null || invItemNos == null) {
                    return "redirect:/seller/order?action=list";
                }

                int invOrderNo = Integer.parseInt(invOrderNoStr);
                sellerOrderDAO.updateItemInvoice(vendorNo, invOrderNo, invCarrier, invTracking, invItemNos);

                return "redirect:/seller/order?action=detail&orderNo=" + invOrderNo;
            }
            
            default:
                return "redirect:/seller/order?action=list";
        }
    }
}