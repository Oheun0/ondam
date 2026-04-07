package com.ondam.situation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.situation.dto.SituationMappingDTO;

public class SituationMappingDAO {

	private DBConnectionMgr pool;

	public SituationMappingDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<SituationMappingDTO> getSituationMapping() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<SituationMappingDTO> vlist = new Vector<SituationMappingDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM situationMapping";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SituationMappingDTO dto = new SituationMappingDTO();
				dto.setSituationMapNo(rs.getInt("situationMapNo"));
				dto.setSituationNo(rs.getInt("situationNo"));
				dto.setProductNo(rs.getInt("productNo"));
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
	public boolean insertSituationMapping(SituationMappingDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT SituationMapping (situationNo, productNo) VALUES (?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getSituationNo());
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

	// Update
	public boolean updateSituationMapping(SituationMappingDTO dto, int situationMapNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE SituationMapping SET situationNo = ?, productNo = ? WHERE situationMapNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getSituationNo());
			pstmt.setInt(2, dto.getProductNo());
			pstmt.setInt(3, situationMapNo);
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
	public boolean deleteSituationMapping(int situationMapNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM SituationMapping WHERE situationMapNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, situationMapNo);
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