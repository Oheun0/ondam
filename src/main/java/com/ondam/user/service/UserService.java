package com.ondam.user.service;

import com.ondam.user.dao.UserDAO;
import com.ondam.user.dto.UserDTO;

public class UserService {
		private UserDAO userDAO;
		
		public UserService() {
			userDAO = new UserDAO();
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
}
