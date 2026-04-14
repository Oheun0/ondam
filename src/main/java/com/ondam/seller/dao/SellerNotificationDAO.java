package com.ondam.seller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.seller.dto.SellerNotificationDTO;

public class SellerNotificationDAO {
    private DBConnectionMgr pool;

    public SellerNotificationDAO() {
        pool = DBConnectionMgr.getInstance();
    }

    public Vector<SellerNotificationDTO> getNotifications(int vendorNo) {
        Vector<SellerNotificationDTO> vlist = new Vector<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = pool.getConnection();

            // 1. 문의 가져오기
            String inqSql = "SELECT i.inquiryNo, i.inquiryContent, i.inquiryStatus, i.answerContent, i.answeredAt, DATE_FORMAT(i.createdAt, '%Y.%m.%d') as cDate, " +
                            "p.productName, u.userName, o.orderCode " +
                            "FROM inquiry i " +
                            "JOIN product p ON i.productNo = p.productNo " +
                            "JOIN user u ON i.userNo = u.userNo " +
                            "LEFT JOIN orders o ON i.orderNo = o.orderNo " +
                            "WHERE p.vendorNo = ? ORDER BY i.createdAt DESC";
            pstmt = con.prepareStatement(inqSql);
            pstmt.setInt(1, vendorNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SellerNotificationDTO n = new SellerNotificationDTO();
                n.setId("INQ-" + rs.getInt("inquiryNo"));
                n.setKind("inquiry");
                n.setStatus(rs.getInt("inquiryStatus") == 1 ? "done" : "pending");
                n.setDate(rs.getString("cDate"));
                n.setProduct(rs.getString("productName"));
                n.setAuthor(rs.getString("userName"));
                n.setOrderNo(rs.getString("orderCode") != null ? rs.getString("orderCode") : "-");
                n.setTitle("문의가 등록되었습니다.");
                n.setBody(rs.getString("inquiryContent"));
                n.setOption("-");
                n.setAnswered(rs.getInt("inquiryStatus") == 1);
                n.setAnswer(rs.getString("answerContent") != null ? rs.getString("answerContent") : "");
                String aDate = rs.getString("answeredAt");
                n.setAnswerDate(aDate != null && aDate.length() >= 10 ? aDate.substring(0,10).replace("-", ".") : "");
                vlist.add(n);
            }
            rs.close(); pstmt.close();

            // 2. 처리 필요 주문 가져오기 (결제완료 0, 배송준비중 1)
            String ordSql = "SELECT o.orderNo, o.orderCode, DATE_FORMAT(o.orderDate, '%Y.%m.%d') as cDate, u.userName, " +
                            "o.orderType, o.paymentMethod, o.deliveryContent, op.snapProductName, op.orderQuantity " +
                            "FROM orders o " +
                            "JOIN ordersproduct op ON o.orderNo = op.orderNo " +
                            "JOIN product p ON op.productNo = p.productNo " +
                            "JOIN user u ON o.userNo = u.userNo " +
                            "WHERE p.vendorNo = ? AND o.orderState = 0 AND o.deliveryState IN (0, 1) ORDER BY o.orderDate DESC";
            pstmt = con.prepareStatement(ordSql);
            pstmt.setInt(1, vendorNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SellerNotificationDTO n = new SellerNotificationDTO();
                n.setId("ORD-" + rs.getInt("orderNo"));
                n.setKind("order");
                n.setStatus("need");
                n.setDate(rs.getString("cDate"));
                n.setOrderNo(rs.getString("orderCode"));
                n.setAuthor(rs.getString("userName"));
                
                int type = rs.getInt("orderType");
                n.setOrderType(type == 0 ? "일반" : (type == 1 ? "조르기" : "선물"));
                
                int pay = rs.getInt("paymentMethod");
                n.setPayMethod(pay == 0 ? "함께지갑 결제" : (pay == 1 ? "카드 결제" : "계좌 이체"));
                
                n.setProduct(rs.getString("snapProductName"));
                n.setQty(rs.getInt("orderQuantity"));
                n.setRequest(rs.getString("deliveryContent") != null ? rs.getString("deliveryContent") : "요청사항 없음");
                n.setTitle("새로운 주문이 들어왔어요.");
                n.setBody("배송 처리가 필요한 주문입니다.");
                vlist.add(n);
            }
            rs.close(); pstmt.close();

            // 3. 리뷰 가져오기 (💡 [수정] imgFile -> reviewImg 로 변경)
            String revSql = "SELECT r.reviewNo, DATE_FORMAT(r.createdAt, '%Y.%m.%d') as cDate, u.userName, " +
                            "r.reviewRating, r.reviewContent, op.snapProductName, " +
                            "(SELECT reviewImg FROM reviewImage ri WHERE ri.reviewNo = r.reviewNo ORDER BY imgOrder ASC LIMIT 1) as rImg " +
                            "FROM review r " +
                            "JOIN ordersproduct op ON r.orderItemNo = op.orderItemNo " +
                            "JOIN product p ON op.productNo = p.productNo " +
                            "JOIN user u ON r.userNo = u.userNo " +
                            "WHERE p.vendorNo = ? ORDER BY r.createdAt DESC";
            pstmt = con.prepareStatement(revSql);
            pstmt.setInt(1, vendorNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SellerNotificationDTO n = new SellerNotificationDTO();
                n.setId("REV-" + rs.getInt("reviewNo"));
                n.setKind("review");
                n.setStatus("pending");
                n.setDate(rs.getString("cDate"));
                n.setProduct(rs.getString("snapProductName"));
                n.setAuthor(rs.getString("userName"));
                n.setRating(rs.getInt("reviewRating"));
                n.setTitle("새로운 리뷰가 달렸어요.");
                n.setBody(rs.getString("reviewContent"));
                
                String img = rs.getString("rImg");
                n.setImage(img != null ? "/ondam/uploads/reviews/" + img : "");
                vlist.add(n);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        
        vlist.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        return vlist;
    }

    public boolean updateInquiryAnswer(int inquiryNo, String answerContent) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            String sql = "UPDATE inquiry SET answerContent = ?, inquiryStatus = 1, answeredAt = NOW() WHERE inquiryNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, answerContent);
            pstmt.setInt(2, inquiryNo);
            if (pstmt.executeUpdate() > 0) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }
}