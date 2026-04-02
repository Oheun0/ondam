package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.user.dao.UserAddressDAO;
import com.ondam.user.dto.UserAddressDTO;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddressDeleteController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login";

        String addressIdStr = request.getParameter("addressId");
        
        if (addressIdStr != null && !addressIdStr.isEmpty()) {
            int addressNo = Integer.parseInt(addressIdStr);
            UserAddressDAO dao = new UserAddressDAO();
            
            UserAddressDTO addr = dao.getAddressByNo(addressNo);
            
            if (addr != null && addr.getIsDefault() == 1) {
                return "redirect:/profile-address";
            }
            dao.deleteUserAddress(addressNo);
        }
        return "redirect:/profile-address";
    }
}