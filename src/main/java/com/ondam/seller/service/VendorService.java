package com.ondam.seller.service;

import java.util.Vector;

import com.ondam.seller.dao.VendorDAO;
import com.ondam.seller.dto.VendorDTO;

public class VendorService {

	private VendorDAO dao;

	public VendorService() {
		this.dao = new VendorDAO();
	}

	public Vector<VendorDTO> getVendorList() {
		return dao.getVendor();
	}

	public boolean createVendor(VendorDTO dto) {
		return dao.insertVendor(dto);
	}

	public boolean modifyVendor(VendorDTO dto, int vendorNo) {
		return dao.updateVendor(dto, vendorNo);
	}

	public boolean removeVendor(int vendorNo) {
		return dao.deleteVendor(vendorNo);
	}
}

