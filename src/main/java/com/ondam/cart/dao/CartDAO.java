package com.ondam.cart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.cart.dto.CartDTO;

public class CartDAO {

	private DBConnectionMgr pool;

	public CartDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<CartDTO> getCart() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<CartDTO> vlist = new Vector<CartDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM cart";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CartDTO dto = new CartDTO();
				dto.setCartNo(rs.getInt("cartNo"));
				dto.setUserNo(rs.getInt("userNo"));
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
	public boolean insertCart(CartDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Cart (userNo) VALUES (?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserNo());
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
	public boolean updateCart(CartDTO dto, int cartNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Cart SET userNo = ? WHERE cartNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserNo());
			pstmt.setInt(2, cartNo);
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
	public boolean deleteCart(int cartNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Cart WHERE cartNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cartNo);
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

