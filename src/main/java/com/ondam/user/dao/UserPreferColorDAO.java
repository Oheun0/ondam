package com.ondam.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.ondam.user.dto.UserPreferColorDTO;

public class UserPreferColorDAO {

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
}