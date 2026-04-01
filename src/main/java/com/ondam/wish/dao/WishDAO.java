package com.ondam.wish.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.wish.dto.WishDTO;

public class WishDAO {

    private DBConnectionMgr pool;

    public WishDAO() {
        pool = DBConnectionMgr.getInstance();
    }

    // Select
    public Vector<WishDTO> getWish() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<WishDTO> vlist = new Vector<WishDTO>();
        try {
            con = pool.getConnection();
            sql = "SELECT * FROM wish";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                WishDTO dto = new WishDTO();
                dto.setWishNo(rs.getInt("wishNo"));
                dto.setUserNo(rs.getInt("userNo"));
                dto.setProductNo(rs.getInt("productNo"));
                dto.setWishDate(rs.getString("wishDate"));
                vlist.addElement(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }

    // Insert
    public boolean insertWish(WishDTO dto) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "INSERT INTO wish (userNo, productNo) VALUES (?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, dto.getUserNo());
            pstmt.setInt(2, dto.getProductNo());
            if (pstmt.executeUpdate() > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }

    // Update
    public boolean updateWish(WishDTO dto, int wishNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "UPDATE wish SET userNo = ?, productNo = ? WHERE wishNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, dto.getUserNo());
            pstmt.setInt(2, dto.getProductNo());
            pstmt.setInt(3, wishNo);
            if (pstmt.executeUpdate() > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }

    // Delete
    public boolean deleteWish(int wishNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "DELETE FROM wish WHERE wishNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, wishNo);
            if (pstmt.executeUpdate() > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }
}