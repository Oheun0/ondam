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

	// 1. 전체 조회 (관리자용)
	public Vector<GiftDTO> getGift() {
		Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
		Vector<GiftDTO> vlist = new Vector<GiftDTO>();
		try {
			con = pool.getConnection();
			String sql = "SELECT * FROM gift ORDER BY giftNo DESC";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				vlist.addElement(extractDTO(rs));
			}
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt, rs); }
		return vlist;
	}

	// 2. 특정 선물 단건 조회 (선물 상세 페이지용)
	public GiftDTO getGiftById(int giftNo) {
		Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
		GiftDTO dto = null;
		try {
			con = pool.getConnection();
			String sql = "SELECT * FROM gift WHERE giftNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, giftNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = extractDTO(rs);
			}
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt, rs); }
		return dto;
	}

	// 3. 주문 번호로 단건 조회 (UNIQUE 제약조건 활용, 주문-선물 매핑용)
	public GiftDTO getGiftByOrderNo(int orderNo) {
		Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
		GiftDTO dto = null;
		try {
			con = pool.getConnection();
			String sql = "SELECT * FROM gift WHERE orderNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, orderNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = extractDTO(rs);
			}
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt, rs); }
		return dto;
	}

	// 4. 내가 보낸 선물 보기
	public Vector<GiftDTO> getSentGifts(int senderNo) {
		Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
		Vector<GiftDTO> vlist = new Vector<GiftDTO>();
		try {
			con = pool.getConnection();
			String sql = "SELECT * FROM gift WHERE senderNo = ? ORDER BY sentAt DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, senderNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				vlist.addElement(extractDTO(rs));
			}
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt, rs); }
		return vlist;
	}

	// 5. 내가 받은 선물 보기
	// 
		public Vector<GiftDTO> getReceivedGifts(int receiverNo) {
			Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
			Vector<GiftDTO> vlist = new Vector<GiftDTO>();
			try {
				con = pool.getConnection();
				
				// ORDER BY 조건에 CASE 문을 추가.
				// 1. giftState가 0(대기)이면 우선순위 0을 주고, 아니면 1을 줍니다. (대기 중인 선물이 위로 옴)
				// 2. 그 그룹 안에서 sentAt(보낸 시간)을 역순으로 최신순 정렬합니다.
				String sql = "SELECT * FROM gift WHERE receiverNo = ? "
						   + "ORDER BY CASE WHEN giftState = 0 THEN 0 ELSE 1 END ASC, sentAt DESC";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, receiverNo);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					vlist.addElement(extractDTO(rs));
				}
			} catch (Exception e) { e.printStackTrace(); } 
			finally { pool.freeConnection(con, pstmt, rs); }
			return vlist;
		}

	// 6. 선물 생성 (DB의 Default 값 활용, INSERT INTO 구문 사용)
	public boolean insertGift(GiftDTO dto) {
		Connection con = null; PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			// 상태(0)와 보낸시간(NOW())은 DB 기본값 및 함수 활용
			String sql = "INSERT INTO gift (orderNo, senderNo, receiverNo, giftMsg) VALUES (?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getOrderNo());
			pstmt.setInt(2, dto.getSenderNo());
			pstmt.setInt(3, dto.getReceiverNo());
			pstmt.setString(4, dto.getGiftMsg());
			if (pstmt.executeUpdate() > 0) flag = true;
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt); }
		return flag;
	}

	// 7. 전체 수정 (관리자용)
	public boolean updateGift(GiftDTO dto, int giftNo) {
		Connection con = null; PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "UPDATE gift SET orderNo = ?, senderNo = ?, receiverNo = ?, giftMsg = ?, giftState = ?, sentAt = ?, respondedAt = ? WHERE giftNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getOrderNo());
			pstmt.setInt(2, dto.getSenderNo());
			pstmt.setInt(3, dto.getReceiverNo());
			pstmt.setString(4, dto.getGiftMsg());
			pstmt.setInt(5, dto.getGiftState());
			pstmt.setString(6, dto.getSentAt());
			pstmt.setString(7, dto.getRespondedAt());
			pstmt.setInt(8, giftNo);
			if (pstmt.executeUpdate() > 0) flag = true;
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt); }
		return flag;
	}

	// 8. 거절이나 수락으로 인한 선물 상태 업데이트 (응답시간 NOW() 처리)
	public boolean updateGiftState(int giftNo, int newState, int addressNo) {
		Connection con = null; PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			// 상태를 변경하면서 응답 시간(respondedAt)도 현재 시간으로 자동 갱신
			String sql = "UPDATE gift SET giftState = ?, addressNo = ?, respondedAt = NOW() WHERE giftNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, newState);
	        pstmt.setInt(2, addressNo);
	        pstmt.setInt(3, giftNo);
			if (pstmt.executeUpdate() > 0) flag = true;
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt); }
		return flag;
	}
	
	// 8-1. 거절이나 만료 시 호출할 기존 메서드 (addressNo가 필요 없는 경우를 위해 남겨둠)
	public boolean updateGiftState(int giftNo, int newState) {
	    Connection con = null; PreparedStatement pstmt = null;
	    boolean flag = false;
	    try {
	        con = pool.getConnection();
	        String sql = "UPDATE gift SET giftState = ?, respondedAt = NOW() WHERE giftNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, newState);
	        pstmt.setInt(2, giftNo);
	        if (pstmt.executeUpdate() > 0) flag = true;
	    } catch (Exception e) { e.printStackTrace(); } 
	    finally { pool.freeConnection(con, pstmt); }
	    return flag;
	}

	// 9. 선물 삭제
	public boolean deleteGift(int giftNo) {
		Connection con = null; PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "DELETE FROM gift WHERE giftNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, giftNo);
			if (pstmt.executeUpdate() > 0) flag = true;
		} catch (Exception e) { e.printStackTrace(); } 
		finally { pool.freeConnection(con, pstmt); }
		return flag;
	}
	
	// 나 ↔ 상대방 사이의 선물 내역 (시간순)
	public Vector<GiftDTO> getGiftsBetween(int myNo, int otherNo) {
	    Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
	    Vector<GiftDTO> vlist = new Vector<>();
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT * FROM gift "
	                   + "WHERE (senderNo = ? AND receiverNo = ?) "
	                   + "   OR (senderNo = ? AND receiverNo = ?) "
	                   + "ORDER BY sentAt ASC";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, myNo);  pstmt.setInt(2, otherNo);
	        pstmt.setInt(3, otherNo); pstmt.setInt(4, myNo);
	        rs = pstmt.executeQuery();
	        while (rs.next()) vlist.add(extractDTO(rs));
	    } catch (Exception e) { e.printStackTrace(); }
	    finally { pool.freeConnection(con, pstmt, rs); }
	    return vlist;
	}

	// [유틸리티] 중복되는 코드를 줄이기 위해 ResultSet에서 DTO를 뽑아내는 메서드
	private GiftDTO extractDTO(ResultSet rs) throws Exception {
		GiftDTO dto = new GiftDTO();
		dto.setGiftNo(rs.getInt("giftNo"));
		dto.setOrderNo(rs.getInt("orderNo"));
		dto.setSenderNo(rs.getInt("senderNo"));
		dto.setReceiverNo(rs.getInt("receiverNo"));
		dto.setGiftMsg(rs.getString("giftMsg"));
		dto.setGiftState(rs.getInt("giftState"));
		String sentAt = rs.getString("sentAt");
		if (sentAt != null && sentAt.length() >= 19) {
			dto.setSentAt(sentAt.substring(0, 19)); // "2026-04-09 14:30:00" 까지만 자름
		} else {
			dto.setSentAt(sentAt);
		}
		
		String respondedAt = rs.getString("respondedAt");
		if (respondedAt != null && respondedAt.length() >= 19) {
			dto.setRespondedAt(respondedAt.substring(0, 19));
		} else {
			dto.setRespondedAt(respondedAt);
		}
		return dto;
	}
}