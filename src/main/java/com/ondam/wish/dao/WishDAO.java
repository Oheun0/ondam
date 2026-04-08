package com.ondam.wish.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.wish.dto.WishDTO;

public class WishDAO {

	private DBConnectionMgr pool;

	public WishDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// 1. 특정 유저가 특정 상품을 이미 찜했는지 확인 (옵션 제거)
	public WishDTO checkWish(int userNo, int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WishDTO dto = null;
		try {
			con = pool.getConnection();
			String sql = "SELECT * FROM wish WHERE userNo=? AND productNo=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, productNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new WishDTO();
				dto.setWishNo(rs.getInt("wishNo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return dto;
	}

	// 2. 내 찜 리스트 보기 (옵션 제거)
	public Vector<WishDTO> getMyWish(int userNo, String sort, String part) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Vector<WishDTO> vlist = new Vector<>();
	    try {
	        con = pool.getConnection();

	        String orderBy;
	        if ("인기순".equals(sort)) {
	            orderBy = "ORDER BY (p.wishCount + p.saleCount) DESC";
	        } else if ("최신순".equals(sort)) {
	            orderBy = "ORDER BY p.productDate DESC";
	        } else if ("가격 낮은순".equals(sort)) {
	            orderBy = "ORDER BY p.productPrice ASC";
	        } else if ("가격 높은순".equals(sort)) {
	            orderBy = "ORDER BY p.productPrice DESC";
	        } else {
	            orderBy = "ORDER BY w.wishDate DESC"; // 담은순 기본값
	        }

	        StringBuilder sql = new StringBuilder("""
	                SELECT w.wishNo, w.userNo, w.productNo, w.wishDate,
	                       p.productName, p.productPrice, p.productOriginPrice,
	                       p.productBrand, p.saleCount, p.wishCount,
	                       parent.categoryName,
	                       (SELECT imgFile FROM productimage
	                        WHERE productNo = p.productNo
	                        ORDER BY imgOrder ASC LIMIT 1) AS imgFile
	                FROM wish w
	                JOIN product p ON w.productNo = p.productNo
	                JOIN category cat ON p.categoryNo = cat.categoryNo
	                LEFT JOIN category parent ON cat.upCategoryNo = parent.categoryNo
	                WHERE w.userNo = ?
	                """);
	        if (part != null && !part.isEmpty()) {
	            sql.append("AND parent.categoryName = ? ");
	        }

	        sql.append(orderBy);

	        pstmt = con.prepareStatement(sql.toString());
	        pstmt.setInt(1, userNo);
	        if (part != null && !part.isEmpty()) {
	            pstmt.setString(2, part);
	        }
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            WishDTO dto = new WishDTO();
	            dto.setWishNo(rs.getInt("wishNo"));
	            dto.setUserNo(rs.getInt("userNo"));
	            dto.setProductNo(rs.getInt("productNo"));
	            dto.setWishDate(rs.getString("wishDate"));
	            dto.setProductName(rs.getString("productName"));
	            dto.setProductPrice(rs.getInt("productPrice"));
	            dto.setProductOriginPrice(rs.getInt("productOriginPrice"));
	            dto.setProductBrand(rs.getString("productBrand"));
	            dto.setSaleCount(rs.getInt("saleCount"));
	            dto.setWishCount(rs.getInt("wishCount"));
	            dto.setProductImg(rs.getString("imgFile"));
	            dto.setCategoryName(rs.getString("categoryName"));
	            vlist.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return vlist;
	}

	// 3. 찜 등록 (옵션 제거)
	public boolean insertWish(WishDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT INTO wish (userNo, productNo) VALUES (?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserNo());
			pstmt.setInt(2, dto.getProductNo());
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	// 4. 찜 삭제 (옵션 제거)
	public boolean deleteWishByInfo(int userNo, int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "DELETE FROM wish WHERE userNo = ? AND productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, productNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	// wishSet 빌드 전용 — JOIN 없이 단순 조회
	public Set<Integer> getWishedProductNos(int userNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Set<Integer> set = new HashSet<>();
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT productNo FROM wish WHERE userNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, userNo);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            set.add(rs.getInt("productNo"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return set;
	}
}