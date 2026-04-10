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
	
	// SellerService.java 에 추가
	public SellerDTO login(String sellerId, String sellerPwd) {
	    return dao.loginSeller(sellerId, sellerPwd);
	}
	
	// SellerService.java 에 추가
	public boolean registerSeller(VendorDTO vDto, SellerDTO sDto) {
	    // 1. 업체(Vendor) 정보를 먼저 DB에 넣고 생성된 번호(vendorNo)를 받아옵니다.
	    // VendorDAO에 insertVendorAndGetNo(vDto) 메서드를 미리 만들어둬야 합니다.
	    int generatedVendorNo = vendorDao.insertVendorAndGetNo(vDto); 

	    if (generatedVendorNo > 0) {
	        // 2. 받아온 업체 번호를 판매자 계정 정보에 심어줍니다.
	        sDto.setVendorNo(generatedVendorNo);
	        // 3. 판매자(Seller) 계정을 생성합니다.
	        return dao.insertSeller(sDto);
	    }
	    return false;
	}
	// 아이디 찾기 서비스 메서드
	public String findSellerId(String repName, String email) {
	    return dao.findSellerIdByVendorInfo(repName, email);
	}
	
	// 비밀번호 재설정 - 1. 본인 확인 (서비스에서 DAO 두 개를 조합!)
	public boolean verifySellerForReset(String sellerId, String inputEmail) {
	    // 1) 아이디로 업체 번호 조회
	    int vendorNo = dao.getVendorNoBySellerId(sellerId);
	    
	    // 업체 번호가 0이면(없는 아이디면) 실패
	    if (vendorNo == 0) {
	        return false;
	    }
	    
	    // 2) 찾아온 업체 번호로 DB에 저장된 이메일 조회
	    String dbEmail = vendorDao.getEmailByVendorNo(vendorNo);
	    
	    // 3) DB 이메일과 사용자가 입력한 이메일이 완벽히 일치하는지 비교
	    if (dbEmail != null && dbEmail.equals(inputEmail)) {
	        return true;
	    }
	    
	    return false;
	}

	// 비밀번호 재설정 - 2. 새 비밀번호 저장
	public boolean resetPassword(String sellerId, String newPassword) {
	    return dao.updatePassword(sellerId, newPassword);
	}
}

