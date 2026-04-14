package com.ondam.seller.controller;

import com.ondam.common.controller.Controller;
import com.ondam.seller.dao.SellerReviewDAO;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.seller.dto.SellerReviewDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class SellerReviewController implements Controller {
    
    private final SellerReviewDAO reviewDAO = new SellerReviewDAO();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginSeller") == null) {
            return "redirect:/seller/auth";
        }

        SellerDTO loginSeller = (SellerDTO) session.getAttribute("loginSeller");
        int vendorNo = loginSeller.getVendorNo();

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
            	List<Map<String, Object>> vendorProductList = reviewDAO.getVendorProductList(vendorNo);
            	request.setAttribute("vendorProductList", vendorProductList);
                String product = request.getParameter("product");
                String rating = request.getParameter("rating");
                String period = request.getParameter("period");
                String query = request.getParameter("query");

                // 페이징 로직
                int currentPage = 1;
                String pageStr = request.getParameter("page");
                if (pageStr != null && !pageStr.isEmpty()) {
                    try { currentPage = Integer.parseInt(pageStr); } catch (Exception e) {}
                }
                int pageSize = 10;
                int startRow = (currentPage - 1) * pageSize;

                // DB 데이터 조회
                int totalCount = reviewDAO.getTotalReviewCount(vendorNo, product, rating, period, query);
                Vector<SellerReviewDTO> reviewList = reviewDAO.getReviewList(vendorNo, product, rating, period, query, startRow, pageSize);
                Map<String, Object> summary = reviewDAO.getReviewSummary(vendorNo);

                // 페이징 계산
                int totalPage = (int) Math.ceil((double) totalCount / pageSize);
                int pageBlock = 5;
                int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
                int endPage = Math.min(startPage + pageBlock - 1, totalPage);

                request.setAttribute("reviewList", reviewList);
                request.setAttribute("summary", summary);     
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("totalPage", totalPage);
                request.setAttribute("startPage", startPage);
                request.setAttribute("endPage", endPage);

                request.setAttribute("paramProduct", product);
                request.setAttribute("paramRating", rating);
                request.setAttribute("paramPeriod", period);
                request.setAttribute("paramQuery", query);

                return "seller/review/list";

            case "reply":
                String reviewNoStr = request.getParameter("reviewNo");
                String replyContent = request.getParameter("replyContent");

                if (reviewNoStr != null && replyContent != null && !replyContent.trim().isEmpty()) {
                    try {
                        int reviewNo = Integer.parseInt(reviewNoStr);
                        reviewDAO.updateReviewReply(reviewNo, replyContent);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                return "redirect:/seller/review?action=list";

            default:
                return "redirect:/seller/review?action=list";
        }
    }
}