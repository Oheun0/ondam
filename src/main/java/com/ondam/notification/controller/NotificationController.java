package com.ondam.notification.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.notification.dto.NotificationDTO;
import com.ondam.notification.service.NotificationService;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class NotificationController implements Controller {

    private NotificationService notificationService = new NotificationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":        return list(request, response);
            case "markOneRead": return markOneRead(request, response);
            case "markAllRead": return markAllRead(request, response);
            case "deleteAll":   return deleteAll(request, response);
            default:            return "redirect:/notification";
        }
    }
    
    private String list(HttpServletRequest request, HttpServletResponse response) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        Vector<NotificationDTO> vlist = notificationService.getNotificationList(loginUser.getUserNo());
        request.setAttribute("vlist", vlist);
        return "notification/list";
    }

//    private String markOneRead(HttpServletRequest request, HttpServletResponse response) {
//        String no = request.getParameter("notificationNo");
//        if (no != null) notificationService.markOneRead(Integer.parseInt(no));
//        return "redirect:/notification";
//    }
    
    private String markOneRead(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String no = request.getParameter("notificationNo");
        if (no != null) notificationService.markOneRead(Integer.parseInt(no));
        response.setContentType("application/json");
        response.getWriter().write("{\"ok\":true}");
        return null; // DispatcherServlet 뷰 처리 건너뜀
    }
    
    private String markAllRead(HttpServletRequest request, HttpServletResponse response) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        notificationService.markAllRead(loginUser.getUserNo());
        return "redirect:/notification";
    }

    private String deleteAll(HttpServletRequest request, HttpServletResponse response) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        notificationService.removeAllNotification(loginUser.getUserNo());
        return "redirect:/notification";
    }
}