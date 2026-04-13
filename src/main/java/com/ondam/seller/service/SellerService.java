package com.ondam.seller.service;

import java.util.Vector;

import com.ondam.seller.dao.SellerDAO;
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
	    if (dbEmail != null && dbEmail.equals(inputEmail)) {
	        return true;
	    }
	    
	    return false;
	}
	public boolean resetPassword(String sellerId, String newPassword) {
	    return dao.updatePassword(sellerId, newPassword);
	}
	
	public java.util.Map<String, Integer> getDashboardStats(int vendorNo) {
	    java.util.Map<String, Integer> stats = new java.util.HashMap<>();
	    
	    // 나중에 실제 DAO(DB)를 연결할 자리 
	    stats.put("todayOrderCount", 12);
	    stats.put("shipReadyCount", 5);
	    stats.put("inquiryCount", 3);
	    stats.put("reviewCount", 4);
	    
	    return stats;
	}
}

