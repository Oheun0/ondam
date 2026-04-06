package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.user.dao.UserAddressDAO;
import com.ondam.user.dto.UserAddressDTO;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddressSaveController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        
        if (loginUser == null) {
            return "redirect:/login";
        }
        
        String mode = request.getParameter("mode");
        String addressName = request.getParameter("addressName");
        String receiverName = request.getParameter("receiverName");
        String receiverTel = request.getParameter("receiverTel"); 
        String userZipcode = request.getParameter("userZipcode");
        String userAddress = request.getParameter("userAddress");
        String userDetailAddress = request.getParameter("userDetailAddress");
        
        int isDefault = request.getParameter("isDefault") != null ? 1 : 0;
        
        // 배송지 수정 도와주기에서 추가된 코드
        String targetUserNoParam = request.getParameter("targetUserNo");
        int targetUserNo = (targetUserNoParam != null && !targetUserNoParam.isEmpty())
            ? Integer.parseInt(targetUserNoParam)
            : loginUser.getUserNo();

        UserAddressDTO dto = new UserAddressDTO();
        dto.setUserNo(targetUserNo);
        dto.setAddressName(addressName);
        dto.setReceiverName(receiverName);
        dto.setReceiverTel(receiverTel);
        dto.setUserZipcode(userZipcode);
        dto.setUserAddress(userAddress);
        dto.setUserDetailAddress(userDetailAddress);
        dto.setIsDefault(isDefault);
        
        UserAddressDAO dao = new UserAddressDAO();

        if ("edit".equals(mode)) {
            String addressNoStr = request.getParameter("userAddressNo");
            if (addressNoStr != null && !addressNoStr.isEmpty()) {
                int addressNo = Integer.parseInt(addressNoStr);
                dto.setUserAddressNo(addressNo);
                if (isDefault == 1) {
                    dao.updateDefaultAddress(targetUserNo, addressNo);
                }
                
                dao.updateUserAddress(dto); 
            }
        } else {
            if (isDefault == 1) {
                dao.resetDefaultAddress(targetUserNo);
            }
            dao.insertUserAddress(dto); 
        }
        
        if (targetUserNoParam != null && !targetUserNoParam.isEmpty()) {
            return "redirect:/profile-address?targetUserNo=" + targetUserNoParam;
        }
        return "redirect:/profile-address";
    }
}