package com.ondam.wallet.service;

import java.util.Vector;

import com.ondam.wallet.dao.WalletDAO;
import com.ondam.wallet.dto.WalletDTO;

public class WalletService {

	private WalletDAO dao;

	public WalletService() {
		this.dao = new WalletDAO();
	}

	public Vector<WalletDTO> getWalletList() {
		return dao.getWallet();
	}

	public boolean createWallet(WalletDTO dto) {
		return dao.insertWallet(dto);
	}

	public boolean modifyWallet(WalletDTO dto, int walletNo) {
		return dao.updateWallet(dto, walletNo);
	}

	public boolean removeWallet(int walletNo) {
		return dao.deleteWallet(walletNo);
	}
	
	public WalletDTO getWalletByFamilyNo(int familyNo) {
	    return dao.getWalletByFamilyNo(familyNo);
	}
	
	public boolean updateBalance(int walletNo, int newBalance) {
	    return dao.updateBalance(walletNo, newBalance);
	}
}