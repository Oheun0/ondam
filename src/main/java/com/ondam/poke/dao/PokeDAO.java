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
				int val = rs.getInt("connectedOrderNo");
				dto.setConnectedOrderNo(rs.wasNull() ? null : val);
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
			if (dto.getConnectedOrderNo() == null) {
			    pstmt.setNull(8, java.sql.Types.INTEGER);
			} else {
			    pstmt.setInt(8, dto.getConnectedOrderNo());
			}
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
			if (dto.getConnectedOrderNo() == null) {
			    pstmt.setNull(8, java.sql.Types.INTEGER);
			} else {
			    pstmt.setInt(8, dto.getConnectedOrderNo());
			}
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

	// 받은 조르기 목록
	public Vector<PokeDTO> getByReceiverNo(int receiverNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<PokeDTO> vlist = new Vector<>();
		try {
			con = pool.getConnection();
			String sql = "SELECT * FROM poke WHERE receiverNo = ? ORDER BY sendDate DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, receiverNo);
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
				int val = rs.getInt("connectedOrderNo");
				dto.setConnectedOrderNo(rs.wasNull() ? null : val);
				vlist.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}

	// 보낸 조르기 목록
	public Vector<PokeDTO> getBySenderNo(int senderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<PokeDTO> vlist = new Vector<>();
		try {
			con = pool.getConnection();
			String sql = "SELECT * FROM poke WHERE senderNo = ? ORDER BY sendDate DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, senderNo);
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
				int val = rs.getInt("connectedOrderNo");
				dto.setConnectedOrderNo(rs.wasNull() ? null : val);
				vlist.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}

	// 단건 조회 (respond용)
	public PokeDTO getPokeById(int pokeNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PokeDTO dto = null;
		try {
			con = pool.getConnection();
			String sql = "SELECT * FROM poke WHERE pokeNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pokeNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new PokeDTO();
				dto.setPokeNo(rs.getInt("pokeNo"));
				dto.setProductNo(rs.getInt("productNo"));
				dto.setSenderNo(rs.getInt("senderNo"));
				dto.setReceiverNo(rs.getInt("receiverNo"));
				dto.setFamilyNo(rs.getInt("familyNo"));
				dto.setPokeMsg(rs.getString("pokeMsg"));
				dto.setSendState(rs.getInt("sendState"));
				dto.setSendDate(rs.getString("sendDate"));
				int val = rs.getInt("connectedOrderNo");
				dto.setConnectedOrderNo(rs.wasNull() ? null : val);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return dto;
	}

	// sendState만 업데이트 (수락/거절)
	public boolean updateSendState(int pokeNo, int sendState) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "UPDATE poke SET sendState = ? WHERE pokeNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sendState);
			pstmt.setInt(2, pokeNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	// 생성된 pokeNo 반환 (실패시 -1)
	public int insertPokeAndGetNo(PokeDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int newPokeNo = -1;
		try {
			con = pool.getConnection();
			String sql = "INSERT INTO Poke (productNo, senderNo, receiverNo, familyNo, pokeMsg, sendState, sendDate, connectedOrderNo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, dto.getProductNo());
			pstmt.setInt(2, dto.getSenderNo());
			pstmt.setInt(3, dto.getReceiverNo());
			pstmt.setInt(4, dto.getFamilyNo());
			pstmt.setString(5, dto.getPokeMsg());
			pstmt.setInt(6, dto.getSendState());
			pstmt.setString(7, dto.getSendDate());
			if (dto.getConnectedOrderNo() == null) {
			    pstmt.setNull(8, java.sql.Types.INTEGER);
			} else {
			    pstmt.setInt(8, dto.getConnectedOrderNo());
			}
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next())
				newPokeNo = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return newPokeNo;
	}
}