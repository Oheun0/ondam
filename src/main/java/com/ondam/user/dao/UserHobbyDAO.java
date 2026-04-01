package com.ondam.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ondam.common.DBConnectionMgr;
import com.ondam.user.dto.UserHobbyDTO;

public class UserHobbyDAO {
	private DBConnectionMgr pool;
	public UserHobbyDAO() {
		pool=DBConnectionMgr.getInstance();
	}
	public List<UserHobbyDTO> getHobbyList(int userNo){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<UserHobbyDTO> list = new ArrayList<>();
		try {
			con = pool.getConnection();
			sql = "select * from userHobby where userNo=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				UserHobbyDTO dto = new UserHobbyDTO();
				dto.setUserHobbyNo(rs.getInt("userHobbyNo"));
				dto.setUserNo(rs.getInt("userNo"));
				dto.setUserHobby(rs.getString("userHobby"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return list;
	}
	public int insertUserHobby(Connection con, UserHobbyDTO hobby) {
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = "insert into userHobby (userNo, userHobby) values (?, ?)";
                   
        try {
            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, hobby.getUserNo());
            pstmt.setString(2, hobby.getUserHobby());
            
            result = pstmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { 
                if (pstmt != null) pstmt.close(); 
            } catch (Exception e) {}
        }
        
        return result;
    }
	
	public int deleteUserHobby(Connection conn, int userNo) {
	    int result = 0;
	    PreparedStatement pstmt = null;
	    String sql = "DELETE FROM userhobby WHERE userNo = ?";

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, userNo);
	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
	    }
	    return result;
	}
}
