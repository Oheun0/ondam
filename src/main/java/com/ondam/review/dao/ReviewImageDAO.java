package com.ondam.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.review.dto.ReviewImageDTO;

public class ReviewImageDAO {

    private DBConnectionMgr pool;

    public ReviewImageDAO() {
        pool = DBConnectionMgr.getInstance();
    }

    public ReviewImageDTO getReviewImageByNo(int reviewImgNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        ReviewImageDTO dto = null;
        try {
            con = pool.getConnection();
            sql = "SELECT * FROM reviewImage WHERE reviewImgNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reviewImgNo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new ReviewImageDTO();
                dto.setReviewImgNo(rs.getInt("reviewImgNo"));
                dto.setReviewNo(rs.getInt("reviewNo"));
                dto.setReviewImg(rs.getString("reviewImg"));
                dto.setImgOrder(rs.getInt("imgOrder"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return dto;
    }

    public Vector<ReviewImageDTO> getReviewImagesByReviewNo(int reviewNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<ReviewImageDTO> vlist = new Vector<>();
        try {
            con = pool.getConnection();
            sql = "SELECT * FROM reviewImage WHERE reviewNo = ? ORDER BY imgOrder ASC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reviewNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ReviewImageDTO dto = new ReviewImageDTO();
                dto.setReviewImgNo(rs.getInt("reviewImgNo"));
                dto.setReviewNo(rs.getInt("reviewNo"));
                dto.setReviewImg(rs.getString("reviewImg"));
                dto.setImgOrder(rs.getInt("imgOrder"));
                vlist.addElement(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }

    public boolean insertReviewImage(ReviewImageDTO dto) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "INSERT INTO reviewImage (reviewNo, reviewImg, imgOrder) VALUES (?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, dto.getReviewNo());
            pstmt.setString(2, dto.getReviewImg());
            pstmt.setInt(3, dto.getImgOrder());
            if (pstmt.executeUpdate() > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }

    public boolean updateReviewImage(ReviewImageDTO dto) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "UPDATE reviewImage SET reviewImg = ?, imgOrder = ? WHERE reviewImgNo = ? AND reviewNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dto.getReviewImg());
            pstmt.setInt(2, dto.getImgOrder());
            pstmt.setInt(3, dto.getReviewImgNo());
            pstmt.setInt(4, dto.getReviewNo());
            if (pstmt.executeUpdate() > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }

    public boolean deleteReviewImage(int reviewImgNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "DELETE FROM reviewImage WHERE reviewImgNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reviewImgNo);
            if (pstmt.executeUpdate() > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }

    public boolean deleteReviewImagesByReviewNo(int reviewNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "DELETE FROM reviewImage WHERE reviewNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reviewNo);
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