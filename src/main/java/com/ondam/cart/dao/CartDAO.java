package com.ondam.cart.dao;

import java.sql.*;
import com.ondam.common.DBConnectionMgr;

public class CartDAO {
    private DBConnectionMgr pool = DBConnectionMgr.getInstance();

    public int getOrCreateCart(int userNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cartNo = 0;

        try {
            con = pool.getConnection();
            // 1. 기존 장바구니 존재 확인
            pstmt = con.prepareStatement("SELECT cartNo FROM Cart WHERE userNo = ?");
            pstmt.setInt(1, userNo);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cartNo = rs.getInt("cartNo");
            } else {
                // 2. 없으면 생성
                pstmt.close();
                pstmt = con.prepareStatement("INSERT INTO Cart (userNo) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, userNo);
                pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) cartNo = rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); } 
        finally { pool.freeConnection(con, pstmt, rs); }
        return cartNo;
    }

    public int findOptionNoByColorAndSize(int productNo, String color, String size) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int optionNo = 0;
        
        try {
            con = pool.getConnection();
            String sql = "SELECT productOptionNo FROM productOption WHERE productNo = ? AND optionColor = ? AND optionSize = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, productNo);
            pstmt.setString(2, color);
            pstmt.setString(3, size);
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
                optionNo = rs.getInt("productOptionNo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return optionNo;
    }
}