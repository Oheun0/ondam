package com.ondam.orders.service;

import java.util.Vector;

import com.ondam.orders.dao.OrdersDAO;
import com.ondam.orders.dto.OrdersDTO;

public class OrdersService {

	private OrdersDAO dao;

	public OrdersService() {
		this.dao = new OrdersDAO();
	}

	public Vector<OrdersDTO> getOrdersList() {
		return dao.getOrders();
	}

	public boolean createOrders(OrdersDTO dto) {
		return dao.insertOrders(dto);
	}

	public boolean modifyOrders(OrdersDTO dto, int orderNo) {
		return dao.updateOrders(dto, orderNo);
	}

	public boolean removeOrders(int orderNo) {
		return dao.deleteOrders(orderNo);
	}
	
	public Vector<OrdersDTO> getOrdersByUserNo(int userNo){
		return dao.getOrdersByUserNo(userNo);
	}
	
	public OrdersDTO getOrderByOrderNo(int orderNo) {
		return dao.getOrderByOrderNo(orderNo);
	}
	
	public int createOrdersAndGetNo(OrdersDTO dto) {
	    return dao.insertOrdersAndGetNo(dto);
	}
	
	public boolean updateGiftReceiverNo(int orderNo, int receiverNo) {
	    return dao.updateGiftReceiverNo(orderNo, receiverNo);
	}
}