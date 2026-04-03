package com.ondam.wallet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.wallet.dto.WalletDTO;

public class WalletDAO {

	private DBConnectionMgr pool;

	public WalletDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<WalletDTO> getWallet() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<WalletDTO> vlist = new Vector<WalletDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM wallet";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				WalletDTO dto = new WalletDTO();
				dto.setWalletNo(rs.getInt("walletNo"));
				dto.setFamilyNo(rs.getInt("familyNo"));
				dto.setBalance(rs.getInt("balance"));
				dto.setCreatedAt(rs.getString("createdAt"));
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
	public boolean insertWallet(WalletDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Wallet (familyNo, balance, createdAt) VALUES (?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getFamilyNo());
			pstmt.setInt(2, dto.getBalance());
			pstmt.setString(3, dto.getCreatedAt());
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
	public boolean updateWallet(WalletDTO dto, int walletNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Wallet SET familyNo = ?, balance = ?, createdAt = ? WHERE walletNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getFamilyNo());
			pstmt.setInt(2, dto.getBalance());
			pstmt.setString(3, dto.getCreatedAt());
			pstmt.setInt(4, walletNo);
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
	public boolean deleteWallet(int walletNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Wallet WHERE walletNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, walletNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	// familyNo로 조회
	public WalletDTO getWalletByFamilyNo(int familyNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    WalletDTO dto = null;
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT * FROM wallet WHERE familyNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, familyNo);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            dto = new WalletDTO();
	            dto.setWalletNo(rs.getInt("walletNo"));
	            dto.setFamilyNo(rs.getInt("familyNo"));
	            dto.setBalance(rs.getInt("balance"));
	            dto.setCreatedAt(rs.getString("createdAt"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return dto;
	}
	
	// 잔액 업데이트
	public boolean updateBalance(int walletNo, int newBalance) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    boolean flag = false;
	    try {
	        con = pool.getConnection();
	        String sql = "UPDATE wallet SET balance = ? WHERE walletNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, newBalance);
	        pstmt.setInt(2, walletNo);
	        if (pstmt.executeUpdate() > 0) flag = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt);
	    }
	    return flag;
	}
}