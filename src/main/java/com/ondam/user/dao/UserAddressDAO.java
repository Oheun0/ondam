package com.ondam.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ondam.common.DBConnectionMgr;
import com.ondam.user.dto.UserAddressDTO;

public class UserAddressDAO {
	private DBConnectionMgr pool;
	public UserAddressDAO() {
		pool=DBConnectionMgr.getInstance();
	}
	public List<UserAddressDTO> getAddressListByUser(int userNo){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<UserAddressDTO> list = new ArrayList<>();
		try {
			con = pool.getConnection();
			sql = "select * from userAddress where userNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				UserAddressDTO dto = new UserAddressDTO();
				dto.setUserAddressNo(rs.getInt("userAddressNo"));
				dto.setUserNo(rs.getInt("userNo"));
				dto.setAddressName(rs.getString("addressName"));
				dto.setIsDefault(rs.getInt("isDefault"));
				dto.setReceiverName(rs.getString("receiverName"));
				dto.setReceiverTel(rs.getString("receiverTel"));
				dto.setUserAddress(rs.getString("userAddress"));
				dto.setUserDetailAddress(rs.getString("userDetailAddress"));
				dto.setUserZipcode(rs.getString("userZipcode"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return list;
	}
	
	public int insertUserAddress(Connection con, UserAddressDTO address) {
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = "insert into userAddress (userNo, addressName, isDefault, receiverName, "
                   + "receiverTel, userAddress, userDetailAddress, userZipcode) "
                   + "values (?, ?, ?, ?, ?, ?, ?, ?)";
                   
        try {
            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, address.getUserNo());
            pstmt.setString(2, address.getAddressName());
            pstmt.setInt(3, address.getIsDefault());
            pstmt.setString(4, address.getReceiverName());
            pstmt.setString(5, address.getReceiverTel());
            pstmt.setString(6, address.getUserAddress());
            pstmt.setString(7, address.getUserDetailAddress());
            pstmt.setString(8, address.getUserZipcode());
            
            result = pstmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { 
                if (pstmt != null) pstmt.close(); 
            } catch (Exception e) {}
        }
        
        return result;
    }
}
