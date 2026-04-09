package com.ondam.user.service;

import java.util.List;

import com.ondam.user.dao.UserAddressDAO;
import com.ondam.user.dto.UserAddressDTO;

public class UserAddressService {

    private UserAddressDAO dao = new UserAddressDAO();

    public UserAddressDTO getDefaultAddress(int userNo) {
        return dao.getDefaultAddress(userNo);
    }
    
    public List<UserAddressDTO> getAddressListByUser(int userNo) {
    	return dao.getAddressListByUser(userNo);
    }
}