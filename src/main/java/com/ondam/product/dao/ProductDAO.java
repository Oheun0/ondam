package com.ondam.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.product.dto.ProductDTO;
import com.ondam.product.dto.ProductOptionDTO;

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
				mapResultSetToDTO(rs, dto);
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
			// productEx 다음에 productGender 추가 (DB 순서에 맞춤)
			sql = "INSERT Product (vendorNo, categoryNo, situationNo, productName, productBrand, productEx, easyOneLine, easyFor, easyComfort, productGender, productPrice, productOriginPrice, productMaterial, productPattern, productFit, productThickness, productDate, productState) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setInt(2, dto.getCategoryNo());
			pstmt.setInt(3, dto.getSituationNo());
			pstmt.setString(4, dto.getProductName());
			pstmt.setString(5, dto.getProductBrand());
			pstmt.setString(6, dto.getProductEx());
			pstmt.setString(7, dto.getEasyOneLine());
			pstmt.setString(8, dto.getEasyFor());
			pstmt.setString(9, dto.getEasyComfort());
			pstmt.setInt(10, dto.getProductGender());
			pstmt.setInt(11, dto.getProductPrice());
			pstmt.setInt(12, dto.getProductOriginPrice());
			pstmt.setString(13, dto.getProductMaterial());
			pstmt.setString(14, dto.getProductPattern());
			pstmt.setString(15, dto.getProductFit());
			pstmt.setString(16, dto.getProductThickness());
			pstmt.setString(17, dto.getProductDate());
			pstmt.setInt(18, dto.getProductState());
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	/**
	 * 판매자 상품 등록용: DB 기본값(productDate 등)을 활용하고, 생성된 productNo를 반환합니다.
	 * (폼에 없는 필드가 많아서 최소 컬럼만 INSERT)
	 */
	public int insertProductAndGetNo(ProductDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = pool.getConnection();
			String sql = "INSERT INTO product (vendorNo, categoryNo, situationNo, productName, productBrand, productEx, easyOneLine, easyFor, easyComfort, productGender, "
					+ "productPrice, productOriginPrice, productMaterial, productPattern, productFit, productThickness, productState) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			pstmt.setInt(i++, dto.getVendorNo());
			pstmt.setInt(i++, dto.getCategoryNo());
			pstmt.setInt(i++, dto.getSituationNo());
			pstmt.setString(i++, dto.getProductName());
			pstmt.setString(i++, dto.getProductBrand());
			pstmt.setString(i++, dto.getProductEx());
			pstmt.setString(i++, dto.getEasyOneLine());
			pstmt.setString(i++, dto.getEasyFor());
			pstmt.setString(i++, dto.getEasyComfort());
			pstmt.setInt(i++, dto.getProductGender());
			pstmt.setInt(i++, dto.getProductPrice());
			pstmt.setInt(i++, dto.getProductOriginPrice());
			pstmt.setString(i++, dto.getProductMaterial());
			pstmt.setString(i++, dto.getProductPattern());
			pstmt.setString(i++, dto.getProductFit());
			pstmt.setString(i++, dto.getProductThickness());
			pstmt.setInt(i++, dto.getProductState());

			int updated = pstmt.executeUpdate();
			if (updated <= 0) {
				return 0;
			}
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return 0;
	}

	// Update
	public boolean updateProduct(ProductDTO dto, int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE Product SET vendorNo = ?, categoryNo = ?, situationNo = ?, productName = ?, productBrand = ?, productEx = ?, easyOneLine = ?, easyFor = ?, easyComfort = ?, productGender = ?, productPrice = ?, productOriginPrice = ?, productMaterial = ?, productPattern = ?, productFit = ?, productThickness = ?, productState = ? WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getVendorNo());
			pstmt.setInt(2, dto.getCategoryNo());
			pstmt.setInt(3, dto.getSituationNo());
			pstmt.setString(4, dto.getProductName());
			pstmt.setString(5, dto.getProductBrand());
			pstmt.setString(6, dto.getProductEx());
			pstmt.setString(7, dto.getEasyOneLine());
			pstmt.setString(8, dto.getEasyFor());
			pstmt.setString(9, dto.getEasyComfort());
			pstmt.setInt(10, dto.getProductGender());
			pstmt.setInt(11, dto.getProductPrice());
			pstmt.setInt(12, dto.getProductOriginPrice());
			pstmt.setString(13, dto.getProductMaterial());
			pstmt.setString(14, dto.getProductPattern());
			pstmt.setString(15, dto.getProductFit());
			pstmt.setString(16, dto.getProductThickness());
			pstmt.setInt(17, dto.getProductState());
			pstmt.setInt(18, productNo);
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
				mapResultSetToDTO(rs, dto);
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

	// 특정 상품의 대표 이미지 파일 경로 조회
	public String getProductImage(int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String img = null;
		try {
			con = pool.getConnection();
			String sql = "SELECT imgFile FROM productimage WHERE productNo = ? and imgType = 0";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				img = rs.getString("imgFile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return img;
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

	public int getProductOriginPrice(int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int productOriginPrice = 0;
		try {
			con = pool.getConnection();
			String sql = "SELECT productOriginPrice FROM product WHERE productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				productOriginPrice = rs.getInt("productOriginPrice");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return productOriginPrice;
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

	// 특정 상품의 모든 옵션 리스트 조회 (색상, 사이즈 등)
	public Vector<ProductOptionDTO> getProductOptions(int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<ProductOptionDTO> vlist = new Vector<>();

		try {
			con = pool.getConnection();
			// ProductOptionDTO 필드에 맞춘 SELECT 문
			String sql = "SELECT * FROM productOption WHERE productNo = ? AND optionStock > 0 ORDER BY productOptionNo ASC";
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

	// 상황(Situation) 번호로 상품 목록 조회 (JOIN 활용)
	public Vector<ProductDTO> getProductBySituationNo(int situationNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<ProductDTO> vlist = new Vector<ProductDTO>();
		try {
			con = pool.getConnection();
			// situationmapping 테이블과 조인하여 해당 상황에 매핑된 상품만 추출
			String sql = "SELECT p.* FROM product p " + "JOIN situationmapping sm ON p.productNo = sm.productNo "
					+ "WHERE sm.situationNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, situationNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				mapResultSetToDTO(rs, dto);
				vlist.addElement(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}

	// 카테고리(Category) 번호로 상품 목록 조회 (FK 직접 사용)
	public Vector<ProductDTO> getProductByCategoryNo(int categoryNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<ProductDTO> vlist = new Vector<ProductDTO>();
		try {
			con = pool.getConnection();
			// product 테이블의 categoryNo 컬럼을 직접 조건으로 사용
			String sql = "SELECT * FROM product WHERE categoryNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, categoryNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				mapResultSetToDTO(rs, dto);
				vlist.addElement(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}

	// 필터별 상품 불러오기
	public Vector<ProductDTO> getProductsByFilter(String viewMode, String category, String sort, String[] colors,
			String[] seasons, boolean seasonAllMatch, String[] features) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<ProductDTO> vlist = new Vector<>();

		try {
			con = pool.getConnection();

			StringBuilder sql = new StringBuilder();
			Vector<Object> params = new Vector<>();

			sql.append("SELECT DISTINCT p.* " + "FROM product p ");

			if ("situation".equals(viewMode)) {
				sql.append("JOIN situationmapping sm ON p.productNo = sm.productNo "
						+ "JOIN situation s ON sm.situationNo = s.situationNo ");
			}

			sql.append("WHERE p.productState = 1 ");

			// 카테고리 / 상황 조건
			if (category != null && !category.isEmpty()) {
				if ("type".equals(viewMode)) {
					sql.append("AND p.categoryNo = (" + "  SELECT categoryNo FROM category "
							+ "  WHERE categoryName = ? AND categoryLevel = 1 LIMIT 1) ");
					params.add(category);
				} else {
					sql.append("AND s.situationName = ? AND s.situationLevel = 2 ");
					params.add(category);
				}
			}

			// 색상 필터
			if (colors != null && colors.length > 0) {
				for (String color : colors) {
					sql.append("AND EXISTS (SELECT 1 FROM productoption po "
							+ "WHERE po.productNo = p.productNo AND po.optionColor = ?) ");
					params.add(color);
				}
			}

			if (seasons != null && seasons.length > 0) {
				if (seasonAllMatch) {
					sql.append(
							"AND p.productNo IN (" + "  SELECT productNo FROM productseason " + "  WHERE season IN (");
					for (int i = 0; i < seasons.length; i++) {
						sql.append(i == 0 ? "?" : ",?");
						params.add(seasons[i]);
					}
					sql.append(") GROUP BY productNo ");
					sql.append("  HAVING COUNT(DISTINCT season) = ").append(seasons.length);
					sql.append(") ");
				} else {
					sql.append("AND p.productNo IN (" + "  SELECT productNo FROM productseason WHERE season IN (");
					for (int i = 0; i < seasons.length; i++) {
						sql.append(i == 0 ? "?" : ",?");
						params.add(seasons[i]); // ← 이 줄도 빠져있었음
					}
					sql.append(")) ");
				}
			}

			// 특징 필터 (선택한 특징 전부 가진 상품만)
			if (features != null && features.length > 0) {
				for (String feature : features) {
					sql.append("AND EXISTS (SELECT 1 FROM productfeature pf "
							+ "WHERE pf.productNo = p.productNo AND pf.feature = ?) ");
					params.add(feature);
				}
			}

			// 정렬
			if ("가격 낮은순".equals(sort)) {
				sql.append("ORDER BY p.productPrice ASC");
			} else if ("가격 높은순".equals(sort)) {
				sql.append("ORDER BY p.productPrice DESC");
			} else if ("최신순".equals(sort)) {
				sql.append("ORDER BY p.productDate DESC");
			} else if ("인기순".equals(sort)) {
				sql.append("ORDER BY (p.wishCount + p.saleCount) DESC");
			} else {
				sql.append("ORDER BY p.productNo ASC");
			}

			pstmt = con.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(i + 1, params.get(i));
			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				mapResultSetToDTO(rs, dto);
				vlist.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}

	public Vector<ProductDTO> getProductsByCategoryName(String categoryName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<ProductDTO> vlist = new Vector<>();

		try {
			con = pool.getConnection();

			String sql = "SELECT p.* FROM product p " + "JOIN category c ON p.categoryNo = c.categoryNo "
					+ "WHERE c.categoryName = ? AND p.productState = 1 " + "ORDER BY p.productNo ASC";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, categoryName);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				mapResultSetToDTO(rs, dto);
				vlist.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return vlist;
	}

	public Vector<ProductDTO> getProductsBySituationName(String situationName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<ProductDTO> vlist = new Vector<>();

		try {
			con = pool.getConnection();

			String sql = "SELECT DISTINCT p.* FROM product p "
					+ "JOIN situationmapping sm ON p.productNo = sm.productNo "
					+ "JOIN situation s ON sm.situationNo = s.situationNo "
					+ "WHERE s.situationName = ? AND p.productState = 1 " + "ORDER BY p.productNo ASC";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, situationName);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				mapResultSetToDTO(rs, dto);
				vlist.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return vlist;
	}

	// wishCount + 1
	public boolean increaseWishCount(int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "UPDATE product SET wishCount = wishCount + 1 WHERE productNo = ?";
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

	// wishCount - 1
	public boolean decreaseWishCount(int productNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "UPDATE product SET wishCount = wishCount - 1 WHERE productNo = ? AND wishCount > 0";
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

	// 검색 메소드
	public Vector<ProductDTO> searchProducts(String keyword) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<ProductDTO> list = new Vector<>();
		try {
			con = pool.getConnection();
			String sql = "SELECT * FROM product " + "WHERE productName LIKE ? OR productBrand LIKE ? "
					+ "AND productState = 1 " + "ORDER BY productNo DESC";
			pstmt = con.prepareStatement(sql);
			String like = "%" + keyword + "%";
			pstmt.setString(1, like);
			pstmt.setString(2, like);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				// 기존 매핑과 동일하게
				dto.setProductNo(rs.getInt("productNo"));
				dto.setProductName(rs.getString("productName"));
				dto.setProductBrand(rs.getString("productBrand"));
				dto.setProductPrice(rs.getInt("productPrice"));
				dto.setProductOriginPrice(rs.getInt("productOriginPrice"));
				dto.setSaleCount(rs.getInt("saleCount"));
				dto.setWishCount(rs.getInt("wishCount"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return list;
	}

	// 검색 + 필터 메소드
	public Vector<ProductDTO> searchProductsWithFilter(String keyword, String sort, String[] colors, String season,
			String[] features) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<ProductDTO> list = new Vector<>();

		try {
			con = pool.getConnection();
			StringBuilder sql = new StringBuilder(
					"SELECT DISTINCT p.* FROM product p WHERE " + "(p.productName LIKE ? OR p.productBrand LIKE ?) ");
			Vector<Object> params = new Vector<>();
			String like = "%" + keyword + "%";
			params.add(like);
			params.add(like);

			// 색상
			if (colors != null && colors.length > 0) {
				for (String color : colors) {
					sql.append("AND EXISTS (SELECT 1 FROM productoption po "
							+ "WHERE po.productNo = p.productNo AND po.optionColor = ?) ");
					params.add(color);
				}
			}

			// 계절
			if (season != null && !season.isEmpty()) {
				sql.append("AND p.productNo IN (" + "SELECT productNo FROM productseason WHERE season = ?) ");
				params.add(season);
			}

			// 특징
			if (features != null && features.length > 0) {
				for (String feature : features) {
					sql.append("AND EXISTS (SELECT 1 FROM productfeature pf "
							+ "WHERE pf.productNo = p.productNo AND pf.feature = ?) ");
					params.add(feature);
				}
			}

			// 정렬
			if ("가격 낮은순".equals(sort))
				sql.append("ORDER BY p.productPrice ASC");
			else if ("가격 높은순".equals(sort))
				sql.append("ORDER BY p.productPrice DESC");
			else if ("최신순".equals(sort))
				sql.append("ORDER BY p.productDate DESC");
			else if ("인기순".equals(sort))
				sql.append("ORDER BY (p.wishCount + p.saleCount) DESC");
			else
				sql.append("ORDER BY p.productNo DESC");

			pstmt = con.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(i + 1, params.get(i));
			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				mapResultSetToDTO(rs, dto); // 기존 매핑 메서드 재사용
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return list;
	}

	// SELECT * 매핑 코드 너무 많아서 라인 수 줄이기 위해
	private void mapResultSetToDTO(ResultSet rs, ProductDTO dto) throws java.sql.SQLException {
	    dto.setProductNo(rs.getInt("productNo"));
	    dto.setVendorNo(rs.getInt("vendorNo"));
	    dto.setCategoryNo(rs.getInt("categoryNo"));
	    dto.setSituationNo(rs.getInt("situationNo"));
	    dto.setProductName(rs.getString("productName"));
	    dto.setProductBrand(rs.getString("productBrand"));
	    dto.setProductEx(rs.getString("productEx"));
	    dto.setEasyOneLine(rs.getString("easyOneLine"));
	    dto.setEasyFor(rs.getString("easyFor"));
	    dto.setEasyComfort(rs.getString("easyComfort"));
	    dto.setProductGender(rs.getInt("productGender"));
	    dto.setProductPrice(rs.getInt("productPrice"));
	    dto.setProductOriginPrice(rs.getInt("productOriginPrice"));
	    dto.setProductMaterial(rs.getString("productMaterial"));
	    dto.setProductPattern(rs.getString("productPattern"));
	    dto.setProductFit(rs.getString("productFit"));
	    dto.setProductThickness(rs.getString("productThickness"));
	    dto.setProductDate(rs.getString("productDate"));
	    dto.setWishCount(rs.getInt("wishCount"));
	    dto.setSaleCount(rs.getInt("saleCount"));
	    dto.setProductState(rs.getInt("productState"));
	}

	public Vector<ProductDTO> getAllActiveProducts() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ProductDTO> vlist = new Vector<ProductDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM product where productState = 1";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				mapResultSetToDTO(rs, dto);
				vlist.addElement(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	
	public Vector<ProductDTO> getProductsByVendor(int vendorNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Vector<ProductDTO> vlist = new Vector<>();
	    
	    try {
	        con = pool.getConnection();
	        // 상태가 삭제(-1 등)가 아닌 정상 노출/숨김(1, 0 등) 상품만 가져온다고 가정
	        String sql = "SELECT * FROM product WHERE vendorNo = ? AND productState >= 0 ORDER BY productNo DESC";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, vendorNo);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            ProductDTO dto = new ProductDTO();
	            mapResultSetToDTO(rs, dto); // 기존에 작성하신 매핑 메서드 재활용
	            vlist.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return vlist;
	}
	
	// 계절이 season인 상품 리스트 반환
	public Vector<ProductDTO> getProductsBySeason(String season) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Vector<ProductDTO> vlist = new Vector<ProductDTO>();
	    
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT p.* FROM product p " +
	                     "JOIN productseason ps ON p.productNo = ps.productNo " +
	                     "WHERE ps.season = ? ORDER BY p.wishCount desc";
	        
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, season);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            ProductDTO dto = new ProductDTO();
	            mapResultSetToDTO(rs, dto);
	            vlist.addElement(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return vlist;
	}
	
	// 최신 상품 5개만 가져오기
	public Vector<ProductDTO> getNewProducts() {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Vector<ProductDTO> vlist = new Vector<ProductDTO>();
	    
	    try {
	        con = pool.getConnection();
	        // 최신 등록순으로 5개만 가져오는 쿼리
	        String sql = "SELECT * FROM product ORDER BY productDate DESC LIMIT 5";
	        
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            ProductDTO dto = new ProductDTO();
	            mapResultSetToDTO(rs, dto);
	            vlist.addElement(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return vlist;
	}
}