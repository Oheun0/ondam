package com.ondam.seller.service;

import java.util.Map;
import java.util.Vector;

import com.ondam.seller.dao.SellerDAO;
import com.ondam.seller.dao.SellerDashboardDAO;
import com.ondam.seller.dao.VendorDAO;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.seller.dto.VendorDTO;

public class SellerService {

	private SellerDAO dao;
	private VendorDAO vendorDao = new VendorDAO();
	public SellerService() {
		this.dao = new SellerDAO();
	}

	public Vector<SellerDTO> getSellerList() {
		return dao.getSeller();
	}

	public boolean createSeller(SellerDTO dto) {
		return dao.insertSeller(dto);
	}

	public boolean modifySeller(SellerDTO dto, int sellerAccountNo) {
		return dao.updateSeller(dto, sellerAccountNo);
	}

	public boolean removeSeller(int sellerAccountNo) {
		return dao.deleteSeller(sellerAccountNo);
	}
	
	public SellerDTO login(String sellerId, String sellerPwd) {
	    if (sellerId != null) {
	        sellerId = sellerId.trim();
	    }
	    if (sellerPwd != null) {
	        sellerPwd = sellerPwd.trim();
	    }
	    return dao.loginSeller(sellerId, sellerPwd);
	}

	public boolean registerSeller(VendorDTO vDto, SellerDTO sDto) {
	    int generatedVendorNo = vendorDao.insertVendorAndGetNo(vDto); 

	    if (generatedVendorNo > 0) {
	        sDto.setVendorNo(generatedVendorNo);
	        return dao.insertSeller(sDto);
	    }
	    return false;
	}
	public String findSellerId(String repName, String email) {
	    return dao.findSellerIdByVendorInfo(repName, email);
	}
	public boolean verifySellerForReset(String sellerId, String inputEmail) {
	    int vendorNo = dao.getVendorNoBySellerId(sellerId);
	    if (vendorNo == 0) {
	        return false;
	    }
	    String dbEmail = vendorDao.getEmailByVendorNo(vendorNo);
	    
	    // 3) DB 이메일과 사용자가 입력한 이메일 비교 (대소문자 무시)
	    if (dbEmail != null && inputEmail != null
	    		&& dbEmail.trim().equalsIgnoreCase(inputEmail.trim())) {
	        return true;
	    }
	    
	    return false;
	}
	public boolean resetPassword(String sellerId, String newPassword) {
	    return dao.updatePassword(sellerId, newPassword);
	}
	
	public Map<String, Object> getDashboardStats(int vendorNo) {
        SellerDashboardDAO dashDao = new SellerDashboardDAO();
        return dashDao.getDashboardStats(vendorNo);
    }
	
}

