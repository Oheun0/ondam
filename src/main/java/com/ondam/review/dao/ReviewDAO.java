package com.ondam.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.review.dto.ReviewDTO;
import com.ondam.review.dto.ReviewImageDTO;

public class ReviewDAO {

    private DBConnectionMgr pool;

    public ReviewDAO() {
        pool = DBConnectionMgr.getInstance();
    }

 // 1. 구매한 물건에 대해 리뷰 작성 (리턴 타입을 int로 변경하여 생성된 reviewNo 반환)
    public int insertReview(ReviewDTO dto) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        int reviewNo = 0;
        
        try {
            con = pool.getConnection();
            sql = "INSERT INTO review (orderItemNo, userNo, reviewRating, reviewContent, isBodyPublic) VALUES (?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, dto.getOrderItemNo());
            pstmt.setInt(2, dto.getUserNo());
            pstmt.setInt(3, dto.getReviewRating());
            pstmt.setString(4, dto.getReviewContent());
            pstmt.setInt(5, dto.getIsBodyPublic());
            
            int affectedRows = pstmt.executeUpdate();
            
            // 인서트 성공 시 생성된 키(reviewNo)를 가져옵니다.
            if (affectedRows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    reviewNo = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return reviewNo;
    }

    // 2. 다른 사용자들의 전체 리뷰 보기 (내 리뷰 제외, isBodyPublic 무관하게 모두 조회)
    public Vector<ReviewDTO> getOtherUsersReviews(int myUserNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<ReviewDTO> vlist = new Vector<>();
        try {
            con = pool.getConnection();
            sql = "SELECT * FROM review WHERE userNo != ? ORDER BY createdAt DESC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, myUserNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ReviewDTO dto = new ReviewDTO();
                dto.setReviewNo(rs.getInt("reviewNo"));
                dto.setOrderItemNo(rs.getInt("orderItemNo"));
                dto.setUserNo(rs.getInt("userNo"));
                dto.setReviewRating(rs.getInt("reviewRating"));
                dto.setReviewContent(rs.getString("reviewContent"));
                dto.setIsBodyPublic(rs.getInt("isBodyPublic"));
                dto.setCreatedAt(rs.getString("createdAt"));
                dto.setUpdatedAt(rs.getString("updatedAt"));
                dto.setReplyContent(rs.getString("replyContent"));
                dto.setReplyDate(rs.getString("replyDate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }

    // 3. 특정 상품에 대해 다른 사용자들의 리뷰 보기 (내 리뷰 제외, isBodyPublic 무관하게 모두 조회)
    public Vector<ReviewDTO> getOtherUsersReviewsByItem(int orderItemNo, int myUserNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<ReviewDTO> vlist = new Vector<>();
        try {
            con = pool.getConnection();
            sql = "SELECT * FROM review WHERE orderItemNo = ? AND userNo != ? ORDER BY createdAt DESC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderItemNo);
            pstmt.setInt(2, myUserNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ReviewDTO dto = new ReviewDTO();
                dto.setReviewNo(rs.getInt("reviewNo"));
                dto.setOrderItemNo(rs.getInt("orderItemNo"));
                dto.setUserNo(rs.getInt("userNo"));
                dto.setReviewRating(rs.getInt("reviewRating"));
                dto.setReviewContent(rs.getString("reviewContent"));
                dto.setIsBodyPublic(rs.getInt("isBodyPublic"));
                dto.setCreatedAt(rs.getString("createdAt"));
                dto.setUpdatedAt(rs.getString("updatedAt"));
                dto.setReplyContent(rs.getString("replyContent"));
                dto.setReplyDate(rs.getString("replyDate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }

    // 4. 내 리뷰 보기
    public Vector<ReviewDTO> getReviewsByUserNo(int userNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<ReviewDTO> vlist = new Vector<>();
        try {
            con = pool.getConnection();
            sql = "SELECT r.*, op.snapProductName, op.snapOptionSize, op.snapOptionColor, pi.imgFile as productImg " +
                    "FROM review r " +
                    "JOIN ordersproduct op ON r.orderItemNo = op.orderItemNo " +
                    "LEFT JOIN (SELECT productNo, MIN(imgFile) as imgFile FROM productimage WHERE imgOrder = 1 GROUP BY productNo) pi " +
                    "ON op.productNo = pi.productNo " +
                    "WHERE r.userNo = ? ORDER BY r.createdAt DESC";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userNo);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ReviewDTO dto = new ReviewDTO();
                dto.setReviewNo(rs.getInt("reviewNo"));
                dto.setOrderItemNo(rs.getInt("orderItemNo"));
                dto.setUserNo(rs.getInt("userNo"));
                dto.setReviewRating(rs.getInt("reviewRating"));
                dto.setReviewContent(rs.getString("reviewContent"));
                dto.setCreatedAt(rs.getString("createdAt"));
                
                dto.setSnapProductName(rs.getString("snapProductName"));
                dto.setSnapOptionSize(rs.getString("snapOptionSize"));
                dto.setSnapOptionColor(rs.getString("snapOptionColor"));
                dto.setProductImg(rs.getString("productImg")); 
                
                dto.setReplyContent(rs.getString("replyContent"));
                dto.setReplyDate(rs.getString("replyDate"));
                
                vlist.addElement(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }

    // 5. 내 리뷰 수정
    public boolean updateMyReview(ReviewDTO dto) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "UPDATE review SET reviewRating = ?, reviewContent = ?, isBodyPublic = ?, updatedAt = NOW() WHERE reviewNo = ? AND userNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, dto.getReviewRating());
            pstmt.setString(2, dto.getReviewContent());
            pstmt.setInt(3, dto.getIsBodyPublic());
            pstmt.setInt(4, dto.getReviewNo());
            pstmt.setInt(5, dto.getUserNo());
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

    // 6. 내 리뷰 삭제
    public boolean deleteMyReview(int reviewNo, int userNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "DELETE FROM review WHERE reviewNo = ? AND userNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reviewNo);
            pstmt.setInt(2, userNo);
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
    
 // 7. 작성 가능한 후기 보기
    public Vector<ReviewDTO> getWriteableReviews(int userNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<ReviewDTO> vlist = new Vector<>();
        try {
            con = pool.getConnection();
            sql = "SELECT op.orderItemNo, op.snapProductName, op.snapOptionSize, op.snapOptionColor, pi.imgFile as productImg " +
                    "FROM orders o " +
                    "JOIN ordersproduct op ON o.orderNo = op.orderNo " +
                    "LEFT JOIN review r ON op.orderItemNo = r.orderItemNo " +
                    "LEFT JOIN (SELECT productNo, MIN(imgFile) as imgFile FROM productimage WHERE imgOrder = 1 GROUP BY productNo) pi " +
                    "ON op.productNo = pi.productNo " +
                    "WHERE o.userNo = ? " +
                    "AND o.deliveryState = 3 " +
                    "AND r.reviewNo IS NULL " + 
                    "ORDER BY o.orderDate DESC";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userNo);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ReviewDTO dto = new ReviewDTO();
                dto.setOrderItemNo(rs.getInt("orderItemNo"));
                dto.setSnapProductName(rs.getString("snapProductName"));
                dto.setSnapOptionSize(rs.getString("snapOptionSize"));
                dto.setSnapOptionColor(rs.getString("snapOptionColor"));
                dto.setProductImg(rs.getString("productImg"));
                vlist.addElement(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }
    
 // 8. 수정 화면을 위해 단일 리뷰 1개 조회
    public ReviewDTO getReviewByNo(int reviewNo, int userNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ReviewDTO dto = null;
        try {
            con = pool.getConnection();
            String sql = "SELECT r.*, op.snapProductName, op.snapOptionSize, op.snapOptionColor, pi.imgFile as productImg " +
                    "FROM review r " +
                    "JOIN ordersproduct op ON r.orderItemNo = op.orderItemNo " +
                    "LEFT JOIN (SELECT productNo, MIN(imgFile) as imgFile FROM productimage WHERE imgOrder = 1 GROUP BY productNo) pi " +
                    "ON op.productNo = pi.productNo " +
                    "WHERE r.reviewNo = ? AND r.userNo = ?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reviewNo);
            pstmt.setInt(2, userNo);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                dto = new ReviewDTO();
                dto.setReviewNo(rs.getInt("reviewNo"));
                dto.setOrderItemNo(rs.getInt("orderItemNo"));
                dto.setReviewRating(rs.getInt("reviewRating"));
                dto.setReviewContent(rs.getString("reviewContent"));
                dto.setSnapProductName(rs.getString("snapProductName"));
                dto.setSnapOptionSize(rs.getString("snapOptionSize"));
                dto.setSnapOptionColor(rs.getString("snapOptionColor"));
                dto.setProductImg(rs.getString("productImg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return dto;
    }
    
 // 9. 글쓰기 화면을 위해 주문 상품 정보 1개 조회
    public ReviewDTO getOrderProductByNo(int orderItemNo, int userNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ReviewDTO dto = null;
        try {
            con = pool.getConnection();
            String sql = "SELECT op.orderItemNo, op.snapProductName, op.snapOptionSize, op.snapOptionColor, pi.imgFile as productImg " +
                         "FROM orders o " +
                         "JOIN ordersproduct op ON o.orderNo = op.orderNo " +
                         "LEFT JOIN (SELECT productNo, MIN(imgFile) as imgFile FROM productimage WHERE imgOrder = 1 GROUP BY productNo) pi " +
                         "ON op.productNo = pi.productNo " +
                         "WHERE op.orderItemNo = ? AND o.userNo = ?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderItemNo);
            pstmt.setInt(2, userNo);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                dto = new ReviewDTO();
                dto.setOrderItemNo(rs.getInt("orderItemNo"));
                dto.setSnapProductName(rs.getString("snapProductName"));
                dto.setSnapOptionSize(rs.getString("snapOptionSize"));
                dto.setSnapOptionColor(rs.getString("snapOptionColor"));
                dto.setProductImg(rs.getString("productImg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return dto;
    }
    
    public Vector<ReviewDTO> getReviewsByProductNo(int productNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vector<ReviewDTO> vlist = new Vector<>();
        try {
            con = pool.getConnection();
            String sql = "SELECT r.*, u.userName, op.snapOptionSize, op.snapOptionColor " +
                         "FROM review r " +
                         "JOIN user u ON r.userNo = u.userNo " +
                         "JOIN ordersproduct op ON r.orderItemNo = op.orderItemNo " +
                         "WHERE op.productNo = ? " +
                         "ORDER BY r.createdAt DESC";
                         
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, productNo);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ReviewDTO dto = new ReviewDTO();
                dto.setReviewNo(rs.getInt("reviewNo"));

                dto.setUserName(rs.getString("userName")); 
                dto.setReviewRating(rs.getInt("reviewRating"));
                dto.setReviewContent(rs.getString("reviewContent"));
                dto.setCreatedAt(rs.getString("createdAt"));
                dto.setSnapOptionSize(rs.getString("snapOptionSize"));
                dto.setSnapOptionColor(rs.getString("snapOptionColor"));
                dto.setReplyContent(rs.getString("replyContent"));
                dto.setReplyDate(rs.getString("replyDate"));
                vlist.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }
}