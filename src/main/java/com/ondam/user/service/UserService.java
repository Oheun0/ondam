package com.ondam.user.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ondam.common.DBConnectionMgr;
import com.ondam.notification.dao.NotificationSettingDAO;
import com.ondam.notification.dto.NotificationSettingDTO;
import com.ondam.user.dao.UserAddressDAO;
import com.ondam.user.dao.UserDAO;
import com.ondam.user.dao.UserHobbyDAO;
import com.ondam.user.dao.UserPreferColorDAO;
import com.ondam.user.dto.UserAddressDTO;
import com.ondam.user.dto.UserDTO;
import com.ondam.user.dto.UserHobbyDTO;
import com.ondam.user.dto.UserPreferColorDTO;

public class UserService {
		private UserDAO userDAO;
		private UserAddressDAO addressDAO;
		private UserHobbyDAO hobbyDAO;
		private UserPreferColorDAO colorDAO;
		
		public UserService() {
			userDAO = new UserDAO();
			addressDAO = new UserAddressDAO();
			hobbyDAO = new UserHobbyDAO();
			colorDAO = new UserPreferColorDAO();
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
		public int insertCompleteSignup(UserDTO user, UserAddressDTO address, 
				List<UserHobbyDTO> hobbyList, List<UserPreferColorDTO> colorList) {
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
	            	userDAO.updateUserForSignup(conn, user); 
	                userNo = existingUser.getUserNo();
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
	                
	                colorDAO.deleteUserPreferColor(conn, userNo);
	                int colorResult = 1;
	                if (colorList != null && !colorList.isEmpty()) {
	                    for (UserPreferColorDTO color : colorList) {
	                        color.setUserNo(userNo);
	                        int res = colorDAO.insertUserPreferColor(conn, color);
	                        if (res == 0) colorResult = 0;
	                    }
	                }
	                
	                if (addressResult > 0 && hobbyResult > 0 && colorResult > 0) {

	                    // 알림 설정 7개 생성 (트랜잭션 안에서 같이 처리)
	                    NotificationSettingDAO notiSettingDao = new NotificationSettingDAO();
	                    int notiResult = 1;
	                    for (int type = 0; type <= 6; type++) {
	                        NotificationSettingDTO setting = new NotificationSettingDTO();
	                        setting.setUserNo(userNo);
	                        setting.setNotificationType(type);
	                        setting.setIsEnabled(1);
	                        boolean ok = notiSettingDao.insertNotificationSetting(conn, setting); // conn 전달
	                        if (!ok) { notiResult = 0; break; }
	                    }

	                    if (notiResult > 0) {
	                        conn.commit();
	                        result = 1;
	                    } else {
	                        conn.rollback();
	                    }

	                } else {
	                    conn.rollback();
	                }
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
		
		//마이페이지 취향 정보 업데이트
		public int updateUserPreferences(int userNo, String[] colors, String[] hobbies) {
		    DBConnectionMgr pool = null;
		    Connection conn = null;
		    int result = 0;

		    try {
		        pool = DBConnectionMgr.getInstance();
		        conn = pool.getConnection();
		        conn.setAutoCommit(false);

		        colorDAO.deleteUserPreferColor(conn, userNo);
		        hobbyDAO.deleteUserHobby(conn, userNo);

		        int colorResult = 1;
		        int hobbyResult = 1;

		        if (colors != null && colors.length > 0) {
		            for (String color : colors) {
		                UserPreferColorDTO colorDTO = new UserPreferColorDTO();
		                colorDTO.setUserNo(userNo);
		                colorDTO.setPreferColor(color);
		                int res = colorDAO.insertUserPreferColor(conn, colorDTO);
		                if (res == 0) colorResult = 0;
		            }
		        }

		        if (hobbies != null && hobbies.length > 0) {
		            for (String hobby : hobbies) {
		                UserHobbyDTO hobbyDTO = new UserHobbyDTO();
		                hobbyDTO.setUserNo(userNo);
		                hobbyDTO.setUserHobby(hobby);
		                int res = hobbyDAO.insertUserHobby(conn, hobbyDTO);
		                if (res == 0) hobbyResult = 0;
		            }
		        }

		        if (colorResult > 0 && hobbyResult > 0) {
		            conn.commit();
		            result = 1;
		        } else {
		            conn.rollback();
		        }
		    } catch (Exception e) {
		        if (conn != null) {
		            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
		        }
		        e.printStackTrace();
		    } finally {
		        if (conn != null) {
		            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
		        }
		        if (pool != null && conn != null) {
		            pool.freeConnection(conn);
		        }
		    }
		    return result;
		}
		
		// 배송지 저장 및 수정
		public int saveUserAddress(UserAddressDTO address, String mode) {
		    DBConnectionMgr pool = null;
		    Connection conn = null;
		    int result = 0;

		    try {
		        pool = DBConnectionMgr.getInstance();
		        conn = pool.getConnection();
		        conn.setAutoCommit(false); // 트랜잭션 시작

		        // 1. 현재 사용자의 배송지 개수 확인
		        int addressCount = addressDAO.countAddresses(conn, address.getUserNo());

		        // 2. [비즈니스 로직] 첫 번째 배송지라면 무조건 기본 배송지(1)로 설정
		        if (addressCount == 0) {
		            address.setIsDefault(1);
		        }

		        // 3. 만약 이번에 저장/수정하려는 배송지가 '기본 배송지'라면
		        //    기존에 설정된 다른 기본 배송지들을 모두 일반 배송지(0)로 초기화
		        if (address.getIsDefault() == 1) {
		            addressDAO.resetDefaultAddress(conn, address.getUserNo());
		        }

		        // 4. 모드에 따라 Insert 또는 Update 수행
		        if ("edit".equals(mode)) {
		            result = addressDAO.updateUserAddress(conn, address);
		        } else {
		            result = addressDAO.insertUserAddress(conn, address);
		        }

		        if (result > 0) {
		            conn.commit(); // 모든 과정 성공 시 커밋
		        } else {
		            conn.rollback();
		        }

		    } catch (Exception e) {
		        if (conn != null) {
		            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
		        }
		        e.printStackTrace();
		    } finally {
		        if (conn != null) {
		            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
		        }
		        if (pool != null && conn != null) {
		            pool.freeConnection(conn);
		        }
		    }
		    return result;
		}
	}