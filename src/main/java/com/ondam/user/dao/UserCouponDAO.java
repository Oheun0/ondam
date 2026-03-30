package com.ondam.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ondam.common.DBConnectionMgr;
import com.ondam.user.dto.UserBodyDTO;
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
			sql = "select * from userCoupon where userNo=?";
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
}
