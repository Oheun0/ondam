package com.ondam.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.product.dto.CategoryDTO;

public class CategoryDAO {

	private DBConnectionMgr pool;

	public CategoryDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<CategoryDTO> getCategory() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<CategoryDTO> vlist = new Vector<CategoryDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM category";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CategoryDTO dto = new CategoryDTO();
				dto.setCategoryNo(rs.getInt("categoryNo"));
				dto.setUpCategoryNo(rs.getInt("upCategoryNo"));
				dto.setCategoryLevel(rs.getInt("categoryLevel"));
				dto.setCategoryName(rs.getString("categoryName"));
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
	public boolean insertCategory(CategoryDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Category (upCategoryNo, categoryLevel, categoryName) VALUES (?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getUpCategoryNo());
			pstmt.setInt(2, dto.getCategoryLevel());
			pstmt.setString(3, dto.getCategoryName());
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
	public boolean updateCategory(CategoryDTO dto, int categoryNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Category SET upCategoryNo = ?, categoryLevel = ?, categoryName = ? WHERE categoryNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getUpCategoryNo());
			pstmt.setInt(2, dto.getCategoryLevel());
			pstmt.setString(3, dto.getCategoryName());
			pstmt.setInt(4, categoryNo);
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
	public boolean deleteCategory(int categoryNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Category WHERE categoryNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, categoryNo);
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

