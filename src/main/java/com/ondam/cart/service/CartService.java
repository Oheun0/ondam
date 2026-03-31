package com.ondam.cart.service;

import java.util.Vector;

import com.ondam.cart.dao.CartDAO;
import com.ondam.cart.dto.CartDTO;

public class CartService {

	private CartDAO dao;

	public CartService() {
		this.dao = new CartDAO();
	}

	public Vector<CartDTO> getCartList() {
		return dao.getCart();
	}

	public boolean createCart(CartDTO dto) {
		return dao.insertCart(dto);
	}

	public boolean modifyCart(CartDTO dto, int cartNo) {
		return dao.updateCart(dto, cartNo);
	}

	public boolean removeCart(int cartNo) {
		return dao.deleteCart(cartNo);
	}
}

