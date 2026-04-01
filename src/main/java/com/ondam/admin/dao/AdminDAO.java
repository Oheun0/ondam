package com.ondam.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.admin.dto.AdminDTO;

public class AdminDAO {

	private DBConnectionMgr pool;

	public AdminDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<AdminDTO> getAdmin() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<AdminDTO> vlist = new Vector<AdminDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM admin";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				AdminDTO dto = new AdminDTO();
				dto.setAdminNo(rs.getInt("adminNo"));
				dto.setAdminId(rs.getString("adminId"));
				dto.setAdminPwd(rs.getString("adminPwd"));
				dto.setAdminName(rs.getString("adminName"));
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
	public boolean insertAdmin(AdminDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Admin (adminId, adminPwd, adminName) VALUES (?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getAdminId());
			pstmt.setString(2, dto.getAdminPwd());
			pstmt.setString(3, dto.getAdminName());
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
	public boolean updateAdmin(AdminDTO dto, int adminNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Admin SET adminId = ?, adminPwd = ?, adminName = ? WHERE adminNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getAdminId());
			pstmt.setString(2, dto.getAdminPwd());
			pstmt.setString(3, dto.getAdminName());
			pstmt.setInt(4, adminNo);
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
	public boolean deleteAdmin(int adminNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Admin WHERE adminNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, adminNo);
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

