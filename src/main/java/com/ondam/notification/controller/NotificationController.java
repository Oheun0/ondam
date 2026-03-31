package com.ondam.notification.controller;

import com.ondam.common.controller.Controller;
import com.ondam.notification.dto.NotificationDTO;
import com.ondam.notification.service.NotificationService;
import com.ondam.user.dto.UserDTO; // 로그인 유저 DTO

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;

public class NotificationController implements Controller {

    private NotificationService notificationService;

    public NotificationController() {
        this.notificationService = new NotificationService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 세션에서 로그인 유저 정보 가져오기
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        // 2. 로그인 여부 체크
        if (loginUser == null) {
            // 로그인 안 되어 있으면 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        // 3. 서비스 호출 (유저 번호 전달)
        int userNo = loginUser.getUserNo();
        Vector<NotificationDTO> vlist = notificationService.getNotificationList(userNo);

        // 4. JSP에 데이터 전달을 위해 request에 저장
        request.setAttribute("vlist", vlist);

        // 5. 뷰 이름 반환
        return "notification/list";
    }
}