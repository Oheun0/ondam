package com.ondam.notification.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.notification.dto.NotificationDTO;

public class NotificationDAO {

	private DBConnectionMgr pool;

	public NotificationDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<NotificationDTO> getNotification(int userNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<NotificationDTO> vlist = new Vector<NotificationDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM notification WHERE userNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				NotificationDTO dto = new NotificationDTO();
				dto.setNotificationNo(rs.getInt("notificationNo"));
				dto.setUserNo(rs.getInt("userNo"));
				dto.setNotificationType(rs.getInt("notificationType"));
				dto.setNotificationContent(rs.getString("notificationContent"));
				dto.setIsRead(rs.getInt("isRead"));
				dto.setRefNo(rs.getInt("refNo"));
				dto.setCreatedAt(rs.getString("createdAt"));
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
	public boolean insertNotification(NotificationDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Notification (userNo, notificationType, notificationContent, isRead, refNo, createdAt) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserNo());
			pstmt.setInt(2, dto.getNotificationType());
			pstmt.setString(3, dto.getNotificationContent());
			pstmt.setInt(4, dto.getIsRead());
			pstmt.setInt(5, dto.getRefNo());
			pstmt.setString(6, dto.getCreatedAt());
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
	public boolean updateNotification(NotificationDTO dto, int notificationNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Notification SET userNo = ?, notificationType = ?, notificationContent = ?, isRead = ?, refNo = ?, createdAt = ? WHERE notificationNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserNo());
			pstmt.setInt(2, dto.getNotificationType());
			pstmt.setString(3, dto.getNotificationContent());
			pstmt.setInt(4, dto.getIsRead());
			pstmt.setInt(5, dto.getRefNo());
			pstmt.setString(6, dto.getCreatedAt());
			pstmt.setInt(7, notificationNo);
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
	public boolean deleteNotification(int notificationNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Notification WHERE notificationNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notificationNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	// 전체 읽음 처리
	public boolean markAllRead(int userNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    boolean flag = false;
	    try {
	        con = pool.getConnection();
	        String sql = "UPDATE notification SET isRead = 1 WHERE userNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, userNo);
	        if (pstmt.executeUpdate() > 0) flag = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt);
	    }
	    return flag;
	}

	// 전체 삭제
	public boolean deleteAllNotification(int userNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    boolean flag = false;
	    try {
	        con = pool.getConnection();
	        String sql = "DELETE FROM notification WHERE userNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, userNo);
	        if (pstmt.executeUpdate() > 0) flag = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt);
	    }
	    return flag;
	}
	
	// 1건 읽음 (클릭으로)
	public boolean markOneRead(int notificationNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    boolean flag = false;
	    try {
	        con = pool.getConnection();
	        String sql = "UPDATE notification SET isRead = 1 WHERE notificationNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, notificationNo);
	        if (pstmt.executeUpdate() > 0) flag = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt);
	    }
	    return flag;
	}
}