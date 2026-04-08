package com.ondam.orders.controller;

import java.util.HashMap;
import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.orders.dto.OrdersDTO;
import com.ondam.orders.dto.OrdersProductDTO;
import com.ondam.orders.service.OrdersProductService;
import com.ondam.orders.service.OrdersService;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class OrderListController implements Controller {

	private OrdersService ordersService = new OrdersService();
	private OrdersProductService productService = new OrdersProductService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		Vector<OrdersDTO> orderList = ordersService.getOrdersByUserNo(loginUser.getUserNo());
		int shippingCount = 0;
		int deliveredCount = 0;
		int cancelCount = 0;

		if (orderList != null) {
		    for (OrdersDTO order : orderList) {
		        if (order.getOrderState() == 2 || order.getOrderState() == 3) {
		            cancelCount++;
		        } 
		        else {
		            if (order.getDeliveryState() == 2) {
		                shippingCount++;
		            } else if (order.getDeliveryState() == 3) {
		                deliveredCount++;
		            }
		        }
		    }
		}
		request.setAttribute("shippingCount", shippingCount);
		request.setAttribute("deliveredCount", deliveredCount);
		request.setAttribute("cancelCount", cancelCount);
		
		HashMap<Integer, Vector<OrdersProductDTO>> orderProductMap = new HashMap<>();

		if (orderList != null && !orderList.isEmpty()) {
			for (OrdersDTO order : orderList) {
				Vector<OrdersProductDTO> productList = productService.getProductsByOrderNo(order.getOrderNo());
				orderProductMap.put(order.getOrderNo(), productList);
			}
		}
		request.setAttribute("orderList", orderList);
		request.setAttribute("orderProductMap", orderProductMap);
		return "order/order-list"; 
	}
}