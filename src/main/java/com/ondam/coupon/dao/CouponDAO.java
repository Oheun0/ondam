package com.ondam.coupon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.coupon.dto.CouponDTO;

public class CouponDAO {

	private DBConnectionMgr pool;

	public CouponDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<CouponDTO> getCoupon() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<CouponDTO> vlist = new Vector<CouponDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM coupon";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CouponDTO dto = new CouponDTO();
				dto.setCouponNo(rs.getInt("couponNo"));
				dto.setCouponName(rs.getString("couponName"));
				dto.setDiscountType(rs.getInt("discountType"));
				dto.setDiscountValue(rs.getInt("discountValue"));
				dto.setMinOrderAmount(rs.getInt("minOrderAmount"));
				dto.setMaxDiscountAmount(rs.getInt("maxDiscountAmount"));
				dto.setValidFrom(rs.getString("validFrom"));
				dto.setValidUntil(rs.getString("validUntil"));
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
	public boolean insertCoupon(CouponDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Coupon (couponName, discountType, discountValue, minOrderAmount, maxDiscountAmount, validFrom, validUntil, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getCouponName());
			pstmt.setInt(2, dto.getDiscountType());
			pstmt.setInt(3, dto.getDiscountValue());
			pstmt.setInt(4, dto.getMinOrderAmount());
			pstmt.setInt(5, dto.getMaxDiscountAmount());
			pstmt.setString(6, dto.getValidFrom());
			pstmt.setString(7, dto.getValidUntil());
			pstmt.setString(8, dto.getCreatedAt());
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
	public boolean updateCoupon(CouponDTO dto, int couponNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Coupon SET couponName = ?, discountType = ?, discountValue = ?, minOrderAmount = ?, maxDiscountAmount = ?, validFrom = ?, validUntil = ?, createdAt = ? WHERE couponNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getCouponName());
			pstmt.setInt(2, dto.getDiscountType());
			pstmt.setInt(3, dto.getDiscountValue());
			pstmt.setInt(4, dto.getMinOrderAmount());
			pstmt.setInt(5, dto.getMaxDiscountAmount());
			pstmt.setString(6, dto.getValidFrom());
			pstmt.setString(7, dto.getValidUntil());
			pstmt.setString(8, dto.getCreatedAt());
			pstmt.setInt(9, couponNo);
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
	public boolean deleteCoupon(int couponNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Coupon WHERE couponNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, couponNo);
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

