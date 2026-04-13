package com.ondam.seller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.seller.dto.SellerOrderDetailDTO;
import com.ondam.seller.dto.SellerOrderListDTO;

public class SellerOrderDAO {
    private DBConnectionMgr pool;

    public SellerOrderDAO() {
        pool = DBConnectionMgr.getInstance();
    }

    //vendorNo 전체 주문 개수 가져오기
    public int getTotalOrderCount(int vendorNo) {
        int count = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = pool.getConnection();
            String sql = "SELECT COUNT(DISTINCT o.orderNo) FROM orders o "
                       + "JOIN ordersproduct op ON o.orderNo = op.orderNo "
                       + "JOIN product p ON op.productNo = p.productNo "
                       + "WHERE p.vendorNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, vendorNo);
            rs = pstmt.executeQuery();
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return count;
    }
    
 // 특정 배송 상태(배송중, 취소 등)의 전체 개수 가져오기
    public int getOrderCountByState(int vendorNo, int deliveryState) {
        int count = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String sql = "SELECT COUNT(DISTINCT o.orderNo) FROM orders o "
                       + "JOIN ordersproduct op ON o.orderNo = op.orderNo "
                       + "JOIN product p ON op.productNo = p.productNo "
                       + "WHERE p.vendorNo = ? AND o.deliveryState = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, vendorNo);
            pstmt.setInt(2, deliveryState);
            rs = pstmt.executeQuery();
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return count;
    }
    
    public Vector<SellerOrderListDTO> getSellerOrderList(int vendorNo, int start, int count) {
        Map<Integer, SellerOrderListDTO> map = new LinkedHashMap<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = pool.getConnection();
            String sql = "SELECT o.orderNo, o.orderDate, o.paymentMethod, o.orderType, o.deliveryContent, o.deliveryState, "
                    + "op.snapProductName, op.orderQuantity, op.snapProductPrice, "
                    + "(SELECT imgFile FROM productimage pi WHERE pi.productNo = op.productNo ORDER BY pi.imgOrder ASC LIMIT 1) AS productImage "
                    + "FROM orders o "
                    + "JOIN ordersproduct op ON o.orderNo = op.orderNo "
                    + "JOIN product p ON op.productNo = p.productNo "
                    + "WHERE p.vendorNo = ? "
                    + "AND o.orderNo IN ( "
                    + "    SELECT orderNo FROM ( "
                    + "        SELECT o2.orderNo FROM orders o2 "
                    + "        JOIN ordersproduct op2 ON o2.orderNo = op2.orderNo "
                    + "        JOIN product p2 ON op2.productNo = p2.productNo "
                    + "        WHERE p2.vendorNo = ? "
                    + "        GROUP BY o2.orderNo "
                    + "        ORDER BY o2.orderDate DESC, o2.orderNo DESC "
                    + "        LIMIT ?, ?) AS tmp) "
                    + "ORDER BY o.orderDate DESC, o.orderNo DESC, op.orderItemNo ASC";

         pstmt = con.prepareStatement(sql);
         pstmt.setInt(1, vendorNo);
         pstmt.setInt(2, vendorNo);
         pstmt.setInt(3, start);
         pstmt.setInt(4, count);
         rs = pstmt.executeQuery();

            while(rs.next()) {
                int orderNo = rs.getInt("orderNo");
                SellerOrderListDTO dto = map.get(orderNo);

                if (dto == null) {
                    dto = new SellerOrderListDTO();
                    dto.setOrderNo(orderNo);
                    dto.setOrderDate(rs.getString("orderDate").substring(0, 10));
                    dto.setPaymentMethod(rs.getInt("paymentMethod"));
                    dto.setOrderType(rs.getInt("orderType"));
                    dto.setDeliveryContent(rs.getString("deliveryContent"));
                    dto.setDeliveryState(rs.getInt("deliveryState"));
                    
                    dto.setRepProductName(rs.getString("snapProductName"));
                    dto.setRepProductImage(rs.getString("productImage"));
                    dto.setTotalQuantity(rs.getInt("orderQuantity"));
                    dto.setTotalPrice(rs.getInt("snapProductPrice") * rs.getInt("orderQuantity"));
                    dto.setSubItemCount(0);
                    
                    map.put(orderNo, dto);
                } 
                else {
                    dto.setTotalQuantity(dto.getTotalQuantity() + rs.getInt("orderQuantity"));
                    dto.setTotalPrice(dto.getTotalPrice() + (rs.getInt("snapProductPrice") * rs.getInt("orderQuantity")));
                    dto.setSubItemCount(dto.getSubItemCount() + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        Vector<SellerOrderListDTO> resultList = new Vector<>();
        for (SellerOrderListDTO dto : map.values()) {
            if (dto.getSubItemCount() > 0) {
                dto.setRepProductName(dto.getRepProductName() + " 외 " + dto.getSubItemCount() + "건");
            }
            resultList.add(dto);
        }

        return resultList;
    }
    
 // 주문 상세 내역 가져오기
    public SellerOrderDetailDTO getSellerOrderDetail(int vendorNo, int orderNo) {
        SellerOrderDetailDTO detail = null;
        java.util.List<com.ondam.seller.dto.SellerOrderItemDTO> itemList = new java.util.ArrayList<>();
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = pool.getConnection();
            String sql = "SELECT o.orderNo, o.orderDate, o.deliveryState, o.orderType, o.paymentMethod, "
                    + "o.receiverName, o.receiverTel, o.deliveryAddr, o.deliveryContent, "
                    + "op.orderItemNo, op.snapProductName, op.snapOptionSize, op.snapOptionColor, "
                    + "op.orderQuantity, op.snapProductPrice, op.courier, op.trackingNo, "
                    + "op.deliveryState AS itemDeliveryState, "
                    + "(SELECT imgFile FROM productimage pi WHERE pi.productNo = op.productNo ORDER BY pi.imgOrder ASC LIMIT 1) AS productImage "
                    + "FROM orders o "
                    + "JOIN ordersproduct op ON o.orderNo = op.orderNo "
                    + "JOIN product p ON op.productNo = p.productNo "
                    + "WHERE p.vendorNo = ? AND o.orderNo = ?";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, vendorNo);
            pstmt.setInt(2, orderNo);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                if (detail == null) {
                    detail = new SellerOrderDetailDTO();
                    detail.setOrderNo(rs.getInt("orderNo"));
                    detail.setOrderDate(rs.getString("orderDate"));
                    detail.setDeliveryState(rs.getInt("deliveryState"));
                    detail.setOrderType(rs.getInt("orderType"));
                    detail.setPaymentMethod(rs.getInt("paymentMethod"));
                    detail.setReceiverName(rs.getString("receiverName"));
                    detail.setReceiverTel(rs.getString("receiverTel"));
                    detail.setDeliveryAddr(rs.getString("deliveryAddr"));
                    detail.setDeliveryContent(rs.getString("deliveryContent"));
                    detail.setCourier(rs.getString("courier"));
                    detail.setTrackingNo(rs.getString("trackingNo"));
                    detail.setItemList(itemList);
                }

                // 자식(상품) 정보 세팅 및 리스트에 추가
                com.ondam.seller.dto.SellerOrderItemDTO item = new com.ondam.seller.dto.SellerOrderItemDTO();
                item.setOrderItemNo(rs.getInt("orderItemNo"));
                item.setProductName(rs.getString("snapProductName"));
                item.setOptionSize(rs.getString("snapOptionSize"));
                item.setOptionColor(rs.getString("snapOptionColor"));
                item.setQuantity(rs.getInt("orderQuantity"));
                item.setPrice(rs.getInt("snapProductPrice"));
                item.setProductImage(rs.getString("productImage"));
                item.setDeliveryState(rs.getInt("itemDeliveryState"));
                
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return detail;
    }
    
    //배송 상태 변경하기 (Update)
    public boolean updateDeliveryState(int vendorNo, int orderNo, int newState) {
        boolean result = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = pool.getConnection();
            
            // 핵심: 특정 주문의 deliveryState를 바꾸되, 해당 상품이 내 업체(vendorNo)의 것인지 확인!
            String sql = "UPDATE orders o "
                       + "JOIN ordersproduct op ON o.orderNo = op.orderNo "
                       + "JOIN product p ON op.productNo = p.productNo "
                       + "SET o.deliveryState = ?, op.deliveryState = ? "
                       + "WHERE p.vendorNo = ? AND o.orderNo = ?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, newState);  // 부모(orders) 테이블 상태 변경
            pstmt.setInt(2, newState);  // 자식(ordersproduct) 테이블 상태 변경
            pstmt.setInt(3, vendorNo);  // 내 업체가 맞는지 확인
            pstmt.setInt(4, orderNo);   // 바꿀 주문 번호
            
            int count = pstmt.executeUpdate();
            if (count > 0) {
                result = true; // 성공하면 true 반환!
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return result;
    }
    
 // 송장 번호 저장
    public boolean updateInvoice(int vendorNo, int orderNo, String carrier, String trackingNo) {
        boolean result = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = pool.getConnection();
            String sql = "UPDATE ordersproduct op "
                       + "JOIN product p ON op.productNo = p.productNo "
                       + "SET op.courier = ?, op.trackingNo = ? "
                       + "WHERE p.vendorNo = ? AND op.orderNo = ?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, carrier);
            pstmt.setString(2, trackingNo);
            pstmt.setInt(3, vendorNo);
            pstmt.setInt(4, orderNo);
            
            int count = pstmt.executeUpdate();
            if (count > 0) result = true;
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return result;
    }
    
 // 개별 상품 상태 변경
    public boolean updateItemDeliveryState(int vendorNo, int orderNo, int newState, String itemNosStr) {
        boolean result = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = pool.getConnection();
            String[] itemNosArray = itemNosStr.split(",");

            StringBuilder placeholders = new StringBuilder();
            for (int i = 0; i < itemNosArray.length; i++) {
                placeholders.append("?");
                if (i < itemNosArray.length - 1) placeholders.append(",");
            }
            String sql = "UPDATE orders o "
                    + "JOIN ordersproduct op ON o.orderNo = op.orderNo "
                    + "JOIN product p ON op.productNo = p.productNo "
                    + "SET op.deliveryState = ?, o.deliveryState = ? " 
                    + "WHERE p.vendorNo = ? AND op.orderNo = ? AND op.orderItemNo IN (" + placeholders.toString() + ")";
         
         pstmt = con.prepareStatement(sql);
            
         pstmt.setInt(1, newState);
         pstmt.setInt(2, newState);
         pstmt.setInt(3, vendorNo);
         pstmt.setInt(4, orderNo);
         for (int i = 0; i < itemNosArray.length; i++) {
             pstmt.setInt(5 + i, Integer.parseInt(itemNosArray[i].trim()));
         }
         
         	int count = pstmt.executeUpdate();        
            if (count > 0) result = true;
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return result;
    }
    
 //개별/일괄 상품 송장 정보 업데이트
    public boolean updateItemInvoice(int vendorNo, int orderNo, String carrier, String trackingNo, String itemNosStr) {
        boolean result = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = pool.getConnection();
            String[] itemNosArray = itemNosStr.split(",");

            StringBuilder placeholders = new StringBuilder();
            for (int i = 0; i < itemNosArray.length; i++) {
                placeholders.append("?");
                if (i < itemNosArray.length - 1) placeholders.append(",");
            }
            String sql = "UPDATE ordersproduct op "
                       + "JOIN product p ON op.productNo = p.productNo "
                       + "SET op.courier = ?, op.trackingNo = ? "
                       + "WHERE p.vendorNo = ? AND op.orderNo = ? AND op.orderItemNo IN (" + placeholders.toString() + ")";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, carrier);
            pstmt.setString(2, trackingNo);
            pstmt.setInt(3, vendorNo);
            pstmt.setInt(4, orderNo);
            
            for (int i = 0; i < itemNosArray.length; i++) {
                pstmt.setInt(5 + i, Integer.parseInt(itemNosArray[i].trim()));
            }
            
            int count = pstmt.executeUpdate();
            if (count > 0) result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return result;
    }
}