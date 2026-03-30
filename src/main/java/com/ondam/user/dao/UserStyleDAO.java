package com.ondam.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ondam.common.DBConnectionMgr;
import com.ondam.user.dto.UserStyleDTO;

public class UserStyleDAO {
	private DBConnectionMgr pool;
	public UserStyleDAO() {
		pool=DBConnectionMgr.getInstance();
	}
	public List<UserStyleDTO> getStyle(int userNo){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<UserStyleDTO> list = new ArrayList<>();
		try {
			con = pool.getConnection();
			sql = "select * from userStyle where userNo=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				UserStyleDTO dto = new UserStyleDTO();
				dto.setUserStyleNo(rs.getInt("userStyleNo"));
				dto.setUserNo(rs.getInt("userNo"));
				dto.setUserPreferStyle(rs.getString("userPreferStyle"));
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