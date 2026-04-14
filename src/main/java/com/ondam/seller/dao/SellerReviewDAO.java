package com.ondam.seller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.seller.dto.SellerReviewDTO;

public class SellerReviewDAO {
    private DBConnectionMgr pool;

    public SellerReviewDAO() {
        pool = DBConnectionMgr.getInstance();
    }

    public Vector<SellerReviewDTO> getReviewList(int vendorNo, String product, String rating, String period, String query, int startRow, int pageSize) {
        Vector<SellerReviewDTO> list = new Vector<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = pool.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT r.*, u.userName, p.productName, oi.optionColor, oi.optionSize, o.orderNo ");
            sql.append("FROM review r ");
            sql.append("JOIN user u ON r.userNo = u.userNo ");
            sql.append("JOIN orderitem oi ON r.orderItemNo = oi.orderItemNo ");
            sql.append("JOIN product p ON oi.productNo = p.productNo ");
            sql.append("JOIN orders o ON oi.orderNo = o.orderNo "); // 주문번호용
            sql.append("WHERE p.vendorNo = ? "); // 본인 상품만 조회

            // 필터링 조건 추가
            if (product != null && !product.equals("all") && !product.isEmpty()) {
                sql.append("AND p.productNo = ? ");
            }
            if (rating != null && !rating.equals("all") && !rating.isEmpty()) {
                if (rating.equals("2")) sql.append("AND r.reviewRating <= 2 ");
                else sql.append("AND r.reviewRating = ? ");
            }
            if (period != null && !period.equals("all")) {
                if (period.equals("7d")) sql.append("AND r.createdAt >= DATE_SUB(NOW(), INTERVAL 7 DAY) ");
                else if (period.equals("30d")) sql.append("AND r.createdAt >= DATE_SUB(NOW(), INTERVAL 30 DAY) ");
                else if (period.equals("3m")) sql.append("AND r.createdAt >= DATE_SUB(NOW(), INTERVAL 3 MONTH) ");
            }
            if (query != null && !query.trim().isEmpty()) {
                sql.append("AND (u.userName LIKE ? OR r.reviewContent LIKE ?) ");
            }

            sql.append("ORDER BY r.createdAt DESC LIMIT ?, ?");

            pstmt = con.prepareStatement(sql.toString());
            int idx = 1;
            pstmt.setInt(idx++, vendorNo);
            if (product != null && !product.equals("all") && !product.isEmpty()) pstmt.setString(idx++, product);
            if (rating != null && !rating.equals("all") && !rating.isEmpty() && !rating.equals("2")) pstmt.setInt(idx++, Integer.parseInt(rating));
            if (query != null && !query.trim().isEmpty()) {
                pstmt.setString(idx++, "%" + query + "%");
                pstmt.setString(idx++, "%" + query + "%");
            }
            pstmt.setInt(idx++, startRow);
            pstmt.setInt(idx++, pageSize);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                SellerReviewDTO dto = new SellerReviewDTO();
                dto.setReviewNo(rs.getInt("reviewNo"));
                dto.setOrderNo(rs.getString("orderNo"));
                dto.setAuthorName(rs.getString("userName"));
                dto.setProductName(rs.getString("productName"));
                dto.setOptionInfo(rs.getString("optionColor") + " / " + rs.getString("optionSize"));
                dto.setReviewRating(rs.getInt("reviewRating"));
                dto.setReviewContent(rs.getString("reviewContent"));
                dto.setCreatedAt(rs.getString("createdAt").substring(0, 10)); // 날짜만
                dto.setReplyContent(rs.getString("replyContent"));
                dto.setAnswered(rs.getString("replyContent") != null);
                dto.setReviewImages(getReviewImages(dto.getReviewNo()));
                
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return list;
    }

    public List<String> getReviewImages(int reviewNo) {
        List<String> images = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String sql = "SELECT reviewImg FROM reviewimage WHERE reviewNo = ? ORDER BY imgOrder ASC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reviewNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                images.add(rs.getString("reviewImg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return images;
    }
    
    public int getTotalReviewCount(int vendorNo, String product, String rating, String period, String query) {
        int total = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT COUNT(*) FROM review r ");
            sql.append("JOIN user u ON r.userNo = u.userNo ");
            sql.append("JOIN orderitem oi ON r.orderItemNo = oi.orderItemNo ");
            sql.append("JOIN product p ON oi.productNo = p.productNo ");
            sql.append("WHERE p.vendorNo = ? ");

            // 필터링 조건
            if (product != null && !product.equals("all") && !product.isEmpty()) sql.append("AND p.productNo = ? ");
            if (rating != null && !rating.equals("all") && !rating.isEmpty()) {
                if (rating.equals("2")) sql.append("AND r.reviewRating <= 2 ");
                else sql.append("AND r.reviewRating = ? ");
            }
            if (period != null && !period.equals("all")) {
                if (period.equals("7d")) sql.append("AND r.createdAt >= DATE_SUB(NOW(), INTERVAL 7 DAY) ");
                else if (period.equals("30d")) sql.append("AND r.createdAt >= DATE_SUB(NOW(), INTERVAL 30 DAY) ");
                else if (period.equals("3m")) sql.append("AND r.createdAt >= DATE_SUB(NOW(), INTERVAL 3 MONTH) ");
            }
            if (query != null && !query.trim().isEmpty()) sql.append("AND (u.userName LIKE ? OR r.reviewContent LIKE ?) ");

            pstmt = con.prepareStatement(sql.toString());
            int idx = 1;
            pstmt.setInt(idx++, vendorNo);
            if (product != null && !product.equals("all") && !product.isEmpty()) pstmt.setString(idx++, product);
            if (rating != null && !rating.equals("all") && !rating.isEmpty() && !rating.equals("2")) pstmt.setInt(idx++, Integer.parseInt(rating));
            if (query != null && !query.trim().isEmpty()) {
                pstmt.setString(idx++, "%" + query + "%");
                pstmt.setString(idx++, "%" + query + "%");
            }

            rs = pstmt.executeQuery();
            if (rs.next()) total = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return total;
    }
    
    public java.util.Map<String, Object> getReviewSummary(int vendorNo) {
        java.util.Map<String, Object> summary = new java.util.HashMap<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String sql = "SELECT COUNT(*) as totalCnt, " +
                         "IFNULL(ROUND(AVG(r.reviewRating), 1), 0) as avgRating, " +
                         "SUM(IF(r.replyContent IS NULL, 1, 0)) as noReplyCnt, " +
                         "SUM(IF(r.createdAt >= DATE_SUB(NOW(), INTERVAL 7 DAY), 1, 0)) as newThisWeek " +
                         "FROM review r JOIN orderitem oi ON r.orderItemNo = oi.orderItemNo " +
                         "JOIN product p ON oi.productNo = p.productNo " +
                         "WHERE p.vendorNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, vendorNo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                summary.put("totalCnt", rs.getInt("totalCnt"));
                summary.put("avgRating", rs.getDouble("avgRating"));
                summary.put("noReplyCnt", rs.getString("noReplyCnt") == null ? 0 : rs.getInt("noReplyCnt"));
                summary.put("newThisWeek", rs.getString("newThisWeek") == null ? 0 : rs.getInt("newThisWeek"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return summary;
    }
    
    public boolean updateReviewReply(int reviewNo, String replyContent) {
        boolean flag = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = pool.getConnection();
            String sql = "UPDATE review SET replyContent = ?, replyDate = NOW() WHERE reviewNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, replyContent);
            pstmt.setInt(2, reviewNo);
            
            if(pstmt.executeUpdate() == 1) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }
}