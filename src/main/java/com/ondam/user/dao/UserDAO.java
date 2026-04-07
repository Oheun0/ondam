package com.ondam.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
				user.setUserWeight(rs.getInt("userWeight"));
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
	
	public int insertUser(Connection con, UserDTO user) {
        int userNo = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "insert into user (userId, userPwd, userName, userNick, userPhoneNumber, "
                   + "userEmail, userBirth, userGender, userHeight, userWeight, "
                   + "joinReason, preferPayment, signupStep, signUpCompleted) "
                   + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                   
        try {
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUserPwd());
            pstmt.setString(3, user.getUserName());
            pstmt.setString(4, user.getUserNick());
            pstmt.setString(5, user.getUserPhoneNumber());
            pstmt.setString(6, user.getUserEmail());
            pstmt.setString(7, user.getUserBirth());
            pstmt.setInt(8, user.getUserGender());
            pstmt.setInt(9, user.getUserHeight());
            pstmt.setInt(10, user.getUserWeight());  
            pstmt.setInt(11, user.getJoinReason());
            pstmt.setInt(12, user.getPreferPayment());
            pstmt.setInt(13, user.getSignupStep());
            pstmt.setInt(14, user.getSignUpCompleted());
            
            pstmt.executeUpdate();
            
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                userNo = rs.getInt(1); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
        }
        return userNo;
    }
	
	public int updateUserForSignup(Connection con, UserDTO user) {
	    int result = 0;
	    PreparedStatement pstmt = null;

	    String sql = "UPDATE user SET userNick=?, userPhoneNumber = ?, userEmail = ?, userBirth = ?, "
	               + "userGender = ?, userHeight = ?, userWeight = ?, "
	               + "joinReason = ?, preferPayment = ?, signupStep = ?, signUpCompleted = ? "
	               + "WHERE userId = ?";
	    try {
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, user.getUserNick());
	        pstmt.setString(2, user.getUserPhoneNumber());
	        pstmt.setString(3, user.getUserEmail());
	        pstmt.setString(4, user.getUserBirth());
	        pstmt.setInt(5, user.getUserGender());
	        pstmt.setInt(6, user.getUserHeight());
	        pstmt.setInt(7, user.getUserWeight());
	        pstmt.setInt(8, user.getJoinReason());
	        pstmt.setInt(9, user.getPreferPayment());
	        pstmt.setInt(10, user.getSignupStep());
	        pstmt.setInt(11, user.getSignUpCompleted());
	        pstmt.setString(12, user.getUserId());

	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
	    }return result;
	}

	public String findUserId(String userName, String userPhoneNumber) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    String foundId = null;

	    try {
	        con = pool.getConnection();
	        sql = "SELECT userId FROM user WHERE userName = ? AND REPLACE(userPhoneNumber, '-', '') = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, userName);
	        pstmt.setString(2, userPhoneNumber);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            foundId = rs.getString("userId");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return foundId;
	}

	//비밀번호 재설정 전 본인 확인
	public int checkUserForPwdReset(String userId, String userName, String userPhoneNumber) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    int userNo = 0;

	    try {
	        con = pool.getConnection();
	        sql = "SELECT userNo FROM user WHERE userId = ? AND userName = ? AND REPLACE(userPhoneNumber, '-', '') = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, userId);
	        pstmt.setString(2, userName);
	        pstmt.setString(3, userPhoneNumber);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            userNo = rs.getInt("userNo");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return userNo;
	}

	public int updatePassword(int userNo, String newPwd) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    int result = 0;

	    try {
	        con = pool.getConnection();
	        String sql = "UPDATE user SET userPwd = ? WHERE userNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, newPwd);
	        pstmt.setInt(2, userNo);
	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt);
	    }
	    return result;
	}
	
	public int updateUserProfile(int userNo, String userName, String birthDate, int gender, String phone, String profileImgName) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    int result = 0;

	    try {
	        con = pool.getConnection(); 
	        String sql = "UPDATE user SET userName = ?, userBirth = ?, userGender = ?, userPhoneNumber = ?, userProfileImg = ? WHERE userNo = ?";
	        
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, userName);
	        pstmt.setString(2, birthDate);
	        pstmt.setInt(3, gender);
	        pstmt.setString(4, phone);
	        pstmt.setString(5, profileImgName);
	        pstmt.setInt(6, userNo);
	        
	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt);
	    }
	    
	    return result;
	}
	
	public int updateBodyInfo(int userNo, int height, int weight) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    int result = 0;

	    try {
	        con = pool.getConnection(); 
	        String sql = "UPDATE user SET userHeight = ?, userWeight = ? WHERE userNo = ?";
	        
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, height);
	        pstmt.setInt(2, weight);
	        pstmt.setInt(3, userNo);
	        
	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt);
	    }
	    return result;
	}
	
	// 회원 탈퇴 처리
		public int withdrawUser(int userNo) {
			Connection con = null;
			PreparedStatement pstmt = null;
			int result = 0;
			
			String query = "UPDATE user SET isActive = 0, deleteAt = NOW() WHERE userNo = ?";
			
			try {
				con = pool.getConnection();
				pstmt = con.prepareStatement(query);
				
				pstmt.setInt(1, userNo);
				result = pstmt.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt);
			}
			
			return result;
		}
		
		public boolean checkPassword(int userNo, String inputPwd) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean isMatch = false;

			String sql = "SELECT userPwd FROM user WHERE userNo = ?";
			
			try {
				con = pool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, userNo);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					String dbPwd = rs.getString("userPwd");

					if (dbPwd != null && dbPwd.equals(inputPwd)) {
						isMatch = true;
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			
			return isMatch;
		}
	public String getUserName(int userNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    String userName = null;

	    try {
	        con = pool.getConnection();
	        sql = "SELECT userName FROM user WHERE userNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, userNo);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	        	userName = rs.getString("userName");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return userName;
	}
	
	public String getUserPhoneNumber(int userNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    String userPhoneNumber = null;

	    try {
	        con = pool.getConnection();
	        sql = "SELECT userPhoneNumber FROM user WHERE userNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, userNo);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	        	userPhoneNumber = rs.getString("userPhoneNumber");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return userPhoneNumber;
	}

}
