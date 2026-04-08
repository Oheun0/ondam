package com.ondam.orders.service;

import java.util.Vector;

import com.ondam.orders.dao.OrdersProductDAO;
import com.ondam.orders.dto.OrdersProductDTO;

public class OrdersProductService {

	private OrdersProductDAO dao;

	public OrdersProductService() {
		this.dao = new OrdersProductDAO();
	}

	public Vector<OrdersProductDTO> getOrdersProductList() {
		return dao.getOrdersProduct();
	}

	public boolean createOrdersProduct(OrdersProductDTO dto) {
		return dao.insertOrdersProduct(dto);
	}

	public boolean modifyOrdersProduct(OrdersProductDTO dto, int orderItemNo) {
		return dao.updateOrdersProduct(dto, orderItemNo);
	}

	public boolean removeOrdersProduct(int orderItemNo) {
		return dao.deleteOrdersProduct(orderItemNo);
	}
	
	public Vector<OrdersProductDTO> getProductsByOrderNo(int orderNo) {
		return dao.getProductsByOrderNo(orderNo);
	}
}

