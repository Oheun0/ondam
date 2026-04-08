package com.ondam.orders.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.orders.dto.OrdersDTO;
import com.ondam.orders.dto.OrdersProductDTO;
import com.ondam.orders.service.OrdersProductService;
import com.ondam.orders.service.OrdersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class OrderDetailController implements Controller {

	private OrdersService ordersService = new OrdersService();
	private OrdersProductService productService = new OrdersProductService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}
		String orderNoStr = request.getParameter("orderNo");
		if (orderNoStr == null || orderNoStr.trim().isEmpty()) {
			return "redirect:/order/order-list";
		}
		int orderNo = Integer.parseInt(orderNoStr);

		OrdersDTO orderInfo = ordersService.getOrderByOrderNo(orderNo);
		Vector<OrdersProductDTO> productList = productService.getProductsByOrderNo(orderNo);

		request.setAttribute("orderInfo", orderInfo);
		request.setAttribute("productList", productList);
		return "order/order-detail";
	}
}