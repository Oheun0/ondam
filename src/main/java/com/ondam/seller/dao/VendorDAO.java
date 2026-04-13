package com.ondam.seller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.seller.dto.VendorDTO;

public class VendorDAO {

	private DBConnectionMgr pool;

	public VendorDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<VendorDTO> getVendor() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<VendorDTO> vlist = new Vector<VendorDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM vendor";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				vlist.addElement(mapVendorRow(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}

	/** 업체 번호로 vendor 한 건 (설정 화면 등) */
	public VendorDTO getVendorByVendorNo(int vendorNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = pool.getConnection();
			String sql = "SELECT * FROM vendor WHERE vendorNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, vendorNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return mapVendorRow(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return null;
	}

	private static Integer getIntegerOrNull(ResultSet rs, String column) throws Exception {
		Object o = rs.getObject(column);
		if (o == null) {
			return null;
		}
		if (o instanceof Number) {
			return Integer.valueOf(((Number) o).intValue());
		}
		return null;
	}

	private static VendorDTO mapVendorRow(ResultSet rs) throws Exception {
		VendorDTO dto = new VendorDTO();
		dto.setVendorNo(rs.getInt("vendorNo"));
		dto.setVendorName(rs.getString("vendorName"));
		dto.setBizType(rs.getInt("bizType"));
		dto.setBizRegNo(rs.getString("bizRegNo"));
		dto.setRepName(rs.getString("repName"));
		dto.setBizAddr(rs.getString("bizAddr"));
		dto.setBizReturnAddr(rs.getString("bizReturnAddr"));
		dto.setBizTel(rs.getString("bizTel"));
		dto.setContactEmail(rs.getString("contactEmail"));
		dto.setBizRegImg(rs.getString("bizRegImg"));
		dto.setMailOrderImg(rs.getString("mailOrderImg"));
		dto.setSealCertImg(rs.getString("sealCertImg"));
		dto.setCorpRegImg(rs.getString("corpRegImg"));
		dto.setLogoImg(rs.getString("logoImg"));
		dto.setBizDescription(rs.getString("bizDescription"));
		dto.setReviewStatus(rs.getInt("reviewStatus"));
		dto.setRejectReason(rs.getString("rejectReason"));
		dto.setApplyDate(rs.getString("applyDate"));
		dto.setReturnExchangeGuide(rs.getString("return_exchange_guide"));
		dto.setShipFee(getIntegerOrNull(rs, "ship_fee"));
		dto.setFreeShipMin(getIntegerOrNull(rs, "free_ship_min"));
		dto.setPrepDays(rs.getString("prep_days"));
		dto.setDefaultCourier(rs.getString("default_courier"));
		dto.setIslandExtra(getIntegerOrNull(rs, "island_extra"));
		dto.setShipNotice(rs.getString("ship_notice"));
		dto.setDelayNotice(rs.getString("delay_notice"));
		dto.setGiftNotice(rs.getString("gift_notice"));
		dto.setExchangeNotice(rs.getString("exchange_notice"));
		return dto;
	}

