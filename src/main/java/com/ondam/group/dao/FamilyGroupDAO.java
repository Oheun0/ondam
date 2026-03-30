package com.ondam.group.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.group.dto.FamilyGroupDTO;

public class FamilyGroupDAO {

	private DBConnectionMgr pool;

	public FamilyGroupDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<FamilyGroupDTO> getFamilyGroup() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<FamilyGroupDTO> vlist = new Vector<FamilyGroupDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM familyGroup";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				FamilyGroupDTO dto = new FamilyGroupDTO();
				dto.setFamilyNo(rs.getInt("familyNo"));
				dto.setFamilyName(rs.getString("familyName"));
				dto.setFamilyDate(rs.getString("familyDate"));
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
	public boolean insertFamilyGroup(FamilyGroupDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT FamilyGroup (familyName, familyDate) VALUES (?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getFamilyName());
			pstmt.setString(2, dto.getFamilyDate());
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
	public boolean updateFamilyGroup(FamilyGroupDTO dto, int familyNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE FamilyGroup SET familyName = ?, familyDate = ? WHERE familyNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getFamilyName());
			pstmt.setString(2, dto.getFamilyDate());
			pstmt.setInt(3, familyNo);
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
	public boolean deleteFamilyGroup(int familyNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM FamilyGroup WHERE familyNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, familyNo);
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