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
        int[] summary = new int[4];
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String sql = "SELECT SUM(totalAmount), SUM(refundAmount), SUM(commissionFee), SUM(actualAmount) "
                       + "FROM settlement "
                       + "WHERE vendorNo = ? AND settleState = 1 AND settleDate LIKE ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, vendorNo);
            pstmt.setString(2, yearMonth + "%"); 
            rs = pstmt.executeQuery();
            if (rs.next()) {
                summary[0] = rs.getInt(1); summary[1] = rs.getInt(2);
                summary[2] = rs.getInt(3); summary[3] = rs.getInt(4);
            }
        } catch (Exception e) { e.printStackTrace();
        } finally { pool.freeConnection(con, pstmt, rs); }
        return summary;
    }

    public Vector<SellerSettlementDTO> getSettlementList(int vendorNo, String startDate, String endDate, String settleStatus) {
        Vector<SellerSettlementDTO> list = new Vector<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = pool.getConnection();

            StringBuilder sql = new StringBuilder(
                "SELECT settlementNo, settleDate, totalAmount, refundAmount, "
              + "commissionFee, cardAmount, bankAmount, walletAmount, "
              + "actualAmount, settleState, createdAt "
              + "FROM settlement "
              + "WHERE vendorNo = ? "
            );

            if (startDate != null && !startDate.isEmpty()) {
                sql.append(" AND settleDate >= ? ");
            }
            if (endDate != null && !endDate.isEmpty()) {
                sql.append(" AND settleDate <= ? ");
            }
            if (settleStatus != null && !settleStatus.isEmpty() && !settleStatus.equals("all")) {
                int state = settleStatus.equals("done") ? 1 : 0;
                sql.append(" AND settleState = ").append(state);
            }

            sql.append(" ORDER BY settleDate DESC, settlementNo DESC");

            pstmt = con.prepareStatement(sql.toString());
            int idx = 1;
            pstmt.setInt(idx++, vendorNo);
            if (startDate != null && !startDate.isEmpty()) {
                pstmt.setString(idx++, startDate);
            }
            if (endDate != null && !endDate.isEmpty()) {
                pstmt.setString(idx++, endDate);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                SellerSettlementDTO dto = new SellerSettlementDTO();
                dto.setSettlementNo(rs.getInt("settlementNo"));

                String sDate = rs.getString("settleDate");
                if(sDate != null && sDate.length() > 10) sDate = sDate.substring(0, 10);
                dto.setSettleDate(sDate);
                
                dto.setTotalAmount(rs.getInt("totalAmount"));
                dto.setRefundAmount(rs.getInt("refundAmount"));
                dto.setCommissionFee(rs.getInt("commissionFee"));
                dto.setCardAmount(rs.getInt("cardAmount"));
                dto.setBankAmount(rs.getInt("bankAmount"));
                dto.setWalletAmount(rs.getInt("walletAmount"));
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
    
    public int getTotalCount(int vendorNo, String start, String end, String status) {
        int total = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM settlement WHERE vendorNo = ?");

            if (start != null && !start.isEmpty()) sql.append(" AND settleDate >= ? ");
            if (end != null && !end.isEmpty()) sql.append(" AND settleDate <= ? ");
            if (status != null && !status.isEmpty() && !status.equals("all")) {
                if (status.equals("done")) sql.append(" AND settleState = 1");
                else if (status.equals("pending")) sql.append(" AND settleState = 0");
                else if (status.equals("refund") || status.equals("cancel")) sql.append(" AND refundAmount > 0");
            }

            pstmt = con.prepareStatement(sql.toString());
            int idx = 1;
            pstmt.setInt(idx++, vendorNo);
            if (start != null && !start.isEmpty()) pstmt.setString(idx++, start);
            if (end != null && !end.isEmpty()) pstmt.setString(idx++, end);

            rs = pstmt.executeQuery();
            if (rs.next()) total = rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt, rs); }
        return total;
    }

    public Vector<SellerSettlementDTO> getSettlementList(int vendorNo, String startDate, String endDate, String settleStatus, int currentPage, int pageSize) {
        Vector<SellerSettlementDTO> list = new Vector<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = pool.getConnection();
            StringBuilder sql = new StringBuilder(
                "SELECT settlementNo, settleDate, totalAmount, refundAmount, "
              + "commissionFee, cardAmount, bankAmount, walletAmount, "
              + "actualAmount, settleState, createdAt FROM settlement WHERE vendorNo = ? "
            );
            if (startDate != null && !startDate.isEmpty()) sql.append(" AND settleDate >= ? ");
            if (endDate != null && !endDate.isEmpty()) sql.append(" AND settleDate <= ? ");
            if (settleStatus != null && !settleStatus.isEmpty() && !settleStatus.equals("all")) {
                if (settleStatus.equals("done")) sql.append(" AND settleState = 1");
                else if (settleStatus.equals("pending")) sql.append(" AND settleState = 0");
                else if (settleStatus.equals("refund") || settleStatus.equals("cancel")) sql.append(" AND refundAmount > 0");
            }

            sql.append(" ORDER BY settleDate DESC, settlementNo DESC ");
            sql.append(" LIMIT ?, ? ");

            pstmt = con.prepareStatement(sql.toString());
            int idx = 1;
            pstmt.setInt(idx++, vendorNo);
            if (startDate != null && !startDate.isEmpty()) pstmt.setString(idx++, startDate);
            if (endDate != null && !endDate.isEmpty()) pstmt.setString(idx++, endDate);

            int startRow = (currentPage - 1) * pageSize;
            pstmt.setInt(idx++, startRow);
            pstmt.setInt(idx++, pageSize);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                SellerSettlementDTO dto = new SellerSettlementDTO();
                dto.setSettlementNo(rs.getInt("settlementNo"));
                String sDate = rs.getString("settleDate");
                if(sDate != null && sDate.length() > 10) sDate = sDate.substring(0, 10);
                dto.setSettleDate(sDate);
                dto.setTotalAmount(rs.getInt("totalAmount"));
                dto.setRefundAmount(rs.getInt("refundAmount"));
                dto.setCommissionFee(rs.getInt("commissionFee"));
                dto.setCardAmount(rs.getInt("cardAmount"));
                dto.setBankAmount(rs.getInt("bankAmount"));
                dto.setWalletAmount(rs.getInt("walletAmount"));
                dto.setActualAmount(rs.getInt("actualAmount"));
                dto.setSettleState(rs.getInt("settleState"));
                String cDate = rs.getString("createdAt");
                if(cDate != null && cDate.length() > 16) cDate = cDate.substring(0, 16);
                dto.setCreatedAt(cDate);
                list.add(dto);
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { pool.freeConnection(con, pstmt, rs); }
        return list;
    }
}