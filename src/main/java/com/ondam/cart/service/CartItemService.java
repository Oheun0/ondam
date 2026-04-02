package com.ondam.cart.service;

import java.util.Vector;
import com.ondam.cart.dao.CartItemDAO;
import com.ondam.cart.dto.CartItemDTO;

public class CartItemService {

    private CartItemDAO dao = new CartItemDAO();

    // 1. 단일 아이템 조회 에러 해결 (Type mismatch 해결)
    // 리스트를 원한다면 getCartItems를 호출하고, 단일을 원한다면 리턴 타입을 DTO로 바꿔야 합니다.
    public Vector<CartItemDTO> getCartItem(int cartNo) {
        // DAO의 getCartItems는 Vector를 반환하므로 타입을 맞춰줍니다.
        return dao.getCartItems(cartNo);
    }

    // 2. 추가 로직 (Cannot return a void result 해결)
    public void insertCartItem(CartItemDTO dto) {
        // DAO의 메서드가 void이므로 return을 제거하고 호출만 합니다.
        dao.insertCartItem(dto);
    }

    // 3. 수정 로직 (Cannot return a void result 해결)
    public void updateCartItem(CartItemDTO dto, int quantity) {
        // return을 제거하고 호출만 합니다.
        dao.updateCartItem(dto, quantity);
    }

    // 4. 삭제 로직 (Cannot return a void result 해결)
    public void deleteCartItem(int cartItemNo) {
        // return을 제거하고 호출만 합니다.
        dao.deleteCartItem(cartItemNo);
    }
}