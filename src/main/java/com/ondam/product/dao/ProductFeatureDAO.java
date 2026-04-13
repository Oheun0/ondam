package com.ondam.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.product.dto.ProductFeatureDTO;

public class ProductFeatureDAO {

	private DBConnectionMgr pool;

	public ProductFeatureDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<ProductFeatureDTO> getProductFeature() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ProductFeatureDTO> vlist = new Vector<ProductFeatureDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM productFeature";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductFeatureDTO dto = new ProductFeatureDTO();
				dto.setProductFeatureNo(rs.getInt("productFeatureNo"));
				dto.setProductNo(rs.getInt("productNo"));
				dto.setFeature(rs.getString("feature"));
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
	public boolean insertProductFeature(ProductFeatureDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT productFeature (productNo, feature) VALUES (?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getProductNo());
			pstmt.setString(2, dto.getFeature());
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
	public boolean updateProductFeature(ProductFeatureDTO dto, int productFeatureNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE productFeature SET productNo = ?, feature = ? WHERE productFeatureNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getProductNo());
			pstmt.setString(2, dto.getFeature());
			pstmt.setInt(3, productFeatureNo);
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
	public boolean deleteProductFeature(int productFeatureNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM productFeature WHERE productFeatureNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productFeatureNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	public boolean deleteByProductNo(int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = pool.getConnection();
			String sql = "DELETE FROM productFeature WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productNo);
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return false;
	}

	public Vector<String> getFeaturesByProductNo(int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<String> features = new Vector<>();
		try {
			con = pool.getConnection();
			String sql = "SELECT feature FROM productFeature WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				features.add(rs.getString("feature"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return features;
	}
}