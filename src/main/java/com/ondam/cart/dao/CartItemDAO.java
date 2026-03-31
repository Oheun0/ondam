package com.ondam.cart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.cart.dto.CartItemDTO;
import com.ondam.common.DBConnectionMgr;

public class CartItemDAO {

	private DBConnectionMgr pool;

	public CartItemDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<CartItemDTO> getCartItem() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<CartItemDTO> vlist = new Vector<CartItemDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM cartItem";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CartItemDTO dto = new CartItemDTO();
				dto.setCartItemNo(rs.getInt("cartItemNo"));
				dto.setCartNo(rs.getInt("cartNo"));
				dto.setProductNo(rs.getInt("productNo"));
				dto.setProductOptionNo(rs.getInt("productOptionNo"));
				dto.setCartQuantity(rs.getInt("cartQuantity"));
				dto.setCartAddedDate(rs.getString("cartAddedDate"));
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
	public boolean insertCartItem(CartItemDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT CartItem (cartNo, productNo, productOptionNo, cartQuantity, cartAddedDate) VALUES (?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getCartNo());
			pstmt.setInt(2, dto.getProductNo());
			pstmt.setInt(3, dto.getProductOptionNo());
			pstmt.setInt(4, dto.getCartQuantity());
			pstmt.setString(5, dto.getCartAddedDate());
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
	public boolean updateCartItem(CartItemDTO dto, int cartItemNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE CartItem SET cartNo = ?, productNo = ?, productOptionNo = ?, cartQuantity = ?, cartAddedDate = ? WHERE cartItemNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getCartNo());
			pstmt.setInt(2, dto.getProductNo());
			pstmt.setInt(3, dto.getProductOptionNo());
			pstmt.setInt(4, dto.getCartQuantity());
			pstmt.setString(5, dto.getCartAddedDate());
			pstmt.setInt(6, cartItemNo);
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
	public boolean deleteCartItem(int cartItemNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM CartItem WHERE cartItemNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cartItemNo);
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

