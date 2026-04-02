package com.ondam.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.review.dto.ReviewDTO;

public class ReviewDAO {

    private DBConnectionMgr pool;

    public ReviewDAO() {
        pool = DBConnectionMgr.getInstance();
    }

    // 1. 구매한 물건에 대해 리뷰 작성
    public boolean insertReview(ReviewDTO dto) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "INSERT INTO review (orderItemNo, userNo, reviewRating, reviewContent, isBodyPublic, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, dto.getOrderItemNo());
            pstmt.setInt(2, dto.getUserNo());
            pstmt.setInt(3, dto.getReviewRating());
            pstmt.setString(4, dto.getReviewContent());
            pstmt.setInt(5, dto.getIsBodyPublic());
            pstmt.setString(6, dto.getCreatedAt());
            pstmt.setString(7, dto.getUpdatedAt());
            if (pstmt.executeUpdate() > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }

    // 2. 다른 사용자들의 전체 리뷰 보기 (내 리뷰 제외, isBodyPublic 무관하게 모두 조회)
    public Vector<ReviewDTO> getOtherUsersReviews(int myUserNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<ReviewDTO> vlist = new Vector<>();
        try {
            con = pool.getConnection();
            sql = "SELECT * FROM review WHERE userNo != ? ORDER BY createdAt DESC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, myUserNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ReviewDTO dto = new ReviewDTO();
                dto.setReviewNo(rs.getInt("reviewNo"));
                dto.setOrderItemNo(rs.getInt("orderItemNo"));
                dto.setUserNo(rs.getInt("userNo"));
                dto.setReviewRating(rs.getInt("reviewRating"));
                dto.setReviewContent(rs.getString("reviewContent"));
                dto.setIsBodyPublic(rs.getInt("isBodyPublic"));
                dto.setCreatedAt(rs.getString("createdAt"));
                dto.setUpdatedAt(rs.getString("updatedAt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }

    // 3. 특정 상품에 대해 다른 사용자들의 리뷰 보기 (내 리뷰 제외, isBodyPublic 무관하게 모두 조회)
    public Vector<ReviewDTO> getOtherUsersReviewsByItem(int orderItemNo, int myUserNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<ReviewDTO> vlist = new Vector<>();
        try {
            con = pool.getConnection();
            sql = "SELECT * FROM review WHERE orderItemNo = ? AND userNo != ? ORDER BY createdAt DESC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderItemNo);
            pstmt.setInt(2, myUserNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ReviewDTO dto = new ReviewDTO();
                dto.setReviewNo(rs.getInt("reviewNo"));
                dto.setOrderItemNo(rs.getInt("orderItemNo"));
                dto.setUserNo(rs.getInt("userNo"));
                dto.setReviewRating(rs.getInt("reviewRating"));
                dto.setReviewContent(rs.getString("reviewContent"));
                dto.setIsBodyPublic(rs.getInt("isBodyPublic"));
                dto.setCreatedAt(rs.getString("createdAt"));
                dto.setUpdatedAt(rs.getString("updatedAt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }

    // 4. 내 리뷰 보기
    public Vector<ReviewDTO> getReviewsByUserNo(int userNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<ReviewDTO> vlist = new Vector<>();
        try {
            con = pool.getConnection();
            sql = "SELECT * FROM review WHERE userNo = ? ORDER BY createdAt DESC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ReviewDTO dto = new ReviewDTO();
                dto.setReviewNo(rs.getInt("reviewNo"));
                dto.setOrderItemNo(rs.getInt("orderItemNo"));
                dto.setUserNo(rs.getInt("userNo"));
                dto.setReviewRating(rs.getInt("reviewRating"));
                dto.setReviewContent(rs.getString("reviewContent"));
                dto.setIsBodyPublic(rs.getInt("isBodyPublic"));
                dto.setCreatedAt(rs.getString("createdAt"));
                dto.setUpdatedAt(rs.getString("updatedAt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }

    // 5. 내 리뷰 수정
    public boolean updateMyReview(ReviewDTO dto) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "UPDATE review SET reviewRating = ?, reviewContent = ?, isBodyPublic = ?, updatedAt = ? WHERE reviewNo = ? AND userNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, dto.getReviewRating());
            pstmt.setString(2, dto.getReviewContent());
            pstmt.setInt(3, dto.getIsBodyPublic());
            pstmt.setString(4, dto.getUpdatedAt());
            pstmt.setInt(5, dto.getReviewNo());
            pstmt.setInt(6, dto.getUserNo());
            if (pstmt.executeUpdate() > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }

    // 6. 내 리뷰 삭제
    public boolean deleteMyReview(int reviewNo, int userNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "DELETE FROM review WHERE reviewNo = ? AND userNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reviewNo);
            pstmt.setInt(2, userNo);
            if (pstmt.executeUpdate() > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }
}