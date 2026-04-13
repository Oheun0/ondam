package com.ondam.seller.controller;

import com.ondam.common.controller.Controller;
import com.ondam.seller.dao.SellerSettlementDAO;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.seller.dto.SellerSettlementDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Vector;

public class SellerSettlementDownloadController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginSeller") == null) {
            return "redirect:/seller/auth";
        }

        SellerDTO loginSeller = (SellerDTO) session.getAttribute("loginSeller");
        int vendorNo = loginSeller.getVendorNo();

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String settleStatus = request.getParameter("settleStatus");
        String payMethod = request.getParameter("payMethod");

        SellerSettlementDAO dao = new SellerSettlementDAO();
        Vector<SellerSettlementDTO> list = dao.getSettlementList(vendorNo, startDate, endDate, settleStatus, payMethod);
        response.setContentType("text/csv; charset=MS949");
        response.setHeader("Content-Disposition", "attachment; filename=\"settlement_list.csv\"");

        PrintWriter out = response.getWriter();
        out.println("정산번호,정산기간,총매출,환불/취소액,플랫폼수수료,최종정산대상액,정산상태");

        for (SellerSettlementDTO item : list) {
            String statusText = (item.getSettleState() == 1) ? "정산완료" : "정산예정";
            out.println("ST" + item.getSettlementNo() + "," 
                      + item.getCreatedAt() + "," 
                      + item.getTotalAmount() + "," 
                      + item.getRefundAmount() + "," 
                      + item.getCommissionFee() + "," 
                      + item.getActualAmount() + "," 
                      + statusText);
        }
        
        out.flush();
        out.close();
        return null; 
    }
}