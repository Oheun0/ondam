package com.ondam.review.controller;

import java.io.File;
import java.util.UUID;

import com.ondam.common.controller.Controller;
import com.ondam.review.dto.ReviewImageDTO;
import com.ondam.review.service.ReviewImageService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

public class ReviewImageController implements Controller {

    private ReviewImageService reviewImageService = new ReviewImageService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }

        String action = request.getParameter("action");
        if (action == null) {
            return "redirect:/review";
        }

        switch (action) {
            case "upload":
                return upload(request, response);
            case "delete":
                return delete(request, response);
            default:
                return "redirect:/review";
        }
    }

    private String upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
        
        // WAS(Tomcat) 구동 환경의 실제 디렉토리 경로 매핑
        String relativePath = "/uploads/reviews";
        String uploadPath = request.getServletContext().getRealPath(relativePath);
        
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        int order = 1;
        // Front Controller(Servlet)에 반드시 @MultipartConfig 어노테이션이 선언되어 있어야 getParts()가 동작함
        for (Part part : request.getParts()) {
            String partName = part.getName();
            if ("reviewImg".equals(partName) && part.getSize() > 0) {
                String submittedFileName = part.getSubmittedFileName();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + submittedFileName;
                
                // 디스크에 물리적 파일 저장
                part.write(uploadPath + File.separator + uniqueFileName);

                // DB에 저장될 경로는 웹 접근 가능 경로
                ReviewImageDTO dto = new ReviewImageDTO();
                dto.setReviewNo(reviewNo);
                dto.setReviewImg(relativePath + "/" + uniqueFileName); 
                dto.setImgOrder(order++);
                
                reviewImageService.uploadReviewImage(dto);
            }
        }
        
        return "redirect:/review?action=myList";
    }

    private String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int reviewImgNo = Integer.parseInt(request.getParameter("reviewImgNo"));
        
        // 물리 파일 삭제를 위해 DB에서 파일 경로 조회
        ReviewImageDTO dto = reviewImageService.getReviewImage(reviewImgNo);
        if (dto != null && dto.getReviewImg() != null) {
            String realPath = request.getServletContext().getRealPath(dto.getReviewImg());
            File file = new File(realPath);
            if (file.exists()) {
                file.delete(); // 디스크에서 파일 제거
            }
            // DB 레코드 삭제
            reviewImageService.removeReviewImage(reviewImgNo);
        }
        
        return "redirect:/review?action=myList";
    }
}