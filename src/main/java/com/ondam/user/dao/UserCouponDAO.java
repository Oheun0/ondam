package com.ondam.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ondam.common.DBConnectionMgr;
import com.ondam.user.dto.UserCouponDTO;

public class UserCouponDAO {
	private DBConnectionMgr pool;
	public UserCouponDAO() {
		pool=DBConnectionMgr.getInstance();
	}
	public List<UserCouponDTO> getCouponList(int userNo){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<UserCouponDTO> list = new ArrayList<>();
		try {
			con = pool.getConnection();
			sql = "SELECT uc.*, c.couponName, c.discountType, c.discountValue, c.minOrderAmount, c.maxDiscountAmount, c.validFrom, c.validUntil " +
                    "FROM userCoupon uc " +
                    "JOIN coupon c ON uc.couponNo = c.couponNo " +
                    "WHERE uc.userNo = ? ORDER BY uc.issuedAt DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				UserCouponDTO dto = new UserCouponDTO();
				dto.setUserCouponNo(rs.getInt("userCouponNo"));
				dto.setUserNo(rs.getInt("userNo"));
				dto.setCouponNo(rs.getInt("couponNo"));
				dto.setIsUsed(rs.getInt("isUsed"));
				dto.setIssuedAt(rs.getString("issuedAt"));
				dto.setUsedAt(rs.getString("usedAt"));
				dto.setOrderNo(rs.getInt("orderNo"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return list;
	}
	
	/**
     * [주문/결제] 특정 금액 이상에서 사용 가능한 쿠폰만 조회 (마스터 정보 JOIN)
     */
    public List<UserCouponDTO> getAvailableCoupons(int userNo, int orderAmount) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<UserCouponDTO> list = new ArrayList<>();
        
        try {
            con = pool.getConnection();
            String sql = "SELECT uc.*, c.couponName, c.discountType, c.discountValue, c.minOrderAmount, c.maxDiscountAmount, c.validFrom, c.validUntil " +
                         "FROM userCoupon uc " +
                         "JOIN coupon c ON uc.couponNo = c.couponNo " +
                         "WHERE uc.userNo = ? AND uc.isUsed = 0 " +
                         "AND c.minOrderAmount <= ? " +
                         "AND NOW() BETWEEN c.validFrom AND c.validUntil " +
                         "ORDER BY c.discountValue DESC";
                         
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userNo);
            pstmt.setInt(2, orderAmount);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	UserCouponDTO dto = new UserCouponDTO();
				dto.setUserCouponNo(rs.getInt("userCouponNo"));
				dto.setUserNo(rs.getInt("userNo"));
				dto.setCouponNo(rs.getInt("couponNo"));
				dto.setIsUsed(rs.getInt("isUsed"));
				dto.setIssuedAt(rs.getString("issuedAt"));
				dto.setUsedAt(rs.getString("usedAt"));
				dto.setOrderNo(rs.getInt("orderNo"));
				list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return list;
    }

    /**
     * [발급] 유저에게 쿠폰 다운로드/발급
     */
    public boolean insertUserCoupon(int userNo, int couponNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean flag = false;
        
        try {
            con = pool.getConnection();
            // 중복 발급 방지는 비즈니스 로직(Service) 또는 Unique Key 제약으로 처리 가정
            String sql = "INSERT INTO userCoupon (userNo, couponNo, isUsed, issuedAt) VALUES (?, ?, 0, NOW())";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userNo);
            pstmt.setInt(2, couponNo);
            
            if (pstmt.executeUpdate() > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }
}
