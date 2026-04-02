package com.ondam.user.controller;

import java.io.File;
import java.util.UUID;

import com.ondam.common.controller.Controller;
import com.ondam.user.dao.UserDAO;
import com.ondam.user.dto.UserDTO; // 🚩 DTO 꼭 import 해주세요!
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

public class ProfileUpdateController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        UserDTO sessionUser = (UserDTO) session.getAttribute("loginUser");

        if (sessionUser == null) {
            return "redirect:/login";
        }

        int userNo = sessionUser.getUserNo(); 

        String userName = request.getParameter("userName");
        String birthDate = request.getParameter("birthDate");
        String genderStr = request.getParameter("gender");
        String phone = request.getParameter("phone");

        int gender = 0;
        if (genderStr != null && !genderStr.isEmpty()) {
            gender = Integer.parseInt(genderStr); 
        }

        Part filePart = request.getPart("profileImage");
        String profileImgName = sessionUser.getUserProfileImg();

        if (filePart != null && filePart.getSize() > 0) {
            String uploadPath = request.getServletContext().getRealPath("/images/profile");

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            String originalFileName = filePart.getSubmittedFileName();
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

            filePart.write(uploadPath + File.separator + uniqueFileName);

            profileImgName = uniqueFileName;
        }

        UserDAO dao = new UserDAO();
        int result = dao.updateUserProfile(userNo, userName, birthDate, gender, phone, profileImgName);

        if (result > 0) {
        	UserDTO updatedUser = dao.getUserId(sessionUser.getUserId());
            session.setAttribute("loginUser", updatedUser);
            return "redirect:/profile"; 
        } else {
            request.setAttribute("errorMessage", "정보 수정에 실패했습니다.");
            return "mypage/profile";
        }
    }
}