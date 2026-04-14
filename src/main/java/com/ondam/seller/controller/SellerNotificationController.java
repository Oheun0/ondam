package com.ondam.seller.controller;

import java.util.Vector;
import com.ondam.common.controller.Controller;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.seller.dto.SellerNotificationDTO;
import com.ondam.seller.service.SellerNotificationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SellerNotificationController implements Controller {
    private SellerNotificationService notiService = new SellerNotificationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginSeller") == null) {
            return "redirect:/seller/auth";
        }
        
        // 💡 [핵심 추가] AJAX 요청인지 확인하는 로직
        // 브라우저 주소창에 직접 쳐서 들어오면 이 헤더가 null입니다.
        String ajaxHeader = request.getHeader("X-Requested-With");
        if (!"XMLHttpRequest".equals(ajaxHeader)) {
            // 직접 접근 시 대시보드로 강제 튕겨냄
            return "redirect:/seller/dashboard"; 
        }
        
        SellerDTO loginSeller = (SellerDTO) session.getAttribute("loginSeller");
        int vendorNo = loginSeller.getVendorNo();
        String action = request.getParameter("action");

        response.setContentType("application/json; charset=UTF-8");

        if ("answer".equals(action)) {
            // 답변 등록 처리
            int inquiryNo = Integer.parseInt(request.getParameter("inquiryNo").replace("INQ-", ""));
            String answerContent = request.getParameter("answerContent");
            boolean success = notiService.answerInquiry(inquiryNo, answerContent);
            response.getWriter().print("{\"success\": " + success + "}");
            return null;
        }

        // 기본: 알림 리스트 반환 (action=list)
        Vector<SellerNotificationDTO> list = notiService.getNotifications(vendorNo);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
            SellerNotificationDTO n = list.get(i);
            sb.append("{");
            sb.append("\"id\":\"").append(escapeJson(n.getId())).append("\",");
            sb.append("\"kind\":\"").append(escapeJson(n.getKind())).append("\",");
            sb.append("\"status\":\"").append(escapeJson(n.getStatus())).append("\",");
            sb.append("\"date\":\"").append(escapeJson(n.getDate())).append("\",");
            sb.append("\"product\":\"").append(escapeJson(n.getProduct())).append("\",");
            sb.append("\"author\":\"").append(escapeJson(n.getAuthor())).append("\",");
            sb.append("\"title\":\"").append(escapeJson(n.getTitle())).append("\",");
            sb.append("\"body\":\"").append(escapeJson(n.getBody())).append("\"");
            
            if ("inquiry".equals(n.getKind())) {
                sb.append(",\"orderNo\":\"").append(escapeJson(n.getOrderNo())).append("\",");
                sb.append("\"option\":\"").append(escapeJson(n.getOption())).append("\",");
                sb.append("\"answered\":").append(n.isAnswered()).append(",");
                sb.append("\"answer\":\"").append(escapeJson(n.getAnswer())).append("\",");
                sb.append("\"answerDate\":\"").append(escapeJson(n.getAnswerDate())).append("\"");
            } else if ("order".equals(n.getKind())) {
                sb.append(",\"orderNo\":\"").append(escapeJson(n.getOrderNo())).append("\",");
                sb.append("\"orderType\":\"").append(escapeJson(n.getOrderType())).append("\",");
                sb.append("\"payMethod\":\"").append(escapeJson(n.getPayMethod())).append("\",");
                sb.append("\"qty\":").append(n.getQty()).append(",");
                sb.append("\"request\":\"").append(escapeJson(n.getRequest())).append("\"");
            } else if ("review".equals(n.getKind())) {
                sb.append(",\"rating\":").append(n.getRating()).append(",");
                // 💡 [추가]
                sb.append("\"answered\":").append(n.isAnswered()).append(",");
                sb.append("\"replyContent\":\"").append(escapeJson(n.getReplyContent())).append("\",");
                sb.append("\"replyDate\":\"").append(escapeJson(n.getReplyDate())).append("\",");
                
                sb.append("\"images\":[");
                if (n.getImage() != null && !n.getImage().isEmpty()) {
                    sb.append("\"").append(escapeJson(n.getImage())).append("\"");
                }
                sb.append("]");
            }
            sb.append("}");
            if (i < list.size() - 1) sb.append(",");
        }
        sb.append("]");
        response.getWriter().print(sb.toString());
        return null;
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\b", "\\b")
                  .replace("\f", "\\f")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}