package com.ondam.group.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.group.dto.FamilyInviteDTO;

public class FamilyInviteDAO {

	private DBConnectionMgr pool;

	public FamilyInviteDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<FamilyInviteDTO> getFamilyInvite() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<FamilyInviteDTO> vlist = new Vector<FamilyInviteDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM familyInvite";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				FamilyInviteDTO dto = new FamilyInviteDTO();
				dto.setInvitationNo(rs.getInt("invitationNo"));
				dto.setFamilyNo(rs.getInt("familyNo"));
				dto.setInviterNo(rs.getInt("inviterNo"));
				dto.setInviteeNo(rs.getInt("inviteeNo"));
				dto.setInviteeKakaoUuid(rs.getString("inviteeKakaoUuid"));
				dto.setInvitationToken(rs.getString("invitationToken"));
				dto.setInvitationStatus(rs.getInt("invitationStatus"));
				dto.setInvitedAt(rs.getString("invitedAt"));
				dto.setRespondedAt(rs.getString("respondedAt"));
				dto.setExpiresAt(rs.getString("expiresAt"));
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
	public boolean insertFamilyInvite(FamilyInviteDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT FamilyInvite (familyNo, inviterNo, inviteeNo, inviteeKakaoUuid, invitationToken, invitationStatus, invitedAt, respondedAt, expiresAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getFamilyNo());
			pstmt.setInt(2, dto.getInviterNo());
			pstmt.setInt(3, dto.getInviteeNo());
			pstmt.setString(4, dto.getInviteeKakaoUuid());
			pstmt.setString(5, dto.getInvitationToken());
			pstmt.setInt(6, dto.getInvitationStatus());
			pstmt.setString(7, dto.getInvitedAt());
			pstmt.setString(8, dto.getRespondedAt());
			pstmt.setString(9, dto.getExpiresAt());
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
	public boolean updateFamilyInvite(FamilyInviteDTO dto, int invitationNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE FamilyInvite SET familyNo = ?, inviterNo = ?, inviteeNo = ?, inviteeKakaoUuid = ?, invitationToken = ?, invitationStatus = ?, invitedAt = ?, respondedAt = ?, expiresAt = ? WHERE invitationNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getFamilyNo());
			pstmt.setInt(2, dto.getInviterNo());
			pstmt.setInt(3, dto.getInviteeNo());
			pstmt.setString(4, dto.getInviteeKakaoUuid());
			pstmt.setString(5, dto.getInvitationToken());
			pstmt.setInt(6, dto.getInvitationStatus());
			pstmt.setString(7, dto.getInvitedAt());
			pstmt.setString(8, dto.getRespondedAt());
			pstmt.setString(9, dto.getExpiresAt());
			pstmt.setInt(10, invitationNo);
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
	public boolean deleteFamilyInvite(int invitationNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM FamilyInvite WHERE invitationNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, invitationNo);
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

