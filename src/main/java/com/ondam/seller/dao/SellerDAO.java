package com.ondam.seller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.seller.dto.SellerDTO;

public class SellerDAO {

	private DBConnectionMgr pool;

	public SellerDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<SellerDTO> getSeller() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<SellerDTO> vlist = new Vector<SellerDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM seller";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SellerDTO dto = new SellerDTO();
				dto.setSellerAccountNo(rs.getInt("sellerAccountNo"));
				dto.setVendorNo(rs.getInt("vendorNo"));
				dto.setSellerId(rs.getString("sellerId"));
				dto.setSellerPwd(rs.getString("sellerPwd"));
				dto.setSellerName(rs.getString("sellerName"));
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
	public boolean insertSeller(SellerDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Seller (vendorNo, sellerId, sellerPwd, sellerName) VALUES (?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setString(2, dto.getSellerId());
			pstmt.setString(3, dto.getSellerPwd());
			pstmt.setString(4, dto.getSellerName());
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
	public boolean updateSeller(SellerDTO dto, int sellerAccountNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Seller SET vendorNo = ?, sellerId = ?, sellerPwd = ?, sellerName = ? WHERE sellerAccountNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setString(2, dto.getSellerId());
			pstmt.setString(3, dto.getSellerPwd());
			pstmt.setString(4, dto.getSellerName());
			pstmt.setInt(5, sellerAccountNo);
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
	public boolean deleteSeller(int sellerAccountNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Seller WHERE sellerAccountNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sellerAccountNo);
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

