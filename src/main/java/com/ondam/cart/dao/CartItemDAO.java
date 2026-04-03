package com.ondam.cart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import com.ondam.cart.dto.CartItemDTO;
import com.ondam.common.DBConnectionMgr;
import com.ondam.product.dto.ProductOptionDTO;

public class CartItemDAO {
    private DBConnectionMgr pool = DBConnectionMgr.getInstance();

 // 1. 장바구니 아이템 전체 조회 (단순 조회 버전)
    public Vector<CartItemDTO> getCartItems(int cartNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vector<CartItemDTO> vlist = new Vector<>();
        try {
            con = pool.getConnection();
            // 조인 없이 CartItem 테이블만 조회
            String sql = "SELECT * FROM CartItem WHERE cartNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, cartNo);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                CartItemDTO dto = new CartItemDTO();
                dto.setCartItemNo(rs.getInt("cartItemNo"));
                dto.setCartNo(rs.getInt("cartNo"));
                dto.setProductNo(rs.getInt("productNo"));
                dto.setProductOptionNo(rs.getInt("productOptionNo"));
                dto.setCartQuantity(rs.getInt("cartQuantity"));
                dto.setCartAddedDate(rs.getString("cartAddedDate"));
                vlist.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }

    // 2. 단일 아이템 조회 (CartItemService용 getCartItem)
    public CartItemDTO getCartItem() {
        // 보통 인자가 필요하지만, 에러 메시지에 따라 기본 생성 형태만 정의
        return new CartItemDTO(); 
    }

    // 3. 아이템 추가 (CartService용: 인자 4개)
    public void insertCartItem(int cartNo, int productNo, int productOptionNo, int quantity) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = pool.getConnection();
            String sql = "INSERT INTO CartItem (cartNo, productNo, productOptionNo, cartQuantity, cartAddedDate) VALUES (?, ?, ?, ?, NOW())";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, cartNo);
            pstmt.setInt(2, productNo);
            pstmt.setInt(3, productOptionNo);
            pstmt.setInt(4, quantity);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt); }
    }

    // 4. 아이템 추가 (CartItemService용: DTO 인자)
    public void insertCartItem(CartItemDTO dto) {
        insertCartItem(dto.getCartNo(), dto.getProductNo(), dto.getProductOptionNo(), dto.getCartQuantity());
    }

    // 5. 수량 업데이트 (CartService용: int, int)
    public void updateQuantity(int cartItemNo, int quantity) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = pool.getConnection();
            String sql = "UPDATE CartItem SET cartQuantity = ? WHERE cartItemNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, cartItemNo);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt); }
    }

    // 6. 수량 업데이트 (CartItemService용: DTO, int)
    public void updateCartItem(CartItemDTO dto, int quantity) {
        updateQuantity(dto.getCartItemNo(), quantity);
    }

    // 7. 개별 삭제 (CartService용: deleteItem)
    public void deleteItem(int cartItemNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = pool.getConnection();
            String sql = "DELETE FROM CartItem WHERE cartItemNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, cartItemNo);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt); }
    }

    // 8. 개별 삭제 (CartItemService용: deleteCartItem)
    public void deleteCartItem(int cartItemNo) {
        deleteItem(cartItemNo);
    }

    // 9. 전체 삭제 (추가)
    public void deleteAllItems(int cartNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = pool.getConnection();
            String sql = "DELETE FROM CartItem WHERE cartNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, cartNo);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt); }
    }
    
    // 10. 중복 체크 (Service 로직에서 사용)
    public CartItemDTO checkExistingItem(int cartNo, int productNo, int productOptionNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CartItemDTO dto = null;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM CartItem WHERE cartNo=? AND productNo=? AND productOptionNo=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, cartNo);
            pstmt.setInt(2, productNo);
            pstmt.setInt(3, productOptionNo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new CartItemDTO();
                dto.setCartItemNo(rs.getInt("cartItemNo"));
                dto.setCartQuantity(rs.getInt("cartQuantity"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt, rs); }
        return dto;
    }
    
    public void deleteSelectedItems(int[] cartItemNos) {
        Connection con = null; 
        PreparedStatement pstmt = null;
        try {
            con = pool.getConnection();
            // AutoCommit을 끌 경우 트랜잭션 관리가 더 확실해집니다 (여기선 기본 실행)
            String sql = "DELETE FROM CartItem WHERE cartItemNo = ?";
            pstmt = con.prepareStatement(sql);
            
            for (int no : cartItemNos) {
                pstmt.setInt(1, no);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally { 
            pool.freeConnection(con, pstmt); 
        }
    }
    
    // 수량 조절 검증을 위한 단일 조회 로직 추가
    public CartItemDTO getCartItemByNo(int cartItemNo) {
        Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
        CartItemDTO dto = null;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM CartItem WHERE cartItemNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, cartItemNo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new CartItemDTO();
                dto.setCartItemNo(rs.getInt("cartItemNo"));
                dto.setProductOptionNo(rs.getInt("productOptionNo"));
                dto.setCartQuantity(rs.getInt("cartQuantity"));
            }
        } catch (Exception e) { 
        	e.printStackTrace(); 
        } finally { 
        	pool.freeConnection(con, pstmt, rs); 
        }
        return dto;
    }
    
    // 장바구니에 담긴 모든 상품의 수량 총합 반환
    public int getCartTotalQuantity(int cartNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int totalQty = 0;
        try {
            con = pool.getConnection();
            // 전체 수량 합산을 위해 SUM() 함수 사용
            String sql = "SELECT SUM(cartQuantity) FROM CartItem WHERE cartNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, cartNo);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // 결과가 NULL일 경우 rs.getInt()는 0을 반환하므로 안전합니다.
                totalQty = rs.getInt(1); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return totalQty;
    }
    
    // 특정 제품(productNo)의 중복되지 않는 '사이즈' 목록 조회
    public Vector<String> getUniqueSizesByProduct(int productNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vector<String> sizes = new Vector<>();
        try {
            con = pool.getConnection();
            // DISTINCT를 사용하여 중복된 사이즈를 제거하고 가져옵니다.
            String sql = "SELECT DISTINCT optionSize FROM ProductOption WHERE productNo = ? ORDER BY optionSize ASC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, productNo);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                sizes.add(rs.getString("optionSize"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return sizes;
    }

    // 특정 제품(productNo)과 선택된 '사이즈'에 해당하는 '색상 및 상세 옵션' 목록 조회
    public Vector<ProductOptionDTO> getColorsByProductSize(int productNo, String optionSize) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vector<ProductOptionDTO> vlist = new Vector<>();
        try {
            con = pool.getConnection();
            // 해당 사이즈에 필터링된 색상, 재고, 추가 금액 정보를 가져옵니다.
            String sql = "SELECT productOptionNo, optionColor, optionStock, optionAddPrice " +
                         "FROM ProductOption WHERE productNo = ? AND optionSize = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, productNo);
            pstmt.setString(2, optionSize);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductOptionDTO dto = new ProductOptionDTO();
                dto.setProductOptionNo(rs.getInt("productOptionNo"));
                dto.setOptionColor(rs.getString("optionColor"));
                dto.setOptionStock(rs.getInt("optionStock"));
                dto.setOptionAddPrice(rs.getInt("optionAddPrice"));
                vlist.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }
    
    // 특정 장바구니 아이템의 옵션을 변경하는 메서드
    public void updateOptionNo(int cartItemNo, int productOptionNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = pool.getConnection();
            // SQL: CartItem 테이블에서 cartItemNo가 일치하는 행의 productOptionNo를 변경함
            String sql = "UPDATE CartItem SET productOptionNo = ? WHERE cartItemNo = ?";
            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, productOptionNo); // 새 옵션 번호
            pstmt.setInt(2, cartItemNo);      // 수정할 아이템 번호
            
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
    }
}