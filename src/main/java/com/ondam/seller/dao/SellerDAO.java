package com.ondam.seller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.seller.dto.SellerDTO;

public class SellerDAO {

	private DBConnectionMgr pool;

	public SellerDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<SellerDTO> getSeller() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<SellerDTO> vlist = new Vector<SellerDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM seller";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SellerDTO dto = new SellerDTO();
				dto.setSellerAccountNo(rs.getInt("sellerAccountNo"));
				dto.setVendorNo(rs.getInt("vendorNo"));
				dto.setSellerId(rs.getString("sellerId"));
				dto.setSellerPwd(rs.getString("sellerPwd"));
				dto.setSellerName(rs.getString("sellerName"));
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
	public boolean insertSeller(SellerDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Seller (vendorNo, sellerId, sellerPwd, sellerName) VALUES (?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setString(2, dto.getSellerId());
			pstmt.setString(3, dto.getSellerPwd());
			pstmt.setString(4, dto.getSellerName());
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
	public boolean updateSeller(SellerDTO dto, int sellerAccountNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Seller SET vendorNo = ?, sellerId = ?, sellerPwd = ?, sellerName = ? WHERE sellerAccountNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setString(2, dto.getSellerId());
			pstmt.setString(3, dto.getSellerPwd());
			pstmt.setString(4, dto.getSellerName());
			pstmt.setInt(5, sellerAccountNo);
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
	public boolean deleteSeller(int sellerAccountNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Seller WHERE sellerAccountNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sellerAccountNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	public SellerDTO loginSeller(String sellerId, String sellerPwd) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    SellerDTO dto = null;
	    try {
	        con = pool.getConnection();
	        // 아이디와 비밀번호가 일치하는 판매자 조회
	        String sql = "SELECT * FROM seller WHERE sellerId = ? AND sellerPwd = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, sellerId);
	        pstmt.setString(2, sellerPwd);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            dto = new SellerDTO();
	            dto.setSellerAccountNo(rs.getInt("sellerAccountNo"));
	            dto.setVendorNo(rs.getInt("vendorNo"));
	            dto.setSellerId(rs.getString("sellerId"));
	            dto.setSellerPwd(rs.getString("sellerPwd"));
	            dto.setSellerName(rs.getString("sellerName"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return dto; // 로그인 실패 시 null 반환
	}
	// 담당자명과 이메일로 판매자 아이디 찾기 (Vendor 테이블과 Seller 테이블 JOIN)
	public String findSellerIdByVendorInfo(String repName, String email) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sellerId = null;
	    try {
	        con = pool.getConnection();
	        // vendor 테이블과 seller 테이블을 vendorNo 기준으로 조인
	        String sql = "SELECT s.sellerId FROM seller s " +
	                     "JOIN vendor v ON s.vendorNo = v.vendorNo " +
	                     "WHERE v.repName = ? AND v.contactEmail = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, repName);
	        pstmt.setString(2, email);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            sellerId = rs.getString("sellerId");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return sellerId; // 일치하는 정보가 없으면 null 반환
	}
	
	// 1. [인증용 - Seller] 아이디를 이용해 해당 판매자의 업체 번호(vendorNo) 가져오기
	public int getVendorNoBySellerId(String sellerId) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int vendorNo = 0;
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT vendorNo FROM seller WHERE sellerId = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, sellerId);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            vendorNo = rs.getInt("vendorNo");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return vendorNo; // 없으면 0 반환
	}

	// 2. [변경용] 새로운 비밀번호로 업데이트 (이건 기존과 동일하게 유지)
	public boolean updatePassword(String sellerId, String newPassword) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    boolean flag = false;
	    try {
	        con = pool.getConnection();
	        String sql = "UPDATE seller SET sellerPwd = ? WHERE sellerId = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, newPassword);
	        pstmt.setString(2, sellerId);
	        
	        if (pstmt.executeUpdate() > 0) {
	            flag = true;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt);
	    }
	    return flag;
	}
}

