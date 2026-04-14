package com.ondam.seller.controller;

import com.ondam.common.controller.Controller;
import com.ondam.seller.dao.SellerSettlementDAO;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.seller.dto.SellerSettlementDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class SellerSettlementController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 세션 확인 및 로그인 체크
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginSeller") == null) {
            return "redirect:/seller/auth";
        }

        SellerDTO loginSeller = (SellerDTO) session.getAttribute("loginSeller");
        int vendorNo = loginSeller.getVendorNo();
        
        // 2. 파라미터 수집 (검색 필터 + 페이지 번호)
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String settleStatus = request.getParameter("settleStatus");
        String payMethod = request.getParameter("payMethod");
        
        String pageParam = request.getParameter("page");
        int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
        int pageSize = 10;

     // 3. DAO 호출 및 데이터 가져오기
        SellerSettlementDAO settlementDAO = new SellerSettlementDAO();

        int totalCount = settlementDAO.getTotalCount(vendorNo, startDate, endDate, settleStatus, payMethod);
        Vector<SellerSettlementDTO> settlementList = settlementDAO.getSettlementList(vendorNo, startDate, endDate, settleStatus, payMethod, currentPage, pageSize);

        // 4. 통계 및 요약 수치 계산
        long totalGross = 0, totalPending = 0, totalDone = 0, totalRefund = 0, totalFee = 0;
        long sumCard = 0, sumBank = 0, sumWallet = 0;

        for (SellerSettlementDTO item : settlementList) {
            totalGross += item.getTotalAmount();
            totalRefund += item.getRefundAmount();
            totalFee += item.getCommissionFee();

            sumCard += item.getCardAmount();
            sumBank += item.getBankAmount();
            sumWallet += item.getWalletAmount();
            
            if (item.getSettleState() == 1) {
                totalDone += item.getActualAmount();
            } else {
                totalPending += item.getActualAmount();
            }
        }

        // 5. 결제 수단 비중 계산
        long totalPay = sumCard + sumBank + sumWallet;
        int cardPct = 0, bankPct = 0, walletPct = 0;

        if (totalPay > 0) {
        	cardPct = (int) Math.round((double) sumCard / totalPay * 100);
            bankPct = (int) Math.round((double) sumBank / totalPay * 100);
            walletPct = (int) Math.round((double) sumWallet / totalPay * 100);
            
            int diff = 100 - (cardPct + bankPct + walletPct);
            if (diff != 0) {
                if (cardPct >= bankPct && cardPct >= walletPct) {
                    cardPct += diff;
                } else if (bankPct >= cardPct && bankPct >= walletPct) {
                    bankPct += diff;
                } else {
                    walletPct += diff;
                }
            }
        }

        Map<String, Object> payMethodStats = new HashMap<>();
        payMethodStats.put("cardAmt", sumCard);    payMethodStats.put("cardPct", cardPct);
        payMethodStats.put("bankAmt", sumBank);    payMethodStats.put("bankPct", bankPct);
        payMethodStats.put("walletAmt", sumWallet); payMethodStats.put("walletPct", walletPct);

        // 6. View로 전달할 데이터 설정 (setAttribute)
        request.setAttribute("sellerPageTitle", "정산 · 매출");
        request.setAttribute("sellerActiveMenu", "settlement");
        request.setAttribute("sellerContentPage", "/WEB-INF/views/seller/settlement/list.jsp");
        
        request.setAttribute("settlementList", settlementList);
        request.setAttribute("totalGross", totalGross);
        request.setAttribute("totalPending", totalPending);
        request.setAttribute("totalDone", totalDone);
        request.setAttribute("totalRefund", totalRefund);
        request.setAttribute("totalFee", totalFee);
        request.setAttribute("payStats", payMethodStats);

        // 검색 조건 및 페이징 정보 유지
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("settleStatus", settleStatus);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPage", (int)Math.ceil((double)totalCount / pageSize));
        request.setAttribute("payMethod", payMethod);

        // 최근 환불 리스트
        List<Map<String, String>> recentRefunds = settlementDAO.getRecentRefunds(vendorNo);
        request.setAttribute("recentRefunds", recentRefunds);

        return "seller/settlement/list";
    }
}