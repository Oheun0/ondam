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

    // 1. 특정 유저가 특정 상품을 이미 찜했는지 확인 (옵션 제거)
    public WishDTO checkWish(int userNo, int productNo) {
        Connection con = null; 
        PreparedStatement pstmt = null; 
        ResultSet rs = null;
        WishDTO dto = null;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM wish WHERE userNo=? AND productNo=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userNo);
            pstmt.setInt(2, productNo);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                dto = new WishDTO();
                dto.setWishNo(rs.getInt("wishNo"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt, rs); }
        return dto;
    }
    
    // 2. 내 찜 리스트 보기 (옵션 제거)
    public Vector<WishDTO> getMyWish(int userNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<WishDTO> vlist = new Vector<WishDTO>();
        try {
            con = pool.getConnection();
            sql = "SELECT * FROM wish where userNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userNo);
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
    
    // 3. 찜 등록 (옵션 제거)
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
    
    // 4. 찜 삭제 (옵션 제거)
    public boolean deleteWishByInfo(int userNo, int productNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            String sql = "DELETE FROM wish WHERE userNo = ? AND productNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userNo);
            pstmt.setInt(2, productNo);
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
