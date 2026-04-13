package com.ondam.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.product.dto.ProductSeasonDTO;

public class ProductSeasonDAO {

	private DBConnectionMgr pool;

	public ProductSeasonDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<ProductSeasonDTO> getProductSeason() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ProductSeasonDTO> vlist = new Vector<ProductSeasonDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM productSeason";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductSeasonDTO dto = new ProductSeasonDTO();
				dto.setProductSeasonNo(rs.getInt("productSeasonNo"));
				dto.setProductNo(rs.getInt("productNo"));
				dto.setSeason(rs.getString("season"));
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
	public boolean insertProductSeason(ProductSeasonDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT productSeason (productNo, season) VALUES (?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getProductNo());
			pstmt.setString(2, dto.getSeason());
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
	public boolean updateProductSeason(ProductSeasonDTO dto, int productSeasonNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE productSeason SET productNo = ?, season = ? WHERE productSeasonNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getProductNo());
			pstmt.setString(2, dto.getSeason());
			pstmt.setInt(3, productSeasonNo);
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
	public boolean deleteProductSeason(int productSeasonNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM productSeason WHERE productSeasonNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productSeasonNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	public boolean deleteByProductNo(int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = pool.getConnection();
			String sql = "DELETE FROM productSeason WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productNo);
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return false;
	}
	
	public Vector<String> getSeasonsByProductNo(int productNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vector<String> seasons = new Vector<>(); // [변경] Vector 사용
        
        try {
            con = pool.getConnection();
            String sql = "SELECT season FROM productseason WHERE productNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, productNo);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                seasons.add(rs.getString("season"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return seasons;
    }
	// 참고용: ProductSeasonDAO.java 내부에 들어갈 메서드 예시
	public String getProductSeasons(int productNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    StringBuilder sb = new StringBuilder();
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT season FROM productseason WHERE productNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, productNo);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            if (sb.length() > 0) sb.append(",");
	            sb.append(rs.getString("season")); // 예: "봄,가을"
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return sb.toString();
	}
}