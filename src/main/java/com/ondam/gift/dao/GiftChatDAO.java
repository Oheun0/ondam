package com.ondam.gift.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.gift.dto.GiftChatDTO;

public class GiftChatDAO {

    private DBConnectionMgr pool;

    public GiftChatDAO() {
        pool = DBConnectionMgr.getInstance();
    }

    // [유틸리티] ResultSet → DTO 변환
    private GiftChatDTO extractDTO(ResultSet rs) throws Exception {
        GiftChatDTO dto = new GiftChatDTO();
        dto.setChatNo(rs.getInt("chatNo"));
        dto.setGiftNo(rs.getInt("giftNo"));
        dto.setSenderNo(rs.getInt("senderNo"));
        dto.setReceiverNo(rs.getInt("receiverNo"));
        dto.setChatType(rs.getInt("chatType"));
        dto.setCardImg(rs.getString("cardImg"));

        String sentAt = rs.getString("sentAt");
        if (sentAt != null && sentAt.length() >= 19) {
            dto.setSentAt(sentAt.substring(0, 19));
        } else {
            dto.setSentAt(sentAt);
        }
        return dto;
    }

    // 1. 선물카드 또는 감사카드 INSERT
    public boolean insertGiftChat(GiftChatDTO dto) {
        Connection con = null; PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            String sql = "INSERT INTO giftChat (giftNo, senderNo, receiverNo, chatType, cardImg) "
                       + "VALUES (?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, dto.getGiftNo());
            pstmt.setInt(2, dto.getSenderNo());
            pstmt.setInt(3, dto.getReceiverNo());
            pstmt.setInt(4, dto.getChatType());
            pstmt.setString(5, dto.getCardImg());
            if (pstmt.executeUpdate() > 0) flag = true;
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt); }
        return flag;
    }

    // 2. 특정 giftNo의 채팅 목록 조회 (sentAt ASC → 시간순)
    public Vector<GiftChatDTO> getChatListByGiftNo(int giftNo) {
        Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
        Vector<GiftChatDTO> vlist = new Vector<>();
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM giftChat WHERE giftNo = ? ORDER BY sentAt ASC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, giftNo);
            rs = pstmt.executeQuery();
            while (rs.next()) vlist.add(extractDTO(rs));
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt, rs); }
        return vlist;
    }

    // 3. A↔B 사이의 전체 채팅 목록 조회 (sentAt ASC → 시간순)
    //    gift-chat.jsp 진입 시 대화방 전체 내역 로드용
    public Vector<GiftChatDTO> getChatListBetween(int myNo, int otherNo) {
        Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
        Vector<GiftChatDTO> vlist = new Vector<>();
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM giftChat "
                       + "WHERE (senderNo = ? AND receiverNo = ?) "
                       + "   OR (senderNo = ? AND receiverNo = ?) "
                       + "ORDER BY sentAt ASC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, myNo);   pstmt.setInt(2, otherNo);
            pstmt.setInt(3, otherNo); pstmt.setInt(4, myNo);
            rs = pstmt.executeQuery();
            while (rs.next()) vlist.add(extractDTO(rs));
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt, rs); }
        return vlist;
    }

    // 4. 특정 giftNo에 감사카드가 이미 존재하는지 확인 (중복 방지용)
    //    true면 이미 감사카드 보낸 것 → "고마움 표시하기" 버튼 비활성화
    public boolean existsThanksCard(int giftNo) {
        Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
        boolean exists = false;
        try {
            con = pool.getConnection();
            String sql = "SELECT 1 FROM giftChat WHERE giftNo = ? AND chatType = 1 LIMIT 1";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, giftNo);
            rs = pstmt.executeQuery();
            if (rs.next()) exists = true;
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt, rs); }
        return exists;
    }

    // 5. 단건 조회 (chatNo 기준)
    public GiftChatDTO getChatByChatNo(int chatNo) {
        Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
        GiftChatDTO dto = null;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM giftChat WHERE chatNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, chatNo);
            rs = pstmt.executeQuery();
            if (rs.next()) dto = extractDTO(rs);
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt, rs); }
        return dto;
    }

    // 6. 삭제 (chatNo 기준, 관리자용)
    public boolean deleteGiftChat(int chatNo) {
        Connection con = null; PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            String sql = "DELETE FROM giftChat WHERE chatNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, chatNo);
            if (pstmt.executeUpdate() > 0) flag = true;
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt); }
        return flag;
    }
}