package com.ondam.wallet.service;

import java.util.Vector;

import com.ondam.wallet.dao.WalletTransactionDAO;
import com.ondam.wallet.dto.WalletTransactionDTO;

public class WalletTransactionService {

	private WalletTransactionDAO dao;

	public WalletTransactionService() {
		this.dao = new WalletTransactionDAO();
	}

	public Vector<WalletTransactionDTO> getWalletTransactionList() {
		return dao.getWalletTransaction();
	}

	public boolean createWalletTransaction(WalletTransactionDTO dto) {
		return dao.insertWalletTransaction(dto);
	}

	public boolean modifyWalletTransaction(WalletTransactionDTO dto, int transactionNo) {
		return dao.updateWalletTransaction(dto, transactionNo);
	}

	public boolean removeWalletTransaction(int transactionNo) {
		return dao.deleteWalletTransaction(transactionNo);
	}
}

