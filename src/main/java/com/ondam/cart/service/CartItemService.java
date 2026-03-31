package com.ondam.cart.service;

import java.util.Vector;

import com.ondam.cart.dao.CartItemDAO;
import com.ondam.cart.dto.CartItemDTO;

public class CartItemService {

	private CartItemDAO dao;

	public CartItemService() {
		this.dao = new CartItemDAO();
	}

	public Vector<CartItemDTO> getCartItemList() {
		return dao.getCartItem();
	}

	public boolean createCartItem(CartItemDTO dto) {
		return dao.insertCartItem(dto);
	}

	public boolean modifyCartItem(CartItemDTO dto, int cartItemNo) {
		return dao.updateCartItem(dto, cartItemNo);
	}

	public boolean removeCartItem(int cartItemNo) {
		return dao.deleteCartItem(cartItemNo);
	}
}

