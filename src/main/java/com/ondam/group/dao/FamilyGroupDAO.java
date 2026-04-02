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
				dto.setFamilyInviteCode(rs.getString("familyInviteCode"));
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
	
	// familyNo로 그룹 단건 조회
	public FamilyGroupDTO getFamilyGroupByNo(int familyNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    FamilyGroupDTO dto = null;
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT * FROM FamilyGroup WHERE familyNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, familyNo);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            dto = new FamilyGroupDTO();
	            dto.setFamilyNo(rs.getInt("familyNo"));
	            dto.setFamilyName(rs.getString("familyName"));
	            dto.setFamilyInviteCode(rs.getString("familyInviteCode"));
	            dto.setFamilyDate(rs.getString("familyDate"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return dto;
	}

	// Insert
	public boolean insertFamilyGroup(FamilyGroupDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT FamilyGroup (familyName, familyInviteCode, familyDate) VALUES (?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getFamilyName());
			pstmt.setString(2, dto.getFamilyInviteCode());
			pstmt.setString(3, dto.getFamilyDate());
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	// INSERT 후 생성된 familyNo 반환 (RETURN_GENERATED_KEYS 활용)
	public int insertFamilyGroupAndGetNo(FamilyGroupDTO dto) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int generatedNo = -1;
	    try {
	        con = pool.getConnection();
	        String sql = "INSERT INTO FamilyGroup (familyName, familyInviteCode, familyDate) VALUES (?, ?, ?)";
	        pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	        pstmt.setString(1, dto.getFamilyName());
	        pstmt.setString(2, dto.getFamilyInviteCode());
	        pstmt.setString(3, dto.getFamilyDate());
	        pstmt.executeUpdate();
	        rs = pstmt.getGeneratedKeys();
	        if (rs.next()) {
	            generatedNo = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return generatedNo; // -1이면 실패
	}

	// Update
	public boolean updateFamilyGroup(FamilyGroupDTO dto, int familyNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE FamilyGroup SET familyName = ?, familyInviteCode = ?, familyDate = ? WHERE familyNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getFamilyName());
			pstmt.setString(2, dto.getFamilyInviteCode());
			pstmt.setString(3, dto.getFamilyDate());
			pstmt.setInt(4, familyNo);
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
	
	// 초대코드로 그룹 조회
	public FamilyGroupDTO getFamilyGroupByInviteCode(String inviteCode) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    FamilyGroupDTO dto = null;
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT * FROM FamilyGroup WHERE familyInviteCode = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, inviteCode);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            dto = new FamilyGroupDTO();
	            dto.setFamilyNo(rs.getInt("familyNo"));
	            dto.setFamilyName(rs.getString("familyName"));
	            dto.setFamilyInviteCode(rs.getString("familyInviteCode"));
	            dto.setFamilyDate(rs.getString("familyDate"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return dto;
	}
}