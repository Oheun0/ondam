package com.ondam.wallet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.wallet.dto.WalletTransactionDTO;

public class WalletTransactionDAO {

	private DBConnectionMgr pool;

	public WalletTransactionDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<WalletTransactionDTO> getWalletTransaction() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<WalletTransactionDTO> vlist = new Vector<WalletTransactionDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM walletTransaction";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				WalletTransactionDTO dto = new WalletTransactionDTO();
				dto.setTransactionNo(rs.getInt("transactionNo"));
				dto.setWalletNo(rs.getInt("walletNo"));
				dto.setUserNo(rs.getInt("userNo"));
				dto.setTransactionType(rs.getInt("transactionType"));
				dto.setAmount(rs.getInt("amount"));
				dto.setBalanceSnapshot(rs.getInt("balanceSnapshot"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setTransactionDate(rs.getString("transactionDate"));
				dto.setTransactionMemo(rs.getString("transactionMemo"));
				vlist.addElement(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}

	// Insert
	public boolean insertWalletTransaction(WalletTransactionDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT WalletTransaction (walletNo, userNo, transactionType, amount, balanceSnapshot, orderNo, transactionDate, transactionMemo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getWalletNo());
			pstmt.setInt(2, dto.getUserNo());
			pstmt.setInt(3, dto.getTransactionType());
			pstmt.setInt(4, dto.getAmount());
			pstmt.setInt(5, dto.getBalanceSnapshot());
			pstmt.setInt(6, dto.getOrderNo());
			pstmt.setString(7, dto.getTransactionDate());
			pstmt.setString(8, dto.getTransactionMemo());
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	// Update
	public boolean updateWalletTransaction(WalletTransactionDTO dto, int transactionNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE WalletTransaction SET walletNo = ?, userNo = ?, transactionType = ?, amount = ?, balanceSnapshot = ?, orderNo = ?, transactionDate = ?, transactionMemo = ? WHERE transactionNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getWalletNo());
			pstmt.setInt(2, dto.getUserNo());
			pstmt.setInt(3, dto.getTransactionType());
			pstmt.setInt(4, dto.getAmount());
			pstmt.setInt(5, dto.getBalanceSnapshot());
			pstmt.setInt(6, dto.getOrderNo());
			pstmt.setString(7, dto.getTransactionDate());
			pstmt.setString(8, dto.getTransactionMemo());
			pstmt.setInt(9, transactionNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	// Delete
	public boolean deleteWalletTransaction(int transactionNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM WalletTransaction WHERE transactionNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, transactionNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
}

