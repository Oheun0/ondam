package com.ondam.seller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.seller.dto.SellerSettlementDTO;

public class SellerSettlementDAO {
    private DBConnectionMgr pool;

    public SellerSettlementDAO() {
        pool = DBConnectionMgr.getInstance();
    }
    public int[] getMonthlySettlementSummary(int vendorNo, String yearMonth) {
        int[] summary = new int[3]; 
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = pool.getConnection();
            String sql = "SELECT SUM(totalAmount), SUM(commissionFee), SUM(actualAmount) "
                       + "FROM settlement "
                       + "WHERE vendorNo = ? AND settleState = 1 AND settleDate LIKE ?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, vendorNo);
            pstmt.setString(2, yearMonth + "%"); 
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
                summary[0] = rs.getInt(1);
                summary[1] = rs.getInt(2);
                summary[2] = rs.getInt(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return summary;
    }

    public Vector<SellerSettlementDTO> getSettlementList(int vendorNo) {
        Vector<SellerSettlementDTO> list = new Vector<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = pool.getConnection();
            String sql = "SELECT settlementNo, settleDate, totalAmount, commissionFee, "
                       + "actualAmount, settleState, createdAt "
                       + "FROM settlement "
                       + "WHERE vendorNo = ? "
                       + "ORDER BY settleDate DESC, settlementNo DESC";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, vendorNo);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                SellerSettlementDTO dto = new SellerSettlementDTO();
                dto.setSettlementNo(rs.getInt("settlementNo"));

                String sDate = rs.getString("settleDate");
                if(sDate != null && sDate.length() > 10) sDate = sDate.substring(0, 10);
                dto.setSettleDate(sDate);
                
                dto.setTotalAmount(rs.getInt("totalAmount"));
                dto.setCommissionFee(rs.getInt("commissionFee"));
                dto.setActualAmount(rs.getInt("actualAmount"));
                dto.setSettleState(rs.getInt("settleState"));

                String cDate = rs.getString("createdAt");
                if(cDate != null && cDate.length() > 16) cDate = cDate.substring(0, 16);
                dto.setCreatedAt(cDate);
                
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return list;
    }
}