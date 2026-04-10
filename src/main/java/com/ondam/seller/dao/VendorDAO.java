package com.ondam.seller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
				VendorDTO dto = new VendorDTO();
				dto.setVendorNo(rs.getInt("vendorNo"));
				dto.setVendorName(rs.getString("vendorName"));
				dto.setBizType(rs.getInt("bizType"));
				dto.setBizRegNo(rs.getString("bizRegNo"));
				dto.setRepName(rs.getString("repName"));
				dto.setBizAddr(rs.getString("bizAddr"));
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
	        pstmt.setString(5, dto.getBizTel());
	        pstmt.setString(6, dto.getContactEmail());
	        pstmt.setString(7, dto.getBizAddr()); // 출고지 주소 (가공해서 저장)
	        pstmt.setString(8, dto.getBizReturnAddr()); // 반품지 주소 (가공해서 저장)
	        
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
}

