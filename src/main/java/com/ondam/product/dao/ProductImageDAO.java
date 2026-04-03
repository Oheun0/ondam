package com.ondam.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.product.dto.ProductImageDTO;

public class ProductImageDAO {

	private DBConnectionMgr pool;

	public ProductImageDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<ProductImageDTO> getProductImage() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ProductImageDTO> vlist = new Vector<ProductImageDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM productImage";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductImageDTO dto = new ProductImageDTO();
				dto.setProductImgNo(rs.getInt("productImgNo"));
				dto.setProductNo(rs.getInt("productNo"));
				dto.setImgFile(rs.getString("imgFile"));
				dto.setImgType(rs.getInt("imgType"));
				dto.setImgOrder(rs.getInt("imgOrder"));
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
	public boolean insertProductImage(ProductImageDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT ProductImage (productNo, imgFile, imgType, imgOrder) VALUES (?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getProductNo());
			pstmt.setString(2, dto.getImgFile());
			pstmt.setInt(3, dto.getImgType());
			pstmt.setInt(4, dto.getImgOrder());
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
	public boolean updateProductImage(ProductImageDTO dto, int productImgNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE ProductImage SET productNo = ?, imgFile = ?, imgType = ?, imgOrder = ? WHERE productImgNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getProductNo());
			pstmt.setString(2, dto.getImgFile());
			pstmt.setInt(3, dto.getImgType());
			pstmt.setInt(4, dto.getImgOrder());
			pstmt.setInt(5, productImgNo);
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
	public boolean deleteProductImage(int productImgNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM ProductImage WHERE productImgNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productImgNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	// 특정 상품의 이미지 목록 (imgOrder 순)
	public Vector<ProductImageDTO> getByProductNo(int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<ProductImageDTO> vlist = new Vector<>();
		try {
			con = pool.getConnection();
			String sql = "SELECT * FROM productimage WHERE productNo = ? ORDER BY imgOrder ASC";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductImageDTO dto = new ProductImageDTO();
				dto.setProductImgNo(rs.getInt("productImgNo"));
				dto.setProductNo(rs.getInt("productNo"));
				dto.setImgFile(rs.getString("imgFile"));
				dto.setImgType(rs.getInt("imgType"));
				dto.setImgOrder(rs.getInt("imgOrder"));
				vlist.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	
	public ProductImageDTO getProductImageById(int productNo) {
		Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    ProductImageDTO dto = null;
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT * FROM productImage WHERE productNo = ? ORDER BY imgOrder ASC LIMIT 1";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, productNo);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            dto = new ProductImageDTO();
	            dto.setProductImgNo(rs.getInt("productImgNo"));
	            dto.setProductNo(rs.getInt("productNo"));
	            dto.setImgFile(rs.getString("imgFile"));
	            dto.setImgType(rs.getInt("imgType"));
	            dto.setImgOrder(rs.getInt("imgOrder"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return dto;
	}
}
