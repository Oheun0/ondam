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

	// Select
	public Vector<ReviewDTO> getReview() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ReviewDTO> vlist = new Vector<ReviewDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM review";
			pstmt = con.prepareStatement(sql);
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
	public boolean insertReview(ReviewDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Review (orderItemNo, userNo, reviewRating, reviewContent, isBodyPublic, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getOrderItemNo());
			pstmt.setInt(2, dto.getUserNo());
			pstmt.setInt(3, dto.getReviewRating());
			pstmt.setString(4, dto.getReviewContent());
			pstmt.setInt(5, dto.getIsBodyPublic());
			pstmt.setString(6, dto.getCreatedAt());
			pstmt.setString(7, dto.getUpdatedAt());
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
	public boolean updateReview(ReviewDTO dto, int reviewNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Review SET orderItemNo = ?, userNo = ?, reviewRating = ?, reviewContent = ?, isBodyPublic = ?, createdAt = ?, updatedAt = ? WHERE reviewNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getOrderItemNo());
			pstmt.setInt(2, dto.getUserNo());
			pstmt.setInt(3, dto.getReviewRating());
			pstmt.setString(4, dto.getReviewContent());
			pstmt.setInt(5, dto.getIsBodyPublic());
			pstmt.setString(6, dto.getCreatedAt());
			pstmt.setString(7, dto.getUpdatedAt());
			pstmt.setInt(8, reviewNo);
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
	public boolean deleteReview(int reviewNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Review WHERE reviewNo = ?";
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

