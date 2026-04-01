package com.ondam.seller.service;

import java.util.Vector;

import com.ondam.seller.dao.SellerDAO;
import com.ondam.seller.dto.SellerDTO;

public class SellerService {

	private SellerDAO dao;

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
}

