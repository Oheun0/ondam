package com.ondam.inquiry.controller;

import java.util.Vector;
import com.ondam.common.controller.Controller;
import com.ondam.user.dto.UserDTO;
import com.ondam.inquiry.service.InquiryService;
import com.ondam.inquiry.dto.InquiryDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class InquiryController implements Controller {
    private InquiryService inquiryService = new InquiryService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }

        String action = request.getParameter("action");
        if (action == null) action = "list"; 

        switch (action) {
            case "list": return list(request, response);
            case "writeForm": return writeForm(request, response);
            case "write": return write(request, response);
            case "editForm": return editForm(request, response);
            case "edit": return edit(request, response);
            case "delete": return delete(request, response);
            default: return "redirect:/inquiry?action=list";
        }
    }

    // 1. 문의 목록 보기
    private String list(HttpServletRequest request, HttpServletResponse response) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        Vector<InquiryDTO> inquiryList = inquiryService.getMyInquiries(loginUser.getUserNo());
        request.setAttribute("inquiryList", inquiryList);
        return "product/inquiry/inquiry-list";
    }

    // 2. 문의 작성 페이지 이동
    private String writeForm(HttpServletRequest request, HttpServletResponse response) {
        int productNo = Integer.parseInt(request.getParameter("productNo"));
        request.setAttribute("productInfo", inquiryService.getProductInfo(productNo));
        return "product/inquiry/inquiry-write";
    }

    // 3. 문의 저장 (AJAX)
    private String write(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InquiryDTO dto = new InquiryDTO();
        dto.setProductNo(Integer.parseInt(request.getParameter("productNo")));
        dto.setUserNo(((UserDTO)request.getSession().getAttribute("loginUser")).getUserNo());
        dto.setInquiryContent(request.getParameter("inquiryContent"));
        dto.setIsSecret(Integer.parseInt(request.getParameter("isSecret")));
        dto.setIsNameHidden(Integer.parseInt(request.getParameter("isNameHidden")));
        
        String orderNoStr = request.getParameter("orderNo");
        if (orderNoStr != null && !orderNoStr.isEmpty() && !orderNoStr.equals("null")) {
            dto.setOrderNo(Integer.parseInt(orderNoStr));
        }

        boolean success = inquiryService.insertInquiry(dto);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print("{\"success\": " + success + "}");
        return null;
    }

    // 4. 문의 수정 페이지 이동
    private String editForm(HttpServletRequest request, HttpServletResponse response) {
        int inquiryNo = Integer.parseInt(request.getParameter("inquiryNo"));
        InquiryDTO inquiryData = inquiryService.getInquiryDetail(inquiryNo);
        
        request.setAttribute("inquiryData", inquiryData);
        request.setAttribute("productInfo", inquiryData); // 상품 정보도 같이 담겨있음
        request.setAttribute("returnUrl", request.getParameter("returnUrl"));
        return "product/inquiry/inquiry-write";
    }

    // 5. 문의 수정 처리 (AJAX)
    private String edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InquiryDTO dto = new InquiryDTO();
        dto.setInquiryNo(Integer.parseInt(request.getParameter("inquiryNo")));
        dto.setInquiryContent(request.getParameter("inquiryContent"));
        dto.setIsSecret(Integer.parseInt(request.getParameter("isSecret")));
        dto.setIsNameHidden(Integer.parseInt(request.getParameter("isNameHidden")));

        boolean success = inquiryService.updateInquiry(dto);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print("{\"success\": " + success + "}");
        return null;
    }

    // 6. 문의 삭제 처리
    private String delete(HttpServletRequest request, HttpServletResponse response) {
        int inquiryNo = Integer.parseInt(request.getParameter("inquiryNo"));
        inquiryService.deleteInquiry(inquiryNo);
        return "redirect:/inquiry?action=list";
    }
}