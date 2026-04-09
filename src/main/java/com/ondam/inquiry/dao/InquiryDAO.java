package com.ondam.inquiry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.inquiry.dto.InquiryDTO;

public class InquiryDAO {
    
    private DBConnectionMgr pool;

    public InquiryDAO() {
        try {
            pool = DBConnectionMgr.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1. 내 문의 내역 가져오기
    public Vector<InquiryDTO> getMyInquiries(int userNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vector<InquiryDTO> vlist = new Vector<>();

        try {
            con = pool.getConnection();
            String sql = "SELECT i.*, p.productBrand, p.productName, "
                       + "(SELECT imgFile FROM productimage pi WHERE pi.productNo = p.productNo ORDER BY imgOrder ASC LIMIT 1) AS productImage "
                       + "FROM inquiry i "
                       + "JOIN product p ON i.productNo = p.productNo "
                       + "WHERE i.userNo = ? "
                       + "ORDER BY i.createdAt DESC";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userNo);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                InquiryDTO dto = new InquiryDTO();
                dto.setInquiryNo(rs.getInt("inquiryNo"));
                dto.setProductNo(rs.getInt("productNo"));
                dto.setUserNo(rs.getInt("userNo"));
                dto.setOrderNo(rs.getInt("orderNo"));
                dto.setInquiryContent(rs.getString("inquiryContent"));
                dto.setInquiryStatus(rs.getInt("inquiryStatus"));
                dto.setAnswerContent(rs.getString("answerContent"));
                dto.setIsSecret(rs.getInt("isSecret"));
                dto.setIsNameHidden(rs.getInt("isNameHidden"));

                String createdAt = rs.getString("createdAt");
                if(createdAt != null && createdAt.length() > 10) {
                    dto.setCreatedAt(createdAt.substring(0, 10)); 
                }
                String answeredAt = rs.getString("answeredAt");
                if(answeredAt != null && answeredAt.length() > 10) {
                    dto.setAnsweredAt(answeredAt.substring(0, 10));
                }
                dto.setProductBrand(rs.getString("productBrand"));
                dto.setProductName(rs.getString("productName"));
                dto.setProductImage(rs.getString("productImage"));
                vlist.addElement(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }
    
    // 2. 문의 등록
    public boolean insertInquiry(InquiryDTO dto) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean flag = false;

        try {
            con = pool.getConnection();
            String sql = "INSERT INTO inquiry (productNo, userNo, inquiryContent, isSecret, isNameHidden, orderNo) "
                       + "VALUES (?, ?, ?, ?, ?, ?)";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, dto.getProductNo());
            pstmt.setInt(2, dto.getUserNo());
            pstmt.setString(3, dto.getInquiryContent());
            pstmt.setInt(4, dto.getIsSecret());
            pstmt.setInt(5, dto.getIsNameHidden());
            
            if (dto.getOrderNo() == 0) {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(6, dto.getOrderNo());
            }

            int count = pstmt.executeUpdate();
            if (count > 0) flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }
    
    // 3. 문의 수정
    public boolean updateInquiry(InquiryDTO dto) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            // ⭐️ SQL에 isNameHidden 업데이트 추가
            String sql = "UPDATE inquiry SET inquiryContent = ?, isSecret = ?, isNameHidden = ? "
                       + "WHERE inquiryNo = ? AND inquiryStatus = 0";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dto.getInquiryContent());
            pstmt.setInt(2, dto.getIsSecret());
            pstmt.setInt(3, dto.getIsNameHidden());
            pstmt.setInt(4, dto.getInquiryNo());
            
            if (pstmt.executeUpdate() > 0) flag = true;
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt); }
        return flag;
    }

    // 4. 수정할 데이터 가져오기
    public InquiryDTO getInquiryDetail(int inquiryNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        InquiryDTO dto = new InquiryDTO();
        try {
            con = pool.getConnection();
            String sql = "SELECT i.*, p.productBrand, p.productName, "
                       + "(SELECT imgFile FROM productimage pi WHERE pi.productNo = p.productNo ORDER BY imgOrder ASC LIMIT 1) AS productImage "
                       + "FROM inquiry i JOIN product p ON i.productNo = p.productNo "
                       + "WHERE i.inquiryNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, inquiryNo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto.setInquiryNo(rs.getInt("inquiryNo"));
                dto.setProductNo(rs.getInt("productNo"));
                dto.setInquiryContent(rs.getString("inquiryContent"));
                dto.setIsSecret(rs.getInt("isSecret"));
                dto.setIsNameHidden(rs.getInt("isNameHidden"));
                dto.setProductBrand(rs.getString("productBrand"));
                dto.setProductName(rs.getString("productName"));
                dto.setProductImage(rs.getString("productImage"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt, rs); }
        return dto;
    }

    // 5. 삭제
    public boolean deleteInquiry(int inquiryNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            String sql = "DELETE FROM inquiry WHERE inquiryNo = ? AND inquiryStatus = 0";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, inquiryNo);
            if (pstmt.executeUpdate() > 0) flag = true;
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt); }
        return flag;
    }

    // 6. 상품 정보 가져오기
    public InquiryDTO getProductInfoForInquiry(int productNo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        InquiryDTO dto = new InquiryDTO();
        try {
            con = pool.getConnection();
            String sql = "SELECT p.productNo, p.productBrand, p.productName, "
                       + "(SELECT imgFile FROM productimage pi WHERE pi.productNo = p.productNo ORDER BY imgOrder ASC LIMIT 1) AS productImage "
                       + "FROM product p WHERE p.productNo = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, productNo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto.setProductNo(rs.getInt("productNo"));
                dto.setProductBrand(rs.getString("productBrand"));
                dto.setProductName(rs.getString("productName"));
                dto.setProductImage(rs.getString("productImage"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt, rs); }
        return dto;
    }
}