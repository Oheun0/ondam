package com.ondam.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.product.dto.ProductOptionDTO;

public class ProductOptionDAO {

	private DBConnectionMgr pool;

	public ProductOptionDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<ProductOptionDTO> getProductOption() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ProductOptionDTO> vlist = new Vector<ProductOptionDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM productOption";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductOptionDTO dto = new ProductOptionDTO();
				dto.setProductOptionNo(rs.getInt("productOptionNo"));
				dto.setProductNo(rs.getInt("productNo"));
				dto.setOptionSize(rs.getString("optionSize"));
				dto.setOptionColor(rs.getString("optionColor"));
				dto.setOptionAddPrice(rs.getInt("optionAddPrice"));
				dto.setOptionStock(rs.getInt("optionStock"));
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
	public boolean insertProductOption(ProductOptionDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT ProductOption (productNo, optionSize, optionColor, optionAddPrice, optionStock) VALUES (?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getProductNo());
			pstmt.setString(2, dto.getOptionSize());
			pstmt.setString(3, dto.getOptionColor());
			pstmt.setInt(4, dto.getOptionAddPrice());
			pstmt.setInt(5, dto.getOptionStock());
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
	public boolean updateProductOption(ProductOptionDTO dto, int productOptionNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE ProductOption SET productNo = ?, optionSize = ?, optionColor = ?, optionAddPrice = ?, optionStock = ? WHERE productOptionNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getProductNo());
			pstmt.setString(2, dto.getOptionSize());
			pstmt.setString(3, dto.getOptionColor());
			pstmt.setInt(4, dto.getOptionAddPrice());
			pstmt.setInt(5, dto.getOptionStock());
			pstmt.setInt(6, productOptionNo);
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
	public boolean deleteProductOption(int productOptionNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM ProductOption WHERE productOptionNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productOptionNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	// 특정 상품의 옵션 목록
	public Vector<ProductOptionDTO> getByProductNo(int productNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Vector<ProductOptionDTO> vlist = new Vector<>();
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT * FROM productoption WHERE productNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, productNo);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            ProductOptionDTO dto = new ProductOptionDTO();
	            dto.setProductOptionNo(rs.getInt("productOptionNo"));
	            dto.setProductNo(rs.getInt("productNo"));
	            dto.setOptionSize(rs.getString("optionSize"));
	            dto.setOptionColor(rs.getString("optionColor"));
	            dto.setOptionAddPrice(rs.getInt("optionAddPrice"));
	            dto.setOptionStock(rs.getInt("optionStock"));
	            vlist.addElement(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return vlist;
	}
	// [추가] 옵션 번호로 단일 옵션 정보 조회
    public ProductOptionDTO getProductOptionByNo(int productOptionNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ProductOptionDTO dto = null;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM ProductOption WHERE productOptionNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, productOptionNo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new ProductOptionDTO();
                dto.setProductOptionNo(rs.getInt("productOptionNo"));
                dto.setProductNo(rs.getInt("productNo"));
                dto.setOptionSize(rs.getString("optionSize"));
                dto.setOptionColor(rs.getString("optionColor"));
                dto.setOptionAddPrice(rs.getInt("optionAddPrice"));
                dto.setOptionStock(rs.getInt("optionStock"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return dto;
    }
    public List<ProductOptionDTO> getProductOptionList(int productNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        List<ProductOptionDTO> list = new ArrayList<>();

        try {
            con = pool.getConnection();
            // 재고(optionStock)가 0보다 큰 것만 가져와야 AI가 품절된 상품을 추천하지 않습니다.
            sql = "SELECT * FROM productoption WHERE productNo = ? AND optionStock > 0 ORDER BY optionSize ASC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, productNo);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductOptionDTO dto = new ProductOptionDTO();
                dto.setProductOptionNo(rs.getInt("productOptionNo"));
                dto.setProductNo(rs.getInt("productNo"));
                dto.setOptionSize(rs.getString("optionSize"));
                dto.setOptionColor(rs.getString("optionColor"));
                dto.setOptionAddPrice(rs.getInt("optionAddPrice"));
                dto.setOptionStock(rs.getInt("optionStock"));
                
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return list;
    }
}