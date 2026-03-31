package com.ondam.situation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.situation.dto.SituationDTO;

public class SituationDAO {

	private DBConnectionMgr pool;

	public SituationDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<SituationDTO> getSituation() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<SituationDTO> vlist = new Vector<SituationDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM situation";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SituationDTO dto = new SituationDTO();
				dto.setSituationNo(rs.getInt("situationNo"));
				dto.setUpSituationNo(rs.getInt("upSituationNo"));
				dto.setSituationLevel(rs.getInt("situationLevel"));
				dto.setSituationName(rs.getString("situationName"));
				dto.setSituationImg(rs.getString("situationImg"));
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
	public boolean insertSituation(SituationDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Situation (upSituationNo, situationLevel, situationName, situationImg) VALUES (?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getUpSituationNo());
			pstmt.setInt(2, dto.getSituationLevel());
			pstmt.setString(3, dto.getSituationName());
			pstmt.setString(4, dto.getSituationImg());
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
	public boolean updateSituation(SituationDTO dto, int situationNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Situation SET upSituationNo = ?, situationLevel = ?, situationName = ?, situationImg = ? WHERE situationNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getUpSituationNo());
			pstmt.setInt(2, dto.getSituationLevel());
			pstmt.setString(3, dto.getSituationName());
			pstmt.setString(4, dto.getSituationImg());
			pstmt.setInt(5, situationNo);
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
	public boolean deleteSituation(int situationNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Situation WHERE situationNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, situationNo);
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

