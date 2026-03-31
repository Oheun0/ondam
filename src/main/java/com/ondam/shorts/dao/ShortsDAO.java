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

	// Select All
	public Vector<ShortsDTO> getShorts() {
		Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
		Vector<ShortsDTO> vlist = new Vector<ShortsDTO>();
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement("SELECT * FROM Shorts");
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
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt, rs); }
		return vlist;
	}

	// Insert
	public boolean insertShorts(ShortsDTO dto) {
		Connection con = null; PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "INSERT Shorts (vendorNo, productNo, videoFile, thumbnailImg, shortsState, createdAt) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setInt(2, dto.getProductNo());
			pstmt.setString(3, dto.getVideoFile());
			pstmt.setString(4, dto.getThumbnailImg());
			pstmt.setInt(5, dto.getShortsState());
			pstmt.setString(6, dto.getCreatedAt());
			if (pstmt.executeUpdate() > 0) flag = true;
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt); }
		return flag;
	}

	// Update (전체 수정)
	public boolean updateShorts(ShortsDTO dto, int shortsNo) {
		Connection con = null; PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "UPDATE Shorts SET vendorNo = ?, productNo = ?, videoFile = ?, thumbnailImg = ?, shortsState = ?, createdAt = ? WHERE shortsNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setInt(2, dto.getProductNo());
			pstmt.setString(3, dto.getVideoFile());
			pstmt.setString(4, dto.getThumbnailImg());
			pstmt.setInt(5, dto.getShortsState());
			pstmt.setString(6, dto.getCreatedAt());
			pstmt.setInt(7, shortsNo);
			if (pstmt.executeUpdate() > 0) flag = true;
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt); }
		return flag;
	}

	// Delete
	public boolean deleteShorts(int shortsNo) {
		Connection con = null; PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "DELETE FROM Shorts WHERE shortsNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, shortsNo);
			if (pstmt.executeUpdate() > 0) flag = true;
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt); }
		return flag;
	}
	
	// 특정 상품의 숏폼 상태만 빠르게 업데이트 (-1, 0, 1, 2)
	public boolean updateShortsState(int productNo, int state) {
		Connection con = null; PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "UPDATE Shorts SET shortsState = ? WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, state);
			pstmt.setInt(2, productNo);
			if (pstmt.executeUpdate() > 0) flag = true;
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt); }
		return flag;
	}

	// productNo 기준으로 파일정보 및 상태 갱신 (파이썬 성공 시 사용)
	public boolean updateShortsByProductNo(ShortsDTO dto) {
		Connection con = null; PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "UPDATE Shorts SET videoFile = ?, thumbnailImg = ?, shortsState = ? WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getVideoFile());
			pstmt.setString(2, dto.getThumbnailImg());
			pstmt.setInt(3, dto.getShortsState());
			pstmt.setInt(4, dto.getProductNo());
			if (pstmt.executeUpdate() > 0) flag = true;
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt); }
		return flag;
	}
	
	// 특정 상품의 숏폼 정보 단건 조회 (중복 체크용)
	public ShortsDTO getShortByProductNo(int productNo) {
	    Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
	    ShortsDTO dto = null;
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT * FROM Shorts WHERE productNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, productNo);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            dto = new ShortsDTO();
	            dto.setShortsNo(rs.getInt("shortsNo"));
	            dto.setVendorNo(rs.getInt("vendorNo"));
	            dto.setProductNo(rs.getInt("productNo"));
	            dto.setVideoFile(rs.getString("videoFile"));
	            dto.setThumbnailImg(rs.getString("thumbnailImg"));
	            dto.setShortsState(rs.getInt("shortsState"));
	            dto.setCreatedAt(rs.getString("createdAt"));
	        }
	    } catch (Exception e) { e.printStackTrace(); } 
	    finally { pool.freeConnection(con, pstmt, rs); }
	    return dto;
	}
	
	// 특정 사용자의 숏폼 조회 (매핑 완성)
	public Vector<ShortsDTO> getShortsByVendor(int vendorNo) {
	    Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
	    Vector<ShortsDTO> vlist = new Vector<>();
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT * FROM Shorts WHERE vendorNo = ? ORDER BY createdAt DESC";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, vendorNo);
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
	    } catch (Exception e) { e.printStackTrace(); } 
	    finally { pool.freeConnection(con, pstmt, rs); }
	    return vlist;
	}
}