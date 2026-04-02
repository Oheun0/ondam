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
			pstmt = con.prepareStatement("SELECT * FROM shorts");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ShortsDTO dto = new ShortsDTO();
				dto.setShortsNo(rs.getInt("shortsNo"));
				dto.setVendorNo(rs.getInt("vendorNo"));
				dto.setProductNo(rs.getInt("productNo"));
				dto.setShortsTitle(rs.getString("shortsTitle"));
				dto.setShortsContent(rs.getString("shortsContent"));
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
			String sql = "INSERT INTO shorts (vendorNo, productNo, shortsTitle, shortsContent, videoFile, thumbnailImg, shortsState, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setInt(2, dto.getProductNo());
			pstmt.setString(3, dto.getShortsTitle());
			pstmt.setString(4, dto.getShortsContent());
			pstmt.setString(5, dto.getVideoFile());
			pstmt.setString(6, dto.getThumbnailImg());
			pstmt.setInt(7, dto.getShortsState());
			pstmt.setString(8, dto.getCreatedAt());
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
			String sql = "UPDATE shorts SET vendorNo = ?, productNo = ?, shortsTitle = ?, shortsContent = ?, videoFile = ?, thumbnailImg = ?, shortsState = ?, createdAt = ? WHERE shortsNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setInt(2, dto.getProductNo());
			pstmt.setString(3, dto.getShortsTitle());
			pstmt.setString(4, dto.getShortsContent());
			pstmt.setString(5, dto.getVideoFile());
			pstmt.setString(6, dto.getThumbnailImg());
			pstmt.setInt(7, dto.getShortsState());
			pstmt.setString(8, dto.getCreatedAt());
			pstmt.setInt(9, shortsNo);
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
			String sql = "DELETE FROM shorts WHERE shortsNo = ?";
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
			String sql = "UPDATE shorts SET shortsState = ? WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, state);
			pstmt.setInt(2, productNo);
			if (pstmt.executeUpdate() > 0) flag = true;
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt); }
		return flag;
	}

	// productNo 기준으로 파일정보 및 상태 갱신 (파이썬 자동 생성 성공 시 사용)
	public boolean updateShortsByProductNo(ShortsDTO dto) {
		Connection con = null; PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "UPDATE shorts SET videoFile = ?, thumbnailImg = ?, shortsTitle = ?, shortsContent = ?, shortsState = ? WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getVideoFile());
			pstmt.setString(2, dto.getThumbnailImg());
			pstmt.setString(3, dto.getShortsTitle());
			pstmt.setString(4, dto.getShortsContent());
			pstmt.setInt(5, dto.getShortsState());
			pstmt.setInt(6, dto.getProductNo());
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
	        String sql = "SELECT * FROM shorts WHERE productNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, productNo);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            dto = new ShortsDTO();
	            dto.setShortsNo(rs.getInt("shortsNo"));
	            dto.setVendorNo(rs.getInt("vendorNo"));
	            dto.setProductNo(rs.getInt("productNo"));
	            dto.setShortsTitle(rs.getString("shortsTitle"));
				dto.setShortsContent(rs.getString("shortsContent"));
	            dto.setVideoFile(rs.getString("videoFile"));
	            dto.setThumbnailImg(rs.getString("thumbnailImg"));
	            dto.setShortsState(rs.getInt("shortsState"));
	            dto.setCreatedAt(rs.getString("createdAt"));
	        }
	    } catch (Exception e) { e.printStackTrace(); } 
	    finally { pool.freeConnection(con, pstmt, rs); }
	    return dto;
	}
	
	// 특정 사용자의 숏폼 목록 조회 (벤더별 숏폼 관리용)
	public Vector<ShortsDTO> getShortsByVendor(int vendorNo) {
	    Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
	    Vector<ShortsDTO> vlist = new Vector<>();
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT * FROM shorts WHERE vendorNo = ? ORDER BY createdAt DESC";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, vendorNo);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            ShortsDTO dto = new ShortsDTO();
	            dto.setShortsNo(rs.getInt("shortsNo"));
				dto.setVendorNo(rs.getInt("vendorNo"));
				dto.setProductNo(rs.getInt("productNo"));
				dto.setShortsTitle(rs.getString("shortsTitle"));
				dto.setShortsContent(rs.getString("shortsContent"));
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

	// [로직 추가] 수동 업로드 시 영상, 썸네일, 제목, 내용, 상태를 모두 업데이트
	public boolean updateManualShorts(ShortsDTO dto) {
		Connection con = null; PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "UPDATE shorts SET videoFile = ?, thumbnailImg = ?, shortsTitle = ?, shortsContent = ?, shortsState = ? WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getVideoFile());
			pstmt.setString(2, dto.getThumbnailImg());
			pstmt.setString(3, dto.getShortsTitle());
			pstmt.setString(4, dto.getShortsContent());
			pstmt.setInt(5, dto.getShortsState());
			pstmt.setInt(6, dto.getProductNo());
			if (pstmt.executeUpdate() > 0) flag = true;
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt); }
		return flag;
	}
}