package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.notification.dto.NotificationDTO;
import com.ondam.notification.service.NotificationService;
import com.ondam.user.dto.UserAddressDTO;
import com.ondam.user.dto.UserDTO;
import com.ondam.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddressSaveController implements Controller {
    
    private NotificationService notificationService = new NotificationService();
    private UserService userService = new UserService();
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 로그인 체크
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        
        if (loginUser == null) {
            return "redirect:/login";
        }
        
        // 2. 파라미터 수집
        String mode = request.getParameter("mode");
        String addressName = request.getParameter("addressName");
        String receiverName = request.getParameter("receiverName");
        String receiverTel = request.getParameter("receiverTel"); 
        String userZipcode = request.getParameter("userZipcode");
        String userAddress = request.getParameter("userAddress");
        String userDetailAddress = request.getParameter("userDetailAddress");
        
        // 체크박스는 체크 안 하면 null이 오므로 1 또는 0으로 변환
        int isDefault = request.getParameter("isDefault") != null ? 1 : 0;
        
        // 도움 주기 모드 확인
        String targetUserNoParam = request.getParameter("targetUserNo");
        int targetUserNo = (targetUserNoParam != null && !targetUserNoParam.isEmpty())
            ? Integer.parseInt(targetUserNoParam)
            : loginUser.getUserNo();

        // 3. DTO 객체 생성 및 데이터 세팅
        UserAddressDTO dto = new UserAddressDTO();
        dto.setUserNo(targetUserNo);
        dto.setAddressName(addressName);
        dto.setReceiverName(receiverName);
        dto.setReceiverTel(receiverTel);
        dto.setUserZipcode(userZipcode);
        dto.setUserAddress(userAddress);
        dto.setUserDetailAddress(userDetailAddress);
        dto.setIsDefault(isDefault);
        
        // 수정 모드일 경우 PK 값 세팅
        if ("edit".equals(mode)) {
            String addressNoStr = request.getParameter("userAddressNo");
            if (addressNoStr != null && !addressNoStr.isEmpty()) {
                dto.setUserAddressNo(Integer.parseInt(addressNoStr));
            }
        }

        // 4. 서비스 호출 (비즈니스 로직 실행: 첫 배송지 체크, 기존 기본 배송지 초기화 등)
        int result = userService.saveUserAddress(dto, mode);
        
        // 5. 결과에 따른 처리
        if (result > 0) {
            // 도움 모드일 때만 상대방에게 알림 발송
            if (targetUserNoParam != null && !targetUserNoParam.isEmpty()) {
                NotificationDTO notiDto = new NotificationDTO();
                notiDto.setUserNo(targetUserNo);
                notiDto.setNotificationType(4);
                notiDto.setNotificationContent("\"" + loginUser.getUserName() + "\"님이 배송지를 " + ("edit".equals(mode) ? "수정" : "추가") + "해주셨어요!");
                notiDto.setRefNo(0);
                notiDto.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toString());
                
                notificationService.createNotification(notiDto);
                
                return "redirect:/profile-address?targetUserNo=" + targetUserNoParam;
            }
            return "redirect:/profile-address";
        } else {
            return "redirect:/error"; 
        }
    }
}