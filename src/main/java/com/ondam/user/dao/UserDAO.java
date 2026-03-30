package com.ondam.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ondam.common.DBConnectionMgr;
import com.ondam.user.dto.UserDTO;

public class UserDAO {
	private DBConnectionMgr pool;
	
	public UserDAO() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public UserDTO getUserId(String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		UserDTO user = null;
		
		try {
			con = pool.getConnection();
			sql = "select * from user where userId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user = new UserDTO();
				
				user.setUserNo(rs.getInt("userNo"));
				user.setUserId(rs.getString("userId"));
				user.setUserPwd(rs.getString("userPwd"));
				user.setUserName(rs.getString("userName"));
				user.setUserNick(rs.getString("userNick"));
				user.setUserPhoneNumber(rs.getString("userPhoneNumber"));
				user.setUserEmail(rs.getString("userEmail"));
				user.setUserBirth(rs.getString("userBirth"));
				user.setUserGender(rs.getInt("userGender"));
				user.setUserHeight(rs.getInt("userHeight"));
				user.setUserPreferColor(rs.getString("userPreferColor"));
				user.setUserProfileImg(rs.getString("userProfileImg"));
				user.setJoinReason(rs.getInt("joinReason"));
				user.setIsActive(rs.getInt("isActive"));
				user.setDeleteAt(rs.getString("deleteAt"));
				user.setPreferPayment(rs.getInt("preferPayment"));
				user.setSignupStep(rs.getInt("signupStep"));
				user.setSignUpCompleted(rs.getInt("signUpCompleted"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return user;
	}
}
