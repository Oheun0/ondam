package com.ondam.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.product.dto.ProductDTO;

public class ProductDAO {

	private DBConnectionMgr pool;

	public ProductDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<ProductDTO> getProduct() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ProductDTO> vlist = new Vector<ProductDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM product";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setProductNo(rs.getInt("productNo"));
				dto.setVendorNo(rs.getInt("vendorNo"));
				dto.setCategoryNo(rs.getInt("categoryNo"));
				dto.setProductName(rs.getString("productName"));
				dto.setProductBrand(rs.getString("productBrand"));
				dto.setProductEx(rs.getString("productEx"));
				dto.setProductPrice(rs.getInt("productPrice"));
				dto.setProductOriginPrice(rs.getInt("productOriginPrice"));
				dto.setProductMaterial(rs.getString("productMaterial"));
				dto.setProductPattern(rs.getString("productPattern"));
				dto.setProductFit(rs.getString("productFit"));
				dto.setProductThickness(rs.getString("productThickness"));
				dto.setProductSeason(rs.getString("productSeason"));
				dto.setProductState(rs.getInt("productState"));
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
	public boolean insertProduct(ProductDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT Product (vendorNo, categoryNo, productName, productBrand, productEx, productPrice, productOriginPrice, productMaterial, productPattern, productFit, productThickness, productSeason, productState) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setInt(2, dto.getCategoryNo());
			pstmt.setString(3, dto.getProductName());
			pstmt.setString(4, dto.getProductBrand());
			pstmt.setString(5, dto.getProductEx());
			pstmt.setInt(6, dto.getProductPrice());
			pstmt.setInt(7, dto.getProductOriginPrice());
			pstmt.setString(8, dto.getProductMaterial());
			pstmt.setString(9, dto.getProductPattern());
			pstmt.setString(10, dto.getProductFit());
			pstmt.setString(11, dto.getProductThickness());
			pstmt.setString(12, dto.getProductSeason());
			pstmt.setInt(13, dto.getProductState());
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
	public boolean updateProduct(ProductDTO dto, int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Product SET vendorNo = ?, categoryNo = ?, productName = ?, productBrand = ?, productEx = ?, productPrice = ?, productOriginPrice = ?, productMaterial = ?, productPattern = ?, productFit = ?, productThickness = ?, productSeason = ?, productState = ? WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setInt(2, dto.getCategoryNo());
			pstmt.setString(3, dto.getProductName());
			pstmt.setString(4, dto.getProductBrand());
			pstmt.setString(5, dto.getProductEx());
			pstmt.setInt(6, dto.getProductPrice());
			pstmt.setInt(7, dto.getProductOriginPrice());
			pstmt.setString(8, dto.getProductMaterial());
			pstmt.setString(9, dto.getProductPattern());
			pstmt.setString(10, dto.getProductFit());
			pstmt.setString(11, dto.getProductThickness());
			pstmt.setString(12, dto.getProductSeason());
			pstmt.setInt(13, dto.getProductState());
			pstmt.setInt(14, productNo);
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
	public boolean deleteProduct(int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM Product WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	// 1. 특정 상품 1개 조회 (상품명 가져오기용)
	public ProductDTO getProductById(int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductDTO dto = null;
		try {
			con = pool.getConnection();
			String sql = "SELECT * FROM product WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new ProductDTO();
				dto.setProductNo(rs.getInt("productNo"));
	            dto.setVendorNo(rs.getInt("vendorNo"));
	            dto.setCategoryNo(rs.getInt("categoryNo"));
	            dto.setProductName(rs.getString("productName"));
	            dto.setProductBrand(rs.getString("productBrand"));
	            dto.setProductEx(rs.getString("productEx"));
	            dto.setProductPrice(rs.getInt("productPrice"));
	            dto.setProductOriginPrice(rs.getInt("productOriginPrice"));
	            dto.setProductMaterial(rs.getString("productMaterial"));
	            dto.setProductPattern(rs.getString("productPattern"));
	            dto.setProductFit(rs.getString("productFit"));
	            dto.setProductThickness(rs.getString("productThickness"));
	            dto.setProductSeason(rs.getString("productSeason"));
	            dto.setProductState(rs.getInt("productState"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return dto;
	}

	// 2. 특정 상품의 이미지 파일 목록 조회 (productimage 테이블 조인/조회)
	public Vector<String> getProductImages(int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<String> imgList = new Vector<String>();
		try {
			con = pool.getConnection();
			// productimage 테이블에서 해당 상품의 이미지 파일명들만 가져옴 (imgOrder 순)
			String sql = "SELECT imgFile FROM productimage WHERE productNo = ? ORDER BY imgOrder ASC";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				imgList.add(rs.getString("imgFile"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return imgList;
	}	
	
	// 상품 번호를 통해 상품명 조회
	public String getProductName(int productNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String productName = null;
	    try {
	        con = pool.getConnection();
	        // DB 설계서의 컬럼명 'product_name' 및 'product_no' 사용
	        String sql = "SELECT productName FROM product WHERE productNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, productNo);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            productName = rs.getString("productName");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return productName;
	}
	
	public int getProductPrice(int productNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int productPrice = 0;
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT productPrice FROM product WHERE productNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, productNo);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            productPrice = rs.getInt("productPrice");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return productPrice;
	}
	
	// 상품 옵션명 조회 (장바구니 표시용 추가)
	// 장바구니에는 옵션 번호만 있으므로, 이름을 보여주려면 이 메서드가 반드시 필요합니다.
	public String getOptionName(int productOptionNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String optionName = "";
		try {
			con = pool.getConnection();
			String sql = "SELECT optionName FROM productOption WHERE productOptionNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productOptionNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				optionName = rs.getString("optionName");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return optionName;
	}
}

