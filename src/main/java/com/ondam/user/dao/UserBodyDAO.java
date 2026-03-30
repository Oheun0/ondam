package com.ondam.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ondam.common.DBConnectionMgr;
import com.ondam.user.dto.UserBodyDTO;

public class UserBodyDAO {
	private DBConnectionMgr pool;
	public UserBodyDAO() {
		pool=DBConnectionMgr.getInstance();
	}
	public List<UserBodyDTO> getBodyList(int userNo){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<UserBodyDTO> list = new ArrayList<>();
		try {
			con = pool.getConnection();
			sql = "select * from userBody where userNo=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				UserBodyDTO dto = new UserBodyDTO();
				dto.setUserBodyNo(rs.getInt("userBodyNo"));
				dto.setUserNo(rs.getInt("userNo"));
				dto.setUserBodyType(rs.getString("userBodyType"));
				dto.setCreatedAt(rs.getString("createdAt"));
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
