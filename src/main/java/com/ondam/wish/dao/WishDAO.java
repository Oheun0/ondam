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

    // 특정 유저가 특정 상품(옵션)을 이미 찜했는지 확인
    public WishDTO checkWish(int userNo, int productNo, int productOptionNo) {
        Connection con = null; 
        PreparedStatement pstmt = null; 
        ResultSet rs = null;
        WishDTO dto = null;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM wish WHERE userNo=? AND productNo=? AND productOptionNo=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userNo);
            pstmt.setInt(2, productNo);
            pstmt.setInt(3, productOptionNo);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                dto = new WishDTO();
                dto.setWishNo(rs.getInt("wishNo"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt, rs); }
        return dto;
    }

    
    // 내 찜 리스트 보기
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
                dto.setProductOptionNo(rs.getInt("productOptionNo"));
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
    
    
    // 찜 등록
    public boolean insertWish(WishDTO dto) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "INSERT INTO wish (userNo, productNo, productOptionNo) VALUES (?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, dto.getUserNo());
            pstmt.setInt(2, dto.getProductNo());
            pstmt.setInt(3, dto.getProductOptionNo());
            if (pstmt.executeUpdate() > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }
    
    // 찜 삭제
    public boolean deleteWishByInfo(int userNo, int productNo, int productOptionNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            String sql = "DELETE FROM wish WHERE userNo = ? AND productNo = ? AND productOptionNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userNo);
            pstmt.setInt(2, productNo);
            pstmt.setInt(3, productOptionNo);
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
