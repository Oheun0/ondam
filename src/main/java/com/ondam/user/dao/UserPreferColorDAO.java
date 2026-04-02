package com.ondam.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ondam.common.DBConnectionMgr;
import com.ondam.user.dto.UserPreferColorDTO;

public class UserPreferColorDAO {
		private DBConnectionMgr pool;
		public UserPreferColorDAO() {
			pool=DBConnectionMgr.getInstance();
		}

    public int insertUserPreferColor(Connection conn, UserPreferColorDTO color) {
        int result = 0;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO userprefercolor (userNo, preferColor) VALUES (?, ?)";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, color.getUserNo());
            pstmt.setString(2, color.getPreferColor());
            
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
        }
        return result;
    }

    public int deleteUserPreferColor(Connection conn, int userNo) {
        int result = 0;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM userprefercolor WHERE userNo = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userNo);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
        }
        return result;
    }
    
    public List<UserPreferColorDTO> getUserPreferColor(int userNo) {
    	Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<UserPreferColorDTO> list = new ArrayList<>();
		try {
			con = pool.getConnection();
			sql = "select * from userprefercolor where userNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				UserPreferColorDTO dto = new UserPreferColorDTO();
				dto.setUserNo(rs.getInt("userNo"));
				dto.setPreferColor(rs.getString("preferColor"));
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