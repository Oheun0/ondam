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

	// userNo로 본인의 familyMember 정보 조회 (어느 그룹 소속인지 확인)
	public FamilyMemberDTO getFamilyMemberByUserNo(int userNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    FamilyMemberDTO dto = null;
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT * FROM FamilyMember WHERE userNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, userNo);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            dto = new FamilyMemberDTO();
	            dto.setFamilyMemberNo(rs.getInt("familyMemberNo"));
	            dto.setFamilyNo(rs.getInt("familyNo"));
	            dto.setUserNo(rs.getInt("userNo"));
	            dto.setFamilyAuth(rs.getInt("familyAuth"));
	            dto.setUserName(rs.getString("userName"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return dto;
	}

	// familyNo로 같은 그룹 멤버 전체 조회
	public Vector<FamilyMemberDTO> getFamilyMembersByFamilyNo(int familyNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Vector<FamilyMemberDTO> vlist = new Vector<>();
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT * FROM FamilyMember WHERE familyNo = ? ORDER BY familyAuth DESC";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, familyNo);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            FamilyMemberDTO dto = new FamilyMemberDTO();
	            dto.setFamilyMemberNo(rs.getInt("familyMemberNo"));
	            dto.setFamilyNo(rs.getInt("familyNo"));
	            dto.setUserNo(rs.getInt("userNo"));
	            dto.setFamilyAuth(rs.getInt("familyAuth"));
	            dto.setUserName(rs.getString("userName"));
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
			sql = "INSERT FamilyMember (familyNo, userNo, familyAuth, userName) VALUES (?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getFamilyNo());
			pstmt.setInt(2, dto.getUserNo());
			pstmt.setInt(3, dto.getFamilyAuth());
			pstmt.setString(4, dto.getUserName());
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
			sql = "UPDATE FamilyMember SET familyNo = ?, userNo = ?, familyAuth = ?, userName = ? WHERE familyMemberNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getFamilyNo());
			pstmt.setInt(2, dto.getUserNo());
			pstmt.setInt(3, dto.getFamilyAuth());
			pstmt.setString(4, dto.getUserName());
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
	
	// familyMemberNo로 familyAuth 업데이트 (관리자 위임용)
	public boolean updateFamilyAuth(int familyMemberNo, int familyAuth) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    boolean flag = false;
	    try {
	        con = pool.getConnection();
	        String sql = "UPDATE FamilyMember SET familyAuth = ? WHERE familyMemberNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, familyAuth);
	        pstmt.setInt(2, familyMemberNo);
	        if (pstmt.executeUpdate() > 0) flag = true;
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