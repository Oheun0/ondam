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
		
		/*회원가입 로직 처리, user, userAddress, userhobby를 삽입*/
		public int insertCompleteSignup(UserDTO user, UserAddressDTO address, List<UserHobbyDTO> hobbyList) {
	        DBConnectionMgr pool = null;
	        Connection conn = null;
	        int result = 0;
	        
	        try {
	            pool = DBConnectionMgr.getInstance();
	            conn = pool.getConnection();
	            conn.setAutoCommit(false);

	            int userNo = userDAO.insertUser(conn, user);
	            
	            if (userNo > 0) {
	                address.setUserNo(userNo);
	                int addressResult = addressDAO.insertUserAddress(conn, address);
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