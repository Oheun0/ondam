package com.ondam.poke.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.poke.dto.PokeDTO;

public class PokeDAO {

	private DBConnectionMgr pool;

	public PokeDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<PokeDTO> getPoke() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<PokeDTO> vlist = new Vector<PokeDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM poke";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PokeDTO dto = new PokeDTO();
				dto.setPokeNo(rs.getInt("pokeNo"));
				dto.setProductNo(rs.getInt("productNo"));
				dto.setSenderNo(rs.getInt("senderNo"));
				dto.setReceiverNo(rs.getInt("receiverNo"));
				dto.setFamilyNo(rs.getInt("familyNo"));
				dto.setPokeMsg(rs.getString("pokeMsg"));
				dto.setSendState(rs.getInt("sendState"));
				dto.setSendDate(rs.getString("sendDate"));
				dto.setConnectedOrderNo(rs.getInt("connectedOrderNo"));
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
	public boolean insertPoke(PokeDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Poke (productNo, senderNo, receiverNo, familyNo, pokeMsg, sendState, sendDate, connectedOrderNo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getProductNo());
			pstmt.setInt(2, dto.getSenderNo());
			pstmt.setInt(3, dto.getReceiverNo());
			pstmt.setInt(4, dto.getFamilyNo());
			pstmt.setString(5, dto.getPokeMsg());
			pstmt.setInt(6, dto.getSendState());
			pstmt.setString(7, dto.getSendDate());
			pstmt.setInt(8, dto.getConnectedOrderNo());
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
	public boolean updatePoke(PokeDTO dto, int pokeNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Poke SET productNo = ?, senderNo = ?, receiverNo = ?, familyNo = ?, pokeMsg = ?, sendState = ?, sendDate = ?, connectedOrderNo = ? WHERE pokeNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getProductNo());
			pstmt.setInt(2, dto.getSenderNo());
			pstmt.setInt(3, dto.getReceiverNo());
			pstmt.setInt(4, dto.getFamilyNo());
			pstmt.setString(5, dto.getPokeMsg());
			pstmt.setInt(6, dto.getSendState());
			pstmt.setString(7, dto.getSendDate());
			pstmt.setInt(8, dto.getConnectedOrderNo());
			pstmt.setInt(9, pokeNo);
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
	public boolean deletePoke(int pokeNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Poke WHERE pokeNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pokeNo);
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

