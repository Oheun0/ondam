package com.ondam.shorts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.shorts.dto.ShortsDTO;

public class ShortsDAO {

	private DBConnectionMgr pool;

	public ShortsDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<ShortsDTO> getShorts() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ShortsDTO> vlist = new Vector<ShortsDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM shorts";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ShortsDTO dto = new ShortsDTO();
				dto.setShortsNo(rs.getInt("shortsNo"));
				dto.setVendorNo(rs.getInt("vendorNo"));
				dto.setProductNo(rs.getInt("productNo"));
				dto.setVideoFile(rs.getString("videoFile"));
				dto.setThumbnailImg(rs.getString("thumbnailImg"));
				dto.setShortsState(rs.getInt("shortsState"));
				dto.setCreatedAt(rs.getString("createdAt"));
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
	public boolean insertShorts(ShortsDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Shorts (vendorNo, productNo, videoFile, thumbnailImg, shortsState, createdAt) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setInt(2, dto.getProductNo());
			pstmt.setString(3, dto.getVideoFile());
			pstmt.setString(4, dto.getThumbnailImg());
			pstmt.setInt(5, dto.getShortsState());
			pstmt.setString(6, dto.getCreatedAt());
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
	public boolean updateShorts(ShortsDTO dto, int shortsNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Shorts SET vendorNo = ?, productNo = ?, videoFile = ?, thumbnailImg = ?, shortsState = ?, createdAt = ? WHERE shortsNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setInt(2, dto.getProductNo());
			pstmt.setString(3, dto.getVideoFile());
			pstmt.setString(4, dto.getThumbnailImg());
			pstmt.setInt(5, dto.getShortsState());
			pstmt.setString(6, dto.getCreatedAt());
			pstmt.setInt(7, shortsNo);
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
	public boolean deleteShorts(int shortsNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Shorts WHERE shortsNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, shortsNo);
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

