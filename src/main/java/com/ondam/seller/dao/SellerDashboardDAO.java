package com.ondam.seller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.ondam.common.DBConnectionMgr;

public class SellerDashboardDAO {
    private DBConnectionMgr pool;

    public SellerDashboardDAO() {
        pool = DBConnectionMgr.getInstance();
    }

    public Map<String, Object> getDashboardStats(int vendorNo) {
        Map<String, Object> stats = new HashMap<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = pool.getConnection();

            // 1. 오늘 주문 (결제완료/배송준비 등)
            String sql1 = "SELECT COUNT(DISTINCT o.orderNo) FROM orders o JOIN ordersproduct op ON o.orderNo = op.orderNo JOIN product p ON op.productNo = p.productNo WHERE p.vendorNo = ? AND DATE(o.orderDate) = CURDATE()";
            pstmt = con.prepareStatement(sql1);
            pstmt.setInt(1, vendorNo);
            rs = pstmt.executeQuery();
            stats.put("todayOrderCount", rs.next() ? rs.getInt(1) : 0);
            rs.close(); pstmt.close();

            // 2. 배송 준비 (deliveryState = 1)
            String sql2 = "SELECT COUNT(DISTINCT o.orderNo) FROM orders o JOIN ordersproduct op ON o.orderNo = op.orderNo JOIN product p ON op.productNo = p.productNo WHERE p.vendorNo = ? AND o.deliveryState = 1";
            pstmt = con.prepareStatement(sql2);
            pstmt.setInt(1, vendorNo);
            rs = pstmt.executeQuery();
            stats.put("shipReadyCount", rs.next() ? rs.getInt(1) : 0);
            rs.close(); pstmt.close();

            // 3. 문의 (inquiryStatus = 0)
            String sql3 = "SELECT COUNT(i.inquiryNo) FROM inquiry i JOIN product p ON i.productNo = p.productNo WHERE p.vendorNo = ? AND i.inquiryStatus = 0";
            pstmt = con.prepareStatement(sql3);
            pstmt.setInt(1, vendorNo);
            rs = pstmt.executeQuery();
            stats.put("inquiryCount", rs.next() ? rs.getInt(1) : 0);
            rs.close(); pstmt.close();

            // 4. 신규 리뷰 (오늘 작성된 리뷰)
            String sql4 = "SELECT COUNT(r.reviewNo) FROM review r JOIN ordersproduct op ON r.orderItemNo = op.orderItemNo JOIN product p ON op.productNo = p.productNo WHERE p.vendorNo = ? AND DATE(r.createdAt) = CURDATE()";
            pstmt = con.prepareStatement(sql4);
            pstmt.setInt(1, vendorNo);
            rs = pstmt.executeQuery();
            stats.put("reviewCount", rs.next() ? rs.getInt(1) : 0);
            rs.close(); pstmt.close();

            // 5. 공개 중인 쇼츠 (shortsState = 1)
            String sql5 = "SELECT COUNT(*) FROM shorts WHERE vendorNo = ? AND shortsState = 1";
            pstmt = con.prepareStatement(sql5);
            pstmt.setInt(1, vendorNo);
            rs = pstmt.executeQuery();
            stats.put("activeShortsCount", rs.next() ? rs.getInt(1) : 0);
            rs.close(); pstmt.close();

            // 6. 품절 임박 상품 (재고 5개 이하)
            String sql6 = "SELECT COUNT(DISTINCT p.productNo) FROM product p JOIN productoption po ON p.productNo = po.productNo WHERE p.vendorNo = ? AND p.productState = 1 AND po.optionStock <= 5";
            pstmt = con.prepareStatement(sql6);
            pstmt.setInt(1, vendorNo);
            rs = pstmt.executeQuery();
            stats.put("lowStockCount", rs.next() ? rs.getInt(1) : 0);
            rs.close(); pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return stats;
    }
}