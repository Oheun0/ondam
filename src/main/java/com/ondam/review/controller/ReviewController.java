package com.ondam.review.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.review.dto.ReviewDTO;
import com.ondam.review.service.ReviewService;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ReviewController implements Controller {

    private ReviewService reviewService = new ReviewService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "myList";
        }

        switch (action) {
            case "write":
                return write(request, response);
            case "otherList":
                return otherList(request, response);
            case "otherListByItem":
                return otherListByItem(request, response);
            case "myList":
                return myList(request, response);
            case "update":
                return update(request, response);
            case "delete":
                return delete(request, response);
            default:
                return "redirect:/review";
        }
    }

    private String write(HttpServletRequest request, HttpServletResponse response) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        
        ReviewDTO dto = new ReviewDTO();
        dto.setOrderItemNo(Integer.parseInt(request.getParameter("orderItemNo")));
        dto.setUserNo(loginUser.getUserNo());
        dto.setReviewRating(Integer.parseInt(request.getParameter("reviewRating")));
        dto.setReviewContent(request.getParameter("reviewContent"));
        dto.setIsBodyPublic(Integer.parseInt(request.getParameter("isBodyPublic")));
        dto.setCreatedAt(String.valueOf(System.currentTimeMillis()));
        dto.setUpdatedAt(String.valueOf(System.currentTimeMillis()));

        reviewService.writeReview(dto);
        return "redirect:/review?action=myList";
    }

    private String otherList(HttpServletRequest request, HttpServletResponse response) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        Vector<ReviewDTO> vlist = reviewService.getOtherUsersReviews(loginUser.getUserNo());
        request.setAttribute("reviewList", vlist);
        return "review/otherList";
    }

    private String otherListByItem(HttpServletRequest request, HttpServletResponse response) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        int orderItemNo = Integer.parseInt(request.getParameter("orderItemNo"));
        
        Vector<ReviewDTO> vlist = reviewService.getOtherUsersReviewsByItem(orderItemNo, loginUser.getUserNo());
        request.setAttribute("reviewList", vlist);
        return "review/itemList";
    }

    private String myList(HttpServletRequest request, HttpServletResponse response) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        Vector<ReviewDTO> vlist = reviewService.getMyReviews(loginUser.getUserNo());
        request.setAttribute("reviewList", vlist);
        return "review/myList";
    }

    private String update(HttpServletRequest request, HttpServletResponse response) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        
        ReviewDTO dto = new ReviewDTO();
        dto.setReviewNo(Integer.parseInt(request.getParameter("reviewNo")));
        dto.setUserNo(loginUser.getUserNo());
        dto.setReviewRating(Integer.parseInt(request.getParameter("reviewRating")));
        dto.setReviewContent(request.getParameter("reviewContent"));
        dto.setIsBodyPublic(Integer.parseInt(request.getParameter("isBodyPublic")));
        dto.setUpdatedAt(String.valueOf(System.currentTimeMillis()));

        reviewService.editMyReview(dto);
        return "redirect:/review?action=myList";
    }

    private String delete(HttpServletRequest request, HttpServletResponse response) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
        
        reviewService.deleteMyReview(reviewNo, loginUser.getUserNo());
        return "redirect:/review?action=myList";
    }
}