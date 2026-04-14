package com.ondam.review.controller;

import java.io.File;
import java.util.UUID;

import com.ondam.common.ProjectWebappPaths;
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

        File uploadDir = ProjectWebappPaths.uploadsReviewsDirectory(request.getServletContext());
        String uploadPath = uploadDir.getAbsolutePath();
        
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        int order = 1;

        for (Part part : request.getParts()) {
            String partName = part.getName();

            if ("reviewPhotos".equals(partName) && part.getSize() > 0) {
                String submittedFileName = part.getSubmittedFileName();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + submittedFileName;

                part.write(uploadPath + File.separator + uniqueFileName);
                ReviewImageDTO dto = new ReviewImageDTO();
                dto.setReviewNo(reviewNo);
                dto.setReviewImg(uniqueFileName); 
                dto.setImgOrder(order++);
                
                reviewImageService.uploadReviewImage(dto);
            }
        }
        System.out.println("★ 실제 파일이 저장되는 물리 경로: " + uploadPath);
        
        return "redirect:/review?action=myList";
    }

    private String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int reviewImgNo = Integer.parseInt(request.getParameter("reviewImgNo"));
        ReviewImageDTO dto = reviewImageService.getReviewImage(reviewImgNo);
        
        if (dto != null && dto.getReviewImg() != null) {
            File file = new File(
                    ProjectWebappPaths.uploadsReviewsDirectory(request.getServletContext()),
                    dto.getReviewImg());
            if (file.exists()) {
                file.delete();
            }
            reviewImageService.removeReviewImage(reviewImgNo);
        }
        return "redirect:/review?action=myList";
    }
}