	/**
	 * 판매자 설정 화면에서 수정 가능한 컬럼만 갱신 (인증·이미지·심사 필드는 유지).
	 */
	public boolean updateSellerSettings(VendorDTO dto, int vendorNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = pool.getConnection();
			String sql = "UPDATE vendor SET vendorName = ?, bizRegNo = ?, bizTel = ?, contactEmail = ?, "
					+ "bizAddr = ?, bizReturnAddr = ?, bizDescription = ?, return_exchange_guide = ?, "
					+ "ship_fee = ?, free_ship_min = ?, prep_days = ?, default_courier = ?, island_extra = ?, "
					+ "ship_notice = ?, delay_notice = ?, gift_notice = ?, exchange_notice = ? "
					+ "WHERE vendorNo = ?";
			pstmt = con.prepareStatement(sql);
			int i = 1;
			pstmt.setString(i++, dto.getVendorName());
			pstmt.setString(i++, dto.getBizRegNo());
			pstmt.setString(i++, dto.getBizTel());
			pstmt.setString(i++, dto.getContactEmail());
			pstmt.setString(i++, dto.getBizAddr());
			pstmt.setString(i++, dto.getBizReturnAddr());
			pstmt.setString(i++, dto.getBizDescription());
			pstmt.setString(i++, dto.getReturnExchangeGuide());
			setNullableInt(pstmt, i++, dto.getShipFee());
			setNullableInt(pstmt, i++, dto.getFreeShipMin());
			pstmt.setString(i++, dto.getPrepDays());
			pstmt.setString(i++, dto.getDefaultCourier());
			setNullableInt(pstmt, i++, dto.getIslandExtra());
			pstmt.setString(i++, dto.getShipNotice());
			pstmt.setString(i++, dto.getDelayNotice());
			pstmt.setString(i++, dto.getGiftNotice());
			pstmt.setString(i++, dto.getExchangeNotice());
			pstmt.setInt(i++, vendorNo);
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, null);
		}
		return false;
	}

	private static void setNullableInt(PreparedStatement pstmt, int index, Integer value) throws Exception {
		if (value == null) {
			pstmt.setNull(index, Types.INTEGER);
		} else {
			pstmt.setInt(index, value.intValue());
		}
	}

	// Insert
	public boolean insertVendor(VendorDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Vendor (vendorName, bizType, bizRegNo, repName, bizAddr, bizTel, contactEmail, bizRegImg, mailOrderImg, sealCertImg, corpRegImg, logoImg, bizDescription, reviewStatus, rejectReason, applyDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getVendorName());
			pstmt.setInt(2, dto.getBizType());
			pstmt.setString(3, dto.getBizRegNo());
			pstmt.setString(4, dto.getRepName());
			pstmt.setString(5, dto.getBizAddr());
			pstmt.setString(6, dto.getBizTel());
			pstmt.setString(7, dto.getContactEmail());
			pstmt.setString(8, dto.getBizRegImg());
			pstmt.setString(9, dto.getMailOrderImg());
			pstmt.setString(10, dto.getSealCertImg());
			pstmt.setString(11, dto.getCorpRegImg());
			pstmt.setString(12, dto.getLogoImg());
			pstmt.setString(13, dto.getBizDescription());
			pstmt.setInt(14, dto.getReviewStatus());
			pstmt.setString(15, dto.getRejectReason());
			pstmt.setString(16, dto.getApplyDate());
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
	public boolean updateVendor(VendorDTO dto, int vendorNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Vendor SET vendorName = ?, bizType = ?, bizRegNo = ?, repName = ?, bizAddr = ?, bizTel = ?, contactEmail = ?, bizRegImg = ?, mailOrderImg = ?, sealCertImg = ?, corpRegImg = ?, logoImg = ?, bizDescription = ?, reviewStatus = ?, rejectReason = ?, applyDate = ? WHERE vendorNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getVendorName());
			pstmt.setInt(2, dto.getBizType());
			pstmt.setString(3, dto.getBizRegNo());
			pstmt.setString(4, dto.getRepName());
			pstmt.setString(5, dto.getBizAddr());
			pstmt.setString(6, dto.getBizTel());
			pstmt.setString(7, dto.getContactEmail());
			pstmt.setString(8, dto.getBizRegImg());
			pstmt.setString(9, dto.getMailOrderImg());
			pstmt.setString(10, dto.getSealCertImg());
			pstmt.setString(11, dto.getCorpRegImg());
			pstmt.setString(12, dto.getLogoImg());
			pstmt.setString(13, dto.getBizDescription());
			pstmt.setInt(14, dto.getReviewStatus());
			pstmt.setString(15, dto.getRejectReason());
			pstmt.setString(16, dto.getApplyDate());
			pstmt.setInt(17, vendorNo);
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
	public boolean deleteVendor(int vendorNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Vendor WHERE vendorNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, vendorNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	// 업체 번호를 통해 업체명 조회
	public String getVendorName(int vendorNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String vendorName = null;
	    try {
	        con = pool.getConnection();
	        // DB 설계서의 컬럼명 'vendorName' 사용
	        String sql = "SELECT vendorName FROM vendor WHERE vendorNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, vendorNo);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            vendorName = rs.getString("vendorName");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return vendorName;
	}
	
	public int insertVendorAndGetNo(VendorDTO dto) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int vendorNo = 0;
	    try {
	        con = pool.getConnection();
	        // 가입 폼의 필드들에 맞춰 SQL 작성 (출고지/반품지 컬럼이 DB에 있다고 가정)
	        String sql = "INSERT INTO vendor (vendorName, bizType, bizRegNo, repName, bizAddr, bizReturnAddr, bizTel, contactEmail) " +
	                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        // RETURN_GENERATED_KEYS를 사용하여 생성된 PK값을 가져옵니다.
	        pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	        pstmt.setString(1, dto.getVendorName());
	        pstmt.setInt(2, dto.getBizType());
	        pstmt.setString(3, dto.getBizRegNo());
	        pstmt.setString(4, dto.getRepName());
	        pstmt.setString(5, dto.getBizAddr());
	        pstmt.setString(6, dto.getBizReturnAddr());
	        pstmt.setString(7, dto.getBizTel());
	        pstmt.setString(8, dto.getContactEmail());
	        
	        pstmt.executeUpdate();
	        rs = pstmt.getGeneratedKeys();
	        if (rs.next()) {
	            vendorNo = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return vendorNo;
	}
	// [인증용 - Vendor] 업체 번호(vendorNo)로 담당자 이메일 가져오기
	public String getEmailByVendorNo(int vendorNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String email = null;
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT contactEmail FROM vendor WHERE vendorNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, vendorNo);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            email = rs.getString("contactEmail");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return email;
	}

	/** 업체 번호로 스토어 로고(파일명) 조회 — DB에는 파일명만 저장 */
	public String getLogoImgByVendorNo(int vendorNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String logo = null;
		try {
			con = pool.getConnection();
			String sql = "SELECT logoImg FROM vendor WHERE vendorNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, vendorNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				logo = rs.getString("logoImg");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return logo;
	}

	public boolean updateLogoImg(int vendorNo, String logoFileName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = pool.getConnection();
			String sql = "UPDATE vendor SET logoImg = ? WHERE vendorNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, logoFileName);
			pstmt.setInt(2, vendorNo);
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, null);
		}
		return false;
	}
}

