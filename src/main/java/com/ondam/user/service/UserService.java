package com.ondam.user.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ondam.common.DBConnectionMgr;
import com.ondam.user.dao.UserAddressDAO;
import com.ondam.user.dao.UserDAO;
import com.ondam.user.dao.UserHobbyDAO;
import com.ondam.user.dto.UserAddressDTO;
import com.ondam.user.dto.UserDTO;
import com.ondam.user.dto.UserHobbyDTO;

public class UserService {
		private UserDAO userDAO;
		private UserAddressDAO addressDAO;
		private UserHobbyDAO hobbyDAO;
		
		public UserService() {
			userDAO = new UserDAO();
			addressDAO = new UserAddressDAO();
			hobbyDAO = new UserHobbyDAO();
		}
		
		/*로그인 로직 처리
		 * 로그인 성공 시 userDTO 반환, 실패 시 null 반환*/
		public UserDTO login(String userId, String userPwd) {
			UserDTO user = userDAO.getUserId(userId);
			
			if(user != null && user.getUserPwd() !=null) {
				if(user.getUserPwd().equals(userPwd)) {
					return user;
				}
			}
			return null;
		}
		
		//카카오 로그인
		public UserDTO loginKakao(String kakaoId, String nickname) {
	        UserDTO user = userDAO.getUserId(kakaoId);

	        if (user == null) {
	            DBConnectionMgr pool = null;
	            Connection conn = null;
	            
	            try {
	                pool = DBConnectionMgr.getInstance();
	                conn = pool.getConnection();
	                conn.setAutoCommit(false);
	                
	                UserDTO newUser = new UserDTO();
	                newUser.setUserId(kakaoId);
	                newUser.setUserName(nickname);
	                newUser.setUserNick(nickname);

	                int result = userDAO.insertUser(conn, newUser);

	                if (result > 0) {
	                	conn.commit();
	                    user = userDAO.getUserId(kakaoId);
	                }else {
	                	conn.rollback();
	                }
	                
	            } catch (Exception e) {
	            	try { if(conn != null) conn.rollback(); } catch(Exception ex) {}
	                e.printStackTrace();
	            } finally {
	                if (pool != null && conn != null) {
	                    pool.freeConnection(conn);
	                }
	            }
	        }
	        return user;
	    }
		
		/*회원가입 로직 처리, user, userAddress, userhobby를 삽입*/
		public int insertCompleteSignup(UserDTO user, UserAddressDTO address, List<UserHobbyDTO> hobbyList) {
	        DBConnectionMgr pool = null;
	        Connection conn = null;
	        int result = 0;
	        
	        try {
	            pool = DBConnectionMgr.getInstance();
	            conn = pool.getConnection();
	            conn.setAutoCommit(false);

	            UserDTO existingUser = userDAO.getUserId(user.getUserId());
	            int userNo = 0;

	            if (existingUser == null) {
	                // 일반 회원가입
	                userNo = userDAO.insertUser(conn, user);
	            } else {
	                // 카카오 회원가입
	                userNo = userDAO.updateUserForSignup(conn, user); 
	                if(userNo == 0) userNo = existingUser.getUserNo(); 
	            }
	            
	            if (userNo > 0) {
	                address.setUserNo(userNo);
	                int addressResult = addressDAO.insertUserAddress(conn, address);
	                hobbyDAO.deleteUserHobby(conn, userNo);
	                
	                int hobbyResult = 1;
	                if (hobbyList != null && !hobbyList.isEmpty()) {
	                    for (UserHobbyDTO hobby : hobbyList) {
	                        hobby.setUserNo(userNo);
	                        int res = hobbyDAO.insertUserHobby(conn, hobby);
	                        if (res == 0) hobbyResult = 0;
	                    }
	                }
	                
	                if (addressResult > 0 && hobbyResult > 0) {
	                    conn.commit();
	                    result = 1;
	                } else {
	                    conn.rollback();
	                }
	            } else {
	                conn.rollback();
	            }
	            
	        } catch (Exception e) {
	            if (conn != null) {
	                try { 
	                    conn.rollback(); 
	                } catch (SQLException ex) { 
	                    ex.printStackTrace(); 
	                }
	            }
	            e.printStackTrace();
	        } finally {
	            if (conn != null) {
	                try { 
	                    conn.setAutoCommit(true); 
	                } catch (SQLException e) { 
	                    e.printStackTrace(); 
	                }
	            }

	            if (pool != null && conn != null) {
	                pool.freeConnection(conn); 
	            }
	        }
	        
	        return result;
	    }
	}