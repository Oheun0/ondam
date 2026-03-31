package com.ondam.group.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.group.dto.FamilyMemberDTO;

public class FamilyMemberDAO {

	private DBConnectionMgr pool;

	public FamilyMemberDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<FamilyMemberDTO> getFamilyMember() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<FamilyMemberDTO> vlist = new Vector<FamilyMemberDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM familyMember";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				FamilyMemberDTO dto = new FamilyMemberDTO();
				dto.setFamilyMemberNo(rs.getInt("familyMemberNo"));
				dto.setFamilyNo(rs.getInt("familyNo"));
				dto.setUserNo(rs.getInt("userNo"));
				dto.setFamilyAuth(rs.getInt("familyAuth"));
				dto.setFamilyRelation(rs.getString("familyRelation"));
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
	public boolean insertFamilyMember(FamilyMemberDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT FamilyMember (familyNo, userNo, familyAuth, familyRelation) VALUES (?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getFamilyNo());
			pstmt.setInt(2, dto.getUserNo());
			pstmt.setInt(3, dto.getFamilyAuth());
			pstmt.setString(4, dto.getFamilyRelation());
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
	public boolean updateFamilyMember(FamilyMemberDTO dto, int familyMemberNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE FamilyMember SET familyNo = ?, userNo = ?, familyAuth = ?, familyRelation = ? WHERE familyMemberNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getFamilyNo());
			pstmt.setInt(2, dto.getUserNo());
			pstmt.setInt(3, dto.getFamilyAuth());
			pstmt.setString(4, dto.getFamilyRelation());
			pstmt.setInt(5, familyMemberNo);
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
	public boolean deleteFamilyMember(int familyMemberNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM FamilyMember WHERE familyMemberNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, familyMemberNo);
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

