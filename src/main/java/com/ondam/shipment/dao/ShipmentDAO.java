package com.ondam.shipment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.shipment.dto.ShipmentDTO;

public class ShipmentDAO {

    private DBConnectionMgr pool;

    public ShipmentDAO() {
        pool = DBConnectionMgr.getInstance();
    }

    // 1) 송장 등록
    public boolean insertShipment(ShipmentDTO dto) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean flag = false;

        try {
            con = pool.getConnection();

            String sql = "INSERT INTO shipments (orderItemNo, carrierCode, trackingNo, shipmentStatus, shippedAt) "
                    + "VALUES (?, ?, ?, 1, NOW())";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, dto.getOrderItemNo());
            pstmt.setString(2, dto.getCarrierCode());
            pstmt.setString(3, dto.getTrackingNo());

            if (pstmt.executeUpdate() > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }

        return flag;
    }

    // 2) 배송 상태 변경 (orderItemNo 기준)
    public boolean updateShipmentStatus(int orderItemNo, int shipmentStatus) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean flag = false;

        try {
            con = pool.getConnection();

            String sql = "UPDATE shipments "
                    + "SET shipmentStatus = ?, "
                    + "shippedAt = CASE WHEN ? = 2 AND shippedAt IS NULL THEN NOW() ELSE shippedAt END, "
                    + "deliveredAt = CASE WHEN ? = 3 THEN NOW() ELSE deliveredAt END "
                    + "WHERE orderItemNo = ?";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, shipmentStatus);
            pstmt.setInt(2, shipmentStatus);
            pstmt.setInt(3, shipmentStatus);
            pstmt.setInt(4, orderItemNo);

            if (pstmt.executeUpdate() > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }

        return flag;
    }

    // 3) 배송 조회 (주문번호 기준, 아이템별 배송 목록)
    public Vector<ShipmentDTO> getShipmentsByOrderNo(int orderNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vector<ShipmentDTO> vlist = new Vector<>();

        try {
            con = pool.getConnection();

            String sql = "SELECT op.orderItemNo, op.orderNo, op.productNo, op.snapProductName, p.vendorNo, "
                    + "s.shipmentNo, s.carrierCode, s.trackingNo, COALESCE(s.shipmentStatus, 0) AS shipmentStatus, "
                    + "s.shippedAt, s.deliveredAt, s.createdAt, s.updatedAt "
                    + "FROM ordersproduct op "
                    + "JOIN product p ON p.productNo = op.productNo "
                    + "LEFT JOIN shipments s ON s.orderItemNo = op.orderItemNo "
                    + "WHERE op.orderNo = ? "
                    + "ORDER BY op.orderItemNo ASC";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderNo);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ShipmentDTO dto = new ShipmentDTO();
                dto.setOrderItemNo(rs.getInt("orderItemNo"));
                dto.setOrderNo(rs.getInt("orderNo"));
                dto.setProductNo(rs.getInt("productNo"));
                dto.setSnapProductName(rs.getString("snapProductName"));
                dto.setVendorNo(rs.getInt("vendorNo"));
                dto.setShipmentNo(rs.getInt("shipmentNo"));
                dto.setCarrierCode(rs.getString("carrierCode"));
                dto.setTrackingNo(rs.getString("trackingNo"));
                dto.setShipmentStatus(rs.getInt("shipmentStatus"));
                dto.setShippedAt(rs.getString("shippedAt"));
                dto.setDeliveredAt(rs.getString("deliveredAt"));
                dto.setCreatedAt(rs.getString("createdAt"));
                dto.setUpdatedAt(rs.getString("updatedAt"));
                vlist.addElement(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }

        return vlist;
    }

    // 판매자 권한 검증: 해당 orderItemNo가 로그인 판매자 상품인지 확인
    public boolean isOrderItemOwnedByVendor(int orderItemNo, int vendorNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean owned = false;

        try {
            con = pool.getConnection();
            String sql = "SELECT 1 "
                    + "FROM ordersproduct op "
                    + "JOIN product p ON p.productNo = op.productNo "
                    + "WHERE op.orderItemNo = ? AND p.vendorNo = ? "
                    + "LIMIT 1";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderItemNo);
            pstmt.setInt(2, vendorNo);
            rs = pstmt.executeQuery();
            owned = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }

        return owned;
    }

    // 주문 요약 배송 상태 동기화
    public boolean syncOrderDeliveryStateByOrderItemNo(int orderItemNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean flag = false;

        try {
            con = pool.getConnection();
            String sql = "UPDATE orders o "
                    + "JOIN ( "
                    + "  SELECT op.orderNo, "
                    + "         CASE "
                    + "           WHEN COUNT(s.orderItemNo) = 0 THEN 0 "
                    + "           WHEN SUM(CASE WHEN COALESCE(s.shipmentStatus, 0) = 3 THEN 1 ELSE 0 END) = COUNT(op.orderItemNo) THEN 3 "
                    + "           WHEN SUM(CASE WHEN COALESCE(s.shipmentStatus, 0) = 2 THEN 1 ELSE 0 END) > 0 THEN 2 "
                    + "           WHEN SUM(CASE WHEN COALESCE(s.shipmentStatus, 0) = 1 THEN 1 ELSE 0 END) > 0 THEN 1 "
                    + "           ELSE 0 "
                    + "         END AS aggDeliveryState "
                    + "  FROM ordersproduct op "
                    + "  LEFT JOIN shipments s ON s.orderItemNo = op.orderItemNo "
                    + "  WHERE op.orderNo = (SELECT orderNo FROM ordersproduct WHERE orderItemNo = ?) "
                    + "  GROUP BY op.orderNo "
                    + ") x ON x.orderNo = o.orderNo "
                    + "SET o.deliveryState = x.aggDeliveryState, "
                    + "    o.orderUpdateDate = NOW()";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderItemNo);
            if (pstmt.executeUpdate() > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }

        return flag;
    }

    // 판매자 기준 배송 조회(권한 범위)
    public Vector<ShipmentDTO> getShipmentsByOrderNoAndVendorNo(int orderNo, int vendorNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vector<ShipmentDTO> vlist = new Vector<>();

        try {
            con = pool.getConnection();

            String sql = "SELECT op.orderItemNo, op.orderNo, op.productNo, op.snapProductName, p.vendorNo, "
                    + "s.shipmentNo, s.carrierCode, s.trackingNo, COALESCE(s.shipmentStatus, 0) AS shipmentStatus, "
                    + "s.shippedAt, s.deliveredAt, s.createdAt, s.updatedAt "
                    + "FROM ordersproduct op "
                    + "JOIN product p ON p.productNo = op.productNo "
                    + "LEFT JOIN shipments s ON s.orderItemNo = op.orderItemNo "
                    + "WHERE op.orderNo = ? AND p.vendorNo = ? "
                    + "ORDER BY op.orderItemNo ASC";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderNo);
            pstmt.setInt(2, vendorNo);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ShipmentDTO dto = new ShipmentDTO();
                dto.setOrderItemNo(rs.getInt("orderItemNo"));
                dto.setOrderNo(rs.getInt("orderNo"));
                dto.setProductNo(rs.getInt("productNo"));
                dto.setSnapProductName(rs.getString("snapProductName"));
                dto.setVendorNo(rs.getInt("vendorNo"));
                dto.setShipmentNo(rs.getInt("shipmentNo"));
                dto.setCarrierCode(rs.getString("carrierCode"));
                dto.setTrackingNo(rs.getString("trackingNo"));
                dto.setShipmentStatus(rs.getInt("shipmentStatus"));
                dto.setShippedAt(rs.getString("shippedAt"));
                dto.setDeliveredAt(rs.getString("deliveredAt"));
                dto.setCreatedAt(rs.getString("createdAt"));
                dto.setUpdatedAt(rs.getString("updatedAt"));
                vlist.addElement(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }

        return vlist;
    }
}

