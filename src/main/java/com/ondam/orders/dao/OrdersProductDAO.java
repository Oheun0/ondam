package com.ondam.orders.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.orders.dto.OrdersProductDTO;

public class OrdersProductDAO {

	private DBConnectionMgr pool;

	public OrdersProductDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// Select
	public Vector<OrdersProductDTO> getOrdersProduct() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<OrdersProductDTO> vlist = new Vector<OrdersProductDTO>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM ordersProduct";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrdersProductDTO dto = new OrdersProductDTO();
				dto.setOrderItemNo(rs.getInt("orderItemNo"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setProductNo(rs.getInt("productNo"));
				dto.setProductOptionNo(rs.getInt("productOptionNo"));
				dto.setSnapProductName(rs.getString("snapProductName"));
				dto.setSnapProductPrice(rs.getInt("snapProductPrice"));
				dto.setSnapOptionSize(rs.getString("snapOptionSize"));
				dto.setSnapOptionColor(rs.getString("snapOptionColor"));
				dto.setOrderQuantity(rs.getInt("orderQuantity"));
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
	public boolean insertOrdersProduct(OrdersProductDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT OrdersProduct (orderNo, productNo, productOptionNo, snapProductName, snapProductPrice, snapOptionSize, snapOptionColor, orderQuantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getOrderNo());
			pstmt.setInt(2, dto.getProductNo());
			pstmt.setInt(3, dto.getProductOptionNo());
			pstmt.setString(4, dto.getSnapProductName());
			pstmt.setInt(5, dto.getSnapProductPrice());
			pstmt.setString(6, dto.getSnapOptionSize());
			pstmt.setString(7, dto.getSnapOptionColor());
			pstmt.setInt(8, dto.getOrderQuantity());
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	// Update
	public boolean updateOrdersProduct(OrdersProductDTO dto, int orderItemNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE OrdersProduct SET orderNo = ?, productNo = ?, productOptionNo = ?, snapProductName = ?, snapProductPrice = ?, snapOptionSize = ?, snapOptionColor = ?, orderQuantity = ? WHERE orderItemNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getOrderNo());
			pstmt.setInt(2, dto.getProductNo());
			pstmt.setInt(3, dto.getProductOptionNo());
			pstmt.setString(4, dto.getSnapProductName());
			pstmt.setInt(5, dto.getSnapProductPrice());
			pstmt.setString(6, dto.getSnapOptionSize());
			pstmt.setString(7, dto.getSnapOptionColor());
			pstmt.setInt(8, dto.getOrderQuantity());
			pstmt.setInt(9, orderItemNo);
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
	public boolean deleteOrdersProduct(int orderItemNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM OrdersProduct WHERE orderItemNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, orderItemNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	public int getOrderProductNo(int orderNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    int productNo = 0;

	    try {
	        con = pool.getConnection();
	        sql = "SELECT productNo FROM ordersProduct WHERE orderNo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, orderNo);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	        	productNo = rs.getInt("productNo");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return productNo;
	}
}

