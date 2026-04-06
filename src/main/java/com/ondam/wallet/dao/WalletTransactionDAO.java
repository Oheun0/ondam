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
				dto.setUserName(rs.getString("userName"));
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
			sql = "INSERT INTO wallettransaction (walletNo, userNo, userName, transactionType, amount, balanceSnapshot, orderNo, transactionDate, transactionMemo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getWalletNo());
			pstmt.setInt(2, dto.getUserNo());
			pstmt.setString(3, dto.getUserName());
			pstmt.setInt(4, dto.getTransactionType());
			pstmt.setInt(5, dto.getAmount());
			pstmt.setInt(6, dto.getBalanceSnapshot());
			if (dto.getOrderNo() == 0) {
			    pstmt.setNull(7, java.sql.Types.INTEGER);
			} else {
			    pstmt.setInt(7, dto.getOrderNo());
			}
			pstmt.setString(8, dto.getTransactionDate());
			pstmt.setString(9, dto.getTransactionMemo());
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
			sql = "UPDATE WalletTransaction SET walletNo = ?, userNo = ?, userName = ?, transactionType = ?, amount = ?, balanceSnapshot = ?, orderNo = ?, transactionDate = ?, transactionMemo = ? WHERE transactionNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getWalletNo());
			pstmt.setInt(2, dto.getUserNo());
			pstmt.setString(3, dto.getUserName());
			pstmt.setInt(4, dto.getTransactionType());
			pstmt.setInt(5, dto.getAmount());
			pstmt.setInt(6, dto.getBalanceSnapshot());
			pstmt.setInt(7, dto.getOrderNo());
			pstmt.setString(8, dto.getTransactionDate());
			pstmt.setString(9, dto.getTransactionMemo());
			pstmt.setInt(10, transactionNo);
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
	
	// 최근 3건 조회
	public Vector<WalletTransactionDTO> getRecentByWalletNo(int walletNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Vector<WalletTransactionDTO> vlist = new Vector<>();
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT * FROM wallettransaction WHERE walletNo = ? ORDER BY transactionDate DESC LIMIT 3";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, walletNo);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            WalletTransactionDTO dto = new WalletTransactionDTO();
	            dto.setTransactionNo(rs.getInt("transactionNo"));
	            dto.setWalletNo(rs.getInt("walletNo"));
	            dto.setUserNo(rs.getInt("userNo"));
	            dto.setUserName(rs.getString("userName"));
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
	
	// 전체 내역 조회
	public Vector<WalletTransactionDTO> getByWalletNo(int walletNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Vector<WalletTransactionDTO> vlist = new Vector<>();
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT * FROM wallettransaction WHERE walletNo = ? ORDER BY transactionDate DESC";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, walletNo);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            WalletTransactionDTO dto = new WalletTransactionDTO();
	            dto.setTransactionNo(rs.getInt("transactionNo"));
	            dto.setWalletNo(rs.getInt("walletNo"));
	            dto.setUserNo(rs.getInt("userNo"));
	            dto.setUserName(rs.getString("userName"));
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
}