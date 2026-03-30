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

	// Select
	public Vector<ReviewImageDTO> getReviewImage() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ReviewImageDTO> vlist = new Vector<ReviewImageDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM reviewImage";
			pstmt = con.prepareStatement(sql);
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

	// Insert
	public boolean insertReviewImage(ReviewImageDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT ReviewImage (reviewNo, reviewImg, imgOrder) VALUES (?, ?, ?)";
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

	// Update
	public boolean updateReviewImage(ReviewImageDTO dto, int reviewImgNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE ReviewImage SET reviewNo = ?, reviewImg = ?, imgOrder = ? WHERE reviewImgNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getReviewNo());
			pstmt.setString(2, dto.getReviewImg());
			pstmt.setInt(3, dto.getImgOrder());
			pstmt.setInt(4, reviewImgNo);
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
	public boolean deleteReviewImage(int reviewImgNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM ReviewImage WHERE reviewImgNo = ?";
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
}

