package com.ondam.review.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import com.ondam.common.ProjectWebappPaths;
import com.ondam.common.controller.Controller;
import com.ondam.review.dto.ReviewDTO;
import com.ondam.review.dto.ReviewImageDTO;
import com.ondam.review.service.ReviewService;
import com.ondam.user.dto.UserDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

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
        
        if ("increaseHelpful".equals(action)) {
            increaseHelpful(request, response);
            return null;
        }

        switch (action) {
	        case "writeForm":
	            return writeForm(request, response);
	        case "updateForm":
                return updateForm(request, response);
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
        if (loginUser == null) return "redirect:/login";

        File uploadDir = ProjectWebappPaths.uploadsReviewsDirectory(request.getServletContext());
        String savePath = uploadDir.getAbsolutePath();
        if (!uploadDir.exists()) uploadDir.mkdirs();

        try {
            ReviewDTO dto = new ReviewDTO();
            dto.setOrderItemNo(Integer.parseInt(request.getParameter("orderItemNo")));
            dto.setUserNo(loginUser.getUserNo());
            dto.setReviewRating(Integer.parseInt(request.getParameter("reviewRating")));
            dto.setReviewContent(request.getParameter("reviewContent"));
            dto.setIsBodyPublic(Integer.parseInt(request.getParameter("isBodyPublic")));
            
            int reviewNo = reviewService.writeReview(dto);
            if (reviewNo > 0) {
                int order = 1;
                for (jakarta.servlet.http.Part part : request.getParts()) {
                    if ("reviewPhotos".equals(part.getName()) && part.getSize() > 0) {
                        String submittedFileName = part.getSubmittedFileName();
                        String uniqueFileName = java.util.UUID.randomUUID().toString() + "_" + submittedFileName;
                        part.write(savePath + java.io.File.separator + uniqueFileName);
                        reviewService.saveReviewImage(reviewNo, uniqueFileName, order++);
                    }
                }
                return "redirect:/review?action=myList&tab=written";
            } else {
                return "redirect:/review?action=writeForm&orderItemNo=" + request.getParameter("orderItemNo");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/review?action=myList";
        }
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
        int userNo = loginUser.getUserNo();
        Vector<ReviewDTO> writtenList = reviewService.getReviewsByUserNo(userNo);
        Vector<ReviewDTO> writeableList = reviewService.getWriteableReviews(userNo);
        
        request.setAttribute("writtenList", writtenList);
        request.setAttribute("writeableList", writeableList);
        return "product/review/my-review"; 
    }

    private String update(HttpServletRequest request, HttpServletResponse response) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        
        try {
            ReviewDTO dto = new ReviewDTO();
            dto.setReviewNo(Integer.parseInt(request.getParameter("reviewNo")));
            dto.setUserNo(loginUser.getUserNo());
            dto.setReviewRating(Integer.parseInt(request.getParameter("reviewRating")));
            dto.setReviewContent(request.getParameter("reviewContent"));
            dto.setIsBodyPublic(Integer.parseInt(request.getParameter("isBodyPublic")));
            //삭제할 사진 번호
            File uploadDir = ProjectWebappPaths.uploadsReviewsDirectory(request.getServletContext());
            String savePath = uploadDir.getAbsolutePath();
            
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); 
            }

            // 삭제할 사진 처리
            String deleteImgNos = request.getParameter("deleteImgNos");
            if (deleteImgNos != null && !deleteImgNos.trim().isEmpty()) {
                String[] nos = deleteImgNos.split(",");
                for (String no : nos) {
                    int imgNo = Integer.parseInt(no.trim());
                    reviewService.removeReviewImage(imgNo, savePath);
                }
            }
            boolean result = reviewService.editMyReview(dto);
            if (result) {
                int order = reviewService.getNextImgOrder(dto.getReviewNo());

                for (jakarta.servlet.http.Part part : request.getParts()) {
                    if ("reviewPhotos".equals(part.getName()) && part.getSize() > 0) {
                        String fileName = java.util.UUID.randomUUID() + "_" + part.getSubmittedFileName();
                        part.write(savePath + File.separator + fileName);
                        reviewService.saveReviewImage(dto.getReviewNo(), fileName, order++);
                    }
                }
            }
            return "redirect:/review?action=myList";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/review?action=myList";
        }
    }

    private String delete(HttpServletRequest request, HttpServletResponse response) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
        
        reviewService.deleteMyReview(reviewNo, loginUser.getUserNo());
        return "redirect:/review?action=myList";
    }
    
    private String writeForm(HttpServletRequest request, HttpServletResponse response) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        String orderItemNoStr = request.getParameter("orderItemNo");
        if (orderItemNoStr == null || orderItemNoStr.trim().isEmpty()) {
            return "redirect:/review?action=myList"; 
        }
        int orderItemNo = Integer.parseInt(orderItemNoStr);
        ReviewDTO itemInfo = reviewService.getOrderProductByNo(orderItemNo, loginUser.getUserNo());
        
        request.setAttribute("itemInfo", itemInfo);
        request.setAttribute("orderItemNo", orderItemNo);
        
        return "product/review/review-write";
    }
    
 // 수정 화면으로 이동
    private String updateForm(HttpServletRequest request, HttpServletResponse response) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
        ReviewDTO review = reviewService.getReviewByNo(reviewNo, loginUser.getUserNo());
        Vector<ReviewImageDTO> imageList = reviewService.getReviewImages(reviewNo);

        request.setAttribute("reviewDTO", review);
        request.setAttribute("imageList", imageList);

        return "product/review/review-write";
    }
    
 //도움돼요 갯수 증가 처리 메서드
    private void increaseHelpful(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        java.io.PrintWriter out = response.getWriter();
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        
        if (loginUser == null) {
            out.print("{\"status\":\"login_required\"}");
            return;
        }
        
        try {
            int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
            boolean isSuccess = reviewService.increaseReviewHelpful(reviewNo, loginUser.getUserNo());
            
            if (isSuccess) {
                out.print("{\"status\":\"success\"}");
            } else {
                out.print("{\"status\":\"already_done\"}");
            }
        } catch (Exception e) {
            out.print("{\"status\":\"error\"}");
        } finally {
            out.flush();
            out.close();
        }
    }
}