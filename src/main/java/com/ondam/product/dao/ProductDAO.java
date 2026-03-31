package com.ondam.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.product.dto.ProductDTO;

public class ProductDAO {

	private DBConnectionMgr pool;

	public ProductDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<ProductDTO> getProduct() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ProductDTO> vlist = new Vector<ProductDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM product";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setProductNo(rs.getInt("productNo"));
				dto.setVendorNo(rs.getInt("vendorNo"));
				dto.setCategoryNo(rs.getInt("categoryNo"));
				dto.setProductName(rs.getString("productName"));
				dto.setProductBrand(rs.getString("productBrand"));
				dto.setProductEx(rs.getString("productEx"));
				dto.setProductPrice(rs.getInt("productPrice"));
				dto.setProductOriginPrice(rs.getInt("productOriginPrice"));
				dto.setProductMaterial(rs.getString("productMaterial"));
				dto.setProductPattern(rs.getString("productPattern"));
				dto.setProductFit(rs.getString("productFit"));
				dto.setProductThickness(rs.getString("productThickness"));
				dto.setProductSeason(rs.getString("productSeason"));
				dto.setProductState(rs.getInt("productState"));
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
	public boolean insertProduct(ProductDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Product (vendorNo, categoryNo, productName, productBrand, productEx, productPrice, productOriginPrice, productMaterial, productPattern, productFit, productThickness, productSeason, productState) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setInt(2, dto.getCategoryNo());
			pstmt.setString(3, dto.getProductName());
			pstmt.setString(4, dto.getProductBrand());
			pstmt.setString(5, dto.getProductEx());
			pstmt.setInt(6, dto.getProductPrice());
			pstmt.setInt(7, dto.getProductOriginPrice());
			pstmt.setString(8, dto.getProductMaterial());
			pstmt.setString(9, dto.getProductPattern());
			pstmt.setString(10, dto.getProductFit());
			pstmt.setString(11, dto.getProductThickness());
			pstmt.setString(12, dto.getProductSeason());
			pstmt.setInt(13, dto.getProductState());
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
	public boolean updateProduct(ProductDTO dto, int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Product SET vendorNo = ?, categoryNo = ?, productName = ?, productBrand = ?, productEx = ?, productPrice = ?, productOriginPrice = ?, productMaterial = ?, productPattern = ?, productFit = ?, productThickness = ?, productSeason = ?, productState = ? WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setInt(2, dto.getCategoryNo());
			pstmt.setString(3, dto.getProductName());
			pstmt.setString(4, dto.getProductBrand());
			pstmt.setString(5, dto.getProductEx());
			pstmt.setInt(6, dto.getProductPrice());
			pstmt.setInt(7, dto.getProductOriginPrice());
			pstmt.setString(8, dto.getProductMaterial());
			pstmt.setString(9, dto.getProductPattern());
			pstmt.setString(10, dto.getProductFit());
			pstmt.setString(11, dto.getProductThickness());
			pstmt.setString(12, dto.getProductSeason());
			pstmt.setInt(13, dto.getProductState());
			pstmt.setInt(14, productNo);
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
	public boolean deleteProduct(int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Product WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productNo);
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

