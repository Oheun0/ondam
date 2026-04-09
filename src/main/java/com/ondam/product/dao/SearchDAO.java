package com.ondam.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ondam.common.DBConnectionMgr;
import com.ondam.product.dto.SearchDTO;

public class SearchDAO {
	
	private DBConnectionMgr pool;
	
	public SearchDAO() {
		pool = DBConnectionMgr.getInstance();
	}
	
	// 사용자의 최근 10개 검색어 조회
	public Vector<SearchDTO> getRecentSearch(int userNo) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    Vector<SearchDTO> vlist = new Vector<SearchDTO>();
	    try {
	        con = pool.getConnection();
	        // 중복 검색어는 하나만 나오게 하고, 가장 최근 검색한 순서대로 10개 조회
	        sql = "SELECT searchKeyword, MAX(searchDate) AS latestDate " +
	              "FROM Search " +
	              "WHERE userNo = ? " +
	              "GROUP BY searchKeyword " +
	              "ORDER BY latestDate DESC " +
	              "LIMIT 10";
	              
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, userNo);
	        rs = pstmt.executeQuery();
	        
	        while(rs.next()) {
	            SearchDTO dto = new SearchDTO();
	            // SELECT 절에 맞게 필요한 데이터만 세팅 (searchNo는 그룹화하면 특정하기 어려우므로 제외)
	            dto.setSearchKeyword(rs.getString("searchKeyword"));
	            dto.setSearchDate(rs.getString("latestDate"));
	            
	            vlist.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return vlist;
	}
	
	// 최근 7일간 가장 많이 검색된 검색어 10개
	public Vector<SearchDTO> getPopularSearch() {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Vector<SearchDTO> vlist = new Vector<SearchDTO>();
	    try {
	        con = pool.getConnection();
	        
	        // 횟수(cnt) 기준으로 내림차순 정렬하여 키워드만 추출
	        String sql = "SELECT searchKeyword " +
	                     "FROM Search " +
	                     "WHERE searchDate >= DATE_SUB(NOW(), INTERVAL 7 DAY) " +
	                     "GROUP BY searchKeyword " +
	                     "ORDER BY COUNT(DISTINCT userNo) DESC " +
	                     "LIMIT 10";
	              
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        
	        while(rs.next()) {
	            SearchDTO dto = new SearchDTO();
	            dto.setSearchKeyword(rs.getString("searchKeyword"));
	            vlist.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return vlist;
	}
	
	// 검색어 저장
	public boolean insertSearch(SearchDTO dto) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    String sql = null;
	    boolean flag = false;
	    try {
	        con = pool.getConnection();
	        sql = "INSERT INTO Search (userNo, searchKeyword, searchDate) VALUES (?, ?, ?)";
	        pstmt = con.prepareStatement(sql);
	        
	        pstmt.setInt(1, dto.getUserNo());
	        pstmt.setString(2, dto.getSearchKeyword());
	        pstmt.setString(3, dto.getSearchDate());
	        
	        if (pstmt.executeUpdate() == 1) {
	            flag = true;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt);
	    }
	    return flag;
	}
	
	// 최근 검색어 삭제
	public boolean deleteSearch(int userNo, String keyword) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    boolean flag = false;
	    try {
	        con = pool.getConnection();
	        String sql = "DELETE FROM Search WHERE userNo = ? AND searchKeyword = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, userNo);
	        pstmt.setString(2, keyword);
	        if (pstmt.executeUpdate() > 0) flag = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt);
	    }
	    return flag;
	}
}