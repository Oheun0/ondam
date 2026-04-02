package com.ondam.cart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import com.ondam.cart.dto.CartItemDTO;
import com.ondam.common.DBConnectionMgr;

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
}