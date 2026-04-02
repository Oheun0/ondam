package com.ondam.coupon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
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
			sql = "SELECT * FROM coupon ORDER BY couponNo DESC"; // 최신 쿠폰이 먼저 보이도록 정렬 추가
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CouponDTO dto = new CouponDTO();
				dto.setCouponNo(rs.getInt("couponNo"));
				dto.setCouponName(rs.getString("couponName"));
				dto.setDiscountType(rs.getInt("discountType"));
				dto.setDiscountValue(rs.getInt("discountValue"));
				dto.setMinOrderAmount(rs.getInt("minOrderAmount"));
				
				int maxDiscount = rs.getInt("maxDiscountAmount");
				dto.setMaxDiscountAmount(rs.wasNull() ? null : maxDiscount);
				
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

	// Insert (createdAt 제거 - DB 기본값 사용 권장)
	public boolean insertCoupon(CouponDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT INTO coupon (couponName, discountType, discountValue, minOrderAmount, maxDiscountAmount, validFrom, validUntil) VALUES (?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getCouponName());
			pstmt.setInt(2, dto.getDiscountType());
			pstmt.setInt(3, dto.getDiscountValue());
			pstmt.setInt(4, dto.getMinOrderAmount());
			
			if (dto.getMaxDiscountAmount() == null) {
			    pstmt.setNull(5, Types.INTEGER);
			} else {
			    pstmt.setInt(5, dto.getMaxDiscountAmount());
			}
			
			pstmt.setString(6, dto.getValidFrom());
			pstmt.setString(7, dto.getValidUntil());
			
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	// Update (createdAt 제거 - 수정 시 생성일이 변경되지 않도록 보호)
	public boolean updateCoupon(CouponDTO dto, int couponNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE coupon SET couponName = ?, discountType = ?, discountValue = ?, minOrderAmount = ?, maxDiscountAmount = ?, validFrom = ?, validUntil = ? WHERE couponNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getCouponName());
			pstmt.setInt(2, dto.getDiscountType());
			pstmt.setInt(3, dto.getDiscountValue());
			pstmt.setInt(4, dto.getMinOrderAmount());
			
			if (dto.getMaxDiscountAmount() == null) {
			    pstmt.setNull(5, Types.INTEGER);
			} else {
			    pstmt.setInt(5, dto.getMaxDiscountAmount());
			}
			
			pstmt.setString(6, dto.getValidFrom());
			pstmt.setString(7, dto.getValidUntil());
			pstmt.setInt(8, couponNo);
			
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
			sql = "DELETE FROM coupon WHERE couponNo = ?";
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
	
	// 특정 쿠폰 단건 조회
    public CouponDTO getCouponById(int couponNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CouponDTO dto = null;
        
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM coupon WHERE couponNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, couponNo);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	dto = new CouponDTO();
				dto.setCouponNo(rs.getInt("couponNo"));
				dto.setCouponName(rs.getString("couponName"));
				dto.setDiscountType(rs.getInt("discountType"));
				dto.setDiscountValue(rs.getInt("discountValue"));
				dto.setMinOrderAmount(rs.getInt("minOrderAmount"));
				
				int maxDiscount = rs.getInt("maxDiscountAmount");
				dto.setMaxDiscountAmount(rs.wasNull() ? null : maxDiscount);
				
				dto.setValidFrom(rs.getString("validFrom"));
				dto.setValidUntil(rs.getString("validUntil"));
				dto.setCreatedAt(rs.getString("createdAt"));
			}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return dto;
    }
}