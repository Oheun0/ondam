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

    public Vector<SellerOrderListDTO> getSellerOrderList(int vendorNo) {
        Map<Integer, SellerOrderListDTO> map = new LinkedHashMap<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = pool.getConnection();
            
            // Orders, OrdersProduct, Product 3개 테이블 조인 + 썸네일 서브쿼리
            String sql = "SELECT o.orderNo, o.orderDate, o.paymentMethod, o.orderType, o.deliveryContent, o.deliveryState, "
                       + "op.snapProductName, op.orderQuantity, op.snapProductPrice, "
                       + "(SELECT imgFile FROM productimage pi WHERE pi.productNo = op.productNo ORDER BY pi.imgOrder ASC LIMIT 1) AS productImage "
                       + "FROM orders o "
                       + "JOIN ordersProduct op ON o.orderNo = op.orderNo "
                       + "JOIN product p ON op.productNo = p.productNo "
                       + "WHERE p.vendorNo = ? "
                       + "ORDER BY o.orderDate DESC, o.orderNo DESC, op.orderItemNo ASC";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, vendorNo);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                int orderNo = rs.getInt("orderNo");
                SellerOrderListDTO dto = map.get(orderNo);

                // 1. 이 주문번호가 Map에 없으면 새 DTO를 생성해서 넣습니다. (대표 상품 세팅)
                if (dto == null) {
                    dto = new SellerOrderListDTO();
                    dto.setOrderNo(orderNo);
                    dto.setOrderDate(rs.getString("orderDate").substring(0, 10)); // 날짜 짧게
                    dto.setPaymentMethod(rs.getInt("paymentMethod"));
                    dto.setOrderType(rs.getInt("orderType"));
                    dto.setDeliveryContent(rs.getString("deliveryContent"));
                    dto.setDeliveryState(rs.getInt("deliveryState"));
                    
                    dto.setRepProductName(rs.getString("snapProductName"));
                    dto.setRepProductImage(rs.getString("productImage"));
                    dto.setTotalQuantity(rs.getInt("orderQuantity"));
                    dto.setTotalPrice(rs.getInt("snapProductPrice") * rs.getInt("orderQuantity"));
                    dto.setSubItemCount(0); // 처음엔 외 0건
                    
                    map.put(orderNo, dto);
                } 
                // 2. 이미 같은 주문번호가 Map에 있으면 수량/금액을 누적하고 서브 아이템 카운트를 1 올립니다.
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

        // 3. Map에 담긴 데이터를 Vector로 옮기면서 "외 N건" 텍스트를 완성합니다.
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
                       + "op.orderQuantity, op.snapProductPrice, "
                       + "(SELECT imgFile FROM product_image pi WHERE pi.productNo = op.productNo ORDER BY pi.imgOrder ASC LIMIT 1) AS productImage "
                       + "FROM orders o "
                       + "JOIN orders_product op ON o.orderNo = op.orderNo "
                       + "JOIN product p ON op.productNo = p.productNo "
                       + "WHERE p.vendorNo = ? AND o.orderNo = ?";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, vendorNo);
            pstmt.setInt(2, orderNo);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                // 첫 번째 행에서 부모 정보 세팅
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
                
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return detail;
    }
}