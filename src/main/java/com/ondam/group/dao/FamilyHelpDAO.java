package com.ondam.group.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.group.dto.FamilyHelpDTO;

public class FamilyHelpDAO {

	private DBConnectionMgr pool;

	public FamilyHelpDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select All
	public Vector<FamilyHelpDTO> getFamilyHelpList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<FamilyHelpDTO> vlist = new Vector<FamilyHelpDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM familyhelp";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				FamilyHelpDTO dto = new FamilyHelpDTO();
				dto.setFamilyHelpNo(rs.getInt("familyHelpNo"));
				dto.setFamilyNo(rs.getInt("familyNo"));
				dto.setHelperUserNo(rs.getInt("helperUserNo"));
				dto.setHelpeeUserNo(rs.getInt("helpeeUserNo"));
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
	public boolean insertFamilyHelp(FamilyHelpDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT INTO familyhelp (familyNo, helperUserNo, helpeeUserNo) VALUES (?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getFamilyNo());
			pstmt.setInt(2, dto.getHelperUserNo());
			pstmt.setInt(3, dto.getHelpeeUserNo());
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
	public boolean updateFamilyHelp(FamilyHelpDTO dto, int familyHelpNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE familyhelp SET familyNo = ?, helperUserNo = ?, helpeeUserNo = ? WHERE familyHelpNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getFamilyNo());
			pstmt.setInt(2, dto.getHelperUserNo());
			pstmt.setInt(3, dto.getHelpeeUserNo());
			pstmt.setInt(4, familyHelpNo);
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
	public boolean deleteFamilyHelp(int familyHelpNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM familyhelp WHERE familyHelpNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, familyHelpNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	// 내가 돕는 helpeeUserNo 목록 조회
	public Vector<Integer> getHelpeeUserNosByHelper(int helperUserNo, int familyNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Vector<Integer> list = new Vector<>();
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT helpeeUserNo FROM familyhelp WHERE helperUserNo = ? AND familyNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, helperUserNo);
	        pstmt.setInt(2, familyNo);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            list.add(rs.getInt("helpeeUserNo"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return list;
	}

	// helperUserNo + helpeeUserNo로 단건 삭제
	public boolean deleteByHelperAndHelpee(int helperUserNo, int helpeeUserNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    boolean flag = false;
	    try {
	        con = pool.getConnection();
	        String sql = "DELETE FROM familyhelp WHERE helperUserNo = ? AND helpeeUserNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, helperUserNo);
	        pstmt.setInt(2, helpeeUserNo);
	        if (pstmt.executeUpdate() > 0) flag = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt);
	    }
	    return flag;
	}
}