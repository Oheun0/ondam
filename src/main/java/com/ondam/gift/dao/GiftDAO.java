package com.ondam.gift.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.gift.dto.GiftDTO;

public class GiftDAO {

	private DBConnectionMgr pool;

	public GiftDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<GiftDTO> getGift() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<GiftDTO> vlist = new Vector<GiftDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM gift";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				GiftDTO dto = new GiftDTO();
				dto.setGiftNo(rs.getInt("giftNo"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setSenderNo(rs.getInt("senderNo"));
				dto.setReceiverNo(rs.getInt("receiverNo"));
				dto.setGiftMsg(rs.getString("giftMsg"));
				dto.setGiftState(rs.getInt("giftState"));
				dto.setSentAt(rs.getString("sentAt"));
				dto.setRespondedAt(rs.getString("respondedAt"));
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
	public boolean insertGift(GiftDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Gift (orderNo, senderNo, receiverNo, giftMsg, giftState, sentAt, respondedAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getOrderNo());
			pstmt.setInt(2, dto.getSenderNo());
			pstmt.setInt(3, dto.getReceiverNo());
			pstmt.setString(4, dto.getGiftMsg());
			pstmt.setInt(5, dto.getGiftState());
			pstmt.setString(6, dto.getSentAt());
			pstmt.setString(7, dto.getRespondedAt());
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
	public boolean updateGift(GiftDTO dto, int giftNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Gift SET orderNo = ?, senderNo = ?, receiverNo = ?, giftMsg = ?, giftState = ?, sentAt = ?, respondedAt = ? WHERE giftNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getOrderNo());
			pstmt.setInt(2, dto.getSenderNo());
			pstmt.setInt(3, dto.getReceiverNo());
			pstmt.setString(4, dto.getGiftMsg());
			pstmt.setInt(5, dto.getGiftState());
			pstmt.setString(6, dto.getSentAt());
			pstmt.setString(7, dto.getRespondedAt());
			pstmt.setInt(8, giftNo);
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
	public boolean deleteGift(int giftNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Gift WHERE giftNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, giftNo);
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

