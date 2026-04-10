package com.ondam.orders.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.orders.dto.OrdersDTO;

public class OrdersDAO {

	private DBConnectionMgr pool;

	public OrdersDAO() {
		pool = DBConnectionMgr.getInstance();
	}

	// 전체 Select
	public Vector<OrdersDTO> getOrders() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<OrdersDTO> vlist = new Vector<OrdersDTO>();
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement("SELECT * FROM orders");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				vlist.addElement(mapRow(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}

	// Insert (기존 insertOrders — 필요 시 사용)
	public boolean insertOrders(OrdersDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "INSERT INTO Orders (userNo, orderCode, receiverName, receiverTel, deliveryAddr, "
					+ "deliveryContent, orderPrice, productDiscount, couponDiscount, paymentAmount, "
					+ "paymentMethod, userCouponNo, orderType) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserNo());
			pstmt.setString(2, dto.getOrderCode());
			pstmt.setString(3, dto.getReceiverName());
			pstmt.setString(4, dto.getReceiverTel());
			pstmt.setString(5, dto.getDeliveryAddr());
			pstmt.setString(6, dto.getDeliveryContent());
			pstmt.setInt(7, dto.getOrderPrice());
			pstmt.setInt(8, dto.getProductDiscount());
			pstmt.setInt(9, dto.getCouponDiscount());
			pstmt.setInt(10, dto.getPaymentAmount());
			pstmt.setInt(11, dto.getPaymentMethod());
			if (dto.getUserCouponNo() == 0) {
				pstmt.setNull(12, java.sql.Types.INTEGER);
			} else {
				pstmt.setInt(12, dto.getUserCouponNo());
			}
			pstmt.setInt(13, dto.getOrderType());
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
	public boolean updateOrders(OrdersDTO dto, int orderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			String sql = "UPDATE Orders SET userNo=?, orderCode=?, receiverName=?, receiverTel=?, "
					+ "deliveryAddr=?, deliveryContent=?, orderPrice=?, productDiscount=?, "
					+ "couponDiscount=?, paymentAmount=?, paymentMethod=?, userCouponNo=?, "
					+ "orderState=?, orderDate=?, orderUpdateDate=?, deliveryState=?, "
					+ "orderType=?, giftReceiverNo=? WHERE orderNo=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserNo());
			pstmt.setString(2, dto.getOrderCode());
			pstmt.setString(3, dto.getReceiverName());
			pstmt.setString(4, dto.getReceiverTel());
			pstmt.setString(5, dto.getDeliveryAddr());
			pstmt.setString(6, dto.getDeliveryContent());
			pstmt.setInt(7, dto.getOrderPrice());
			pstmt.setInt(8, dto.getProductDiscount());
			pstmt.setInt(9, dto.getCouponDiscount());
			pstmt.setInt(10, dto.getPaymentAmount());
			pstmt.setInt(11, dto.getPaymentMethod());
			if (dto.getUserCouponNo() == 0) {
				pstmt.setNull(12, java.sql.Types.INTEGER);
			} else {
				pstmt.setInt(12, dto.getUserCouponNo());
			}
			pstmt.setInt(13, dto.getOrderState());
			pstmt.setString(14, dto.getOrderDate());
			pstmt.setString(15, dto.getOrderUpdateDate());
			pstmt.setInt(16, dto.getDeliveryState());
			pstmt.setInt(17, dto.getOrderType());
			pstmt.setInt(18, dto.getGiftReceiverNo());
			pstmt.setInt(19, orderNo);
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
	public boolean deleteOrders(int orderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement("DELETE FROM Orders WHERE orderNo = ?");
			pstmt.setInt(1, orderNo);
			if (pstmt.executeUpdate() > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	// 특정 유저의 주문 내역 Select
	public Vector<OrdersDTO> getOrdersByUserNo(int userNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<OrdersDTO> vlist = new Vector<>();
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement("SELECT * FROM Orders WHERE userNo = ? ORDER BY orderDate DESC");
			pstmt.setInt(1, userNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrdersDTO dto = new OrdersDTO();
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setUserNo(rs.getInt("userNo"));
				dto.setOrderCode(rs.getString("orderCode"));
				dto.setReceiverName(rs.getString("receiverName"));
				dto.setOrderPrice(rs.getInt("orderPrice"));
				dto.setProductDiscount(rs.getInt("productDiscount"));
				dto.setCouponDiscount(rs.getInt("couponDiscount"));
				dto.setPaymentAmount(rs.getInt("paymentAmount"));
				dto.setOrderState(rs.getInt("orderState"));
				dto.setOrderDate(rs.getString("orderDate"));
				dto.setDeliveryState(rs.getInt("deliveryState"));
				vlist.addElement(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}

	// 특정 주문 번호 상세 정보 Select
	public OrdersDTO getOrderByOrderNo(int orderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrdersDTO dto = null;
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement("SELECT * FROM Orders WHERE orderNo = ?");
			pstmt.setInt(1, orderNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = mapRow(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return dto;
	}

	// Orders INSERT 후 생성된 orderNo 반환
	public int insertOrdersAndGetNo(OrdersDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int orderNo = 0;
		try {
			con = pool.getConnection();
			String sql = "INSERT INTO Orders (userNo, orderCode, receiverName, receiverTel, deliveryAddr, "
					+ "deliveryContent, orderPrice, productDiscount, couponDiscount, paymentAmount, "
					+ "paymentMethod, userCouponNo, orderType) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, dto.getUserNo());
			pstmt.setString(2, dto.getOrderCode());
			pstmt.setString(3, dto.getReceiverName());
			pstmt.setString(4, dto.getReceiverTel());
			pstmt.setString(5, dto.getDeliveryAddr());
			pstmt.setString(6, dto.getDeliveryContent());
			pstmt.setInt(7, dto.getOrderPrice());
			pstmt.setInt(8, dto.getProductDiscount());
			pstmt.setInt(9, dto.getCouponDiscount());
			pstmt.setInt(10, dto.getPaymentAmount());
			pstmt.setInt(11, dto.getPaymentMethod());
			if (dto.getUserCouponNo() == 0) {
				pstmt.setNull(12, java.sql.Types.INTEGER);
			} else {
				pstmt.setInt(12, dto.getUserCouponNo());
			}
			pstmt.setInt(13, dto.getOrderType());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next())
				orderNo = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return orderNo;
	}
  
	// ── 공통 매핑 헬퍼 ──────────────────────────────
	private OrdersDTO mapRow(ResultSet rs) throws Exception {
		OrdersDTO dto = new OrdersDTO();
		dto.setOrderNo(rs.getInt("orderNo"));
		dto.setUserNo(rs.getInt("userNo"));
		dto.setOrderCode(rs.getString("orderCode"));
		dto.setReceiverName(rs.getString("receiverName"));
		dto.setReceiverTel(rs.getString("receiverTel"));
		dto.setDeliveryAddr(rs.getString("deliveryAddr"));
		dto.setDeliveryContent(rs.getString("deliveryContent"));
		dto.setOrderPrice(rs.getInt("orderPrice"));
		dto.setProductDiscount(rs.getInt("productDiscount"));
		dto.setCouponDiscount(rs.getInt("couponDiscount"));
		dto.setPaymentAmount(rs.getInt("paymentAmount"));
		dto.setPaymentMethod(rs.getInt("paymentMethod"));
		dto.setUserCouponNo(rs.getInt("userCouponNo"));
		dto.setOrderState(rs.getInt("orderState"));
		dto.setOrderDate(rs.getString("orderDate"));
		dto.setOrderUpdateDate(rs.getString("orderUpdateDate"));
		dto.setDeliveryState(rs.getInt("deliveryState"));
		dto.setOrderType(rs.getInt("orderType"));
		dto.setGiftReceiverNo(rs.getInt("giftReceiverNo"));
		return dto;
	}

	public Vector<Integer> getOrderNosByUser(int userNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<Integer> orderNos = new Vector<>();

		try {
			con = pool.getConnection();
			// 최근 3개월 동안 해당 사용자가 주문한 주문 번호(orderNo)만 추출
			String sql = "SELECT orderNo FROM orders "
					+ "WHERE userNo = ? AND orderDate >= DATE_SUB(NOW(), INTERVAL 3 MONTH) "
					+ "ORDER BY orderDate DESC";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderNos.add(rs.getInt("orderNo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return orderNos;
	}
}