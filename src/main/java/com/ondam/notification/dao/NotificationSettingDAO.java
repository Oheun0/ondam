package com.ondam.notification.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.notification.dto.NotificationSettingDTO;

public class NotificationSettingDAO {

	private DBConnectionMgr pool;

	public NotificationSettingDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// 관리자용 Select
	public Vector<NotificationSettingDTO> getNotificationSetting() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<NotificationSettingDTO> vlist = new Vector<NotificationSettingDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM notificationSetting";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				NotificationSettingDTO dto = new NotificationSettingDTO();
				dto.setNotificationSettingNo(rs.getInt("notificationSettingNo"));
				dto.setUserNo(rs.getInt("userNo"));
				dto.setNotificationType(rs.getInt("notificationType"));
				dto.setIsEnabled(rs.getInt("isEnabled"));
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
	public boolean insertNotificationSetting(NotificationSettingDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT NotificationSetting (userNo, notificationType, isEnabled) VALUES (?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserNo());
			pstmt.setInt(2, dto.getNotificationType());
			pstmt.setInt(3, dto.getIsEnabled());
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
	public boolean updateNotificationSetting(NotificationSettingDTO dto, int notificationSettingNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE NotificationSetting SET userNo = ?, notificationType = ?, isEnabled = ? WHERE notificationSettingNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserNo());
			pstmt.setInt(2, dto.getNotificationType());
			pstmt.setInt(3, dto.getIsEnabled());
			pstmt.setInt(4, notificationSettingNo);
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
	public boolean deleteNotificationSetting(int notificationSettingNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM NotificationSetting WHERE notificationSettingNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notificationSettingNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	// 마이페이지 select
		public Vector<NotificationSettingDTO> getSettingsByUserNo(int userNo) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Vector<NotificationSettingDTO> vlist = new Vector<>();
			try {
				con = pool.getConnection();
				String sql = "SELECT * FROM NotificationSetting WHERE userNo = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, userNo);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					NotificationSettingDTO dto = new NotificationSettingDTO();
					dto.setNotificationSettingNo(rs.getInt("notificationSettingNo"));
					dto.setUserNo(rs.getInt("userNo"));
					dto.setNotificationType(rs.getInt("notificationType"));
					dto.setIsEnabled(rs.getInt("isEnabled"));
					vlist.addElement(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return vlist;
		}

		// 알림 설정 토글 (있으면 Update, 없으면 Insert)
		public boolean toggleSetting(int userNo, int notificationType, int isEnabled) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean flag = false;
			try {
				con = pool.getConnection();
				String checkSql = "SELECT notificationSettingNo FROM NotificationSetting WHERE userNo = ? AND notificationType = ?";
				pstmt = con.prepareStatement(checkSql);
				pstmt.setInt(1, userNo);
				pstmt.setInt(2, notificationType);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					int settingNo = rs.getInt(1);
					pstmt.close();
					String updateSql = "UPDATE NotificationSetting SET isEnabled = ? WHERE notificationSettingNo = ?";
					pstmt = con.prepareStatement(updateSql);
					pstmt.setInt(1, isEnabled);
					pstmt.setInt(2, settingNo);
					if (pstmt.executeUpdate() > 0) flag = true;
				} else {
					pstmt.close();
					String insertSql = "INSERT INTO NotificationSetting (userNo, notificationType, isEnabled) VALUES (?, ?, ?)";
					pstmt = con.prepareStatement(insertSql);
					pstmt.setInt(1, userNo);
					pstmt.setInt(2, notificationType);
					pstmt.setInt(3, isEnabled);
					if (pstmt.executeUpdate() > 0) flag = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return flag;
		}
}

