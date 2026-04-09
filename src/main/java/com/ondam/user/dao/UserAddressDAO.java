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
			sql = "select * from userAddress where userNo = ? order by isDefault desc";
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
	
	//배송지 1개의 정보만 가져오기
	public UserAddressDTO getAddressByNo(int userAddressNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserAddressDTO dto = null;
		
		try {
			con = pool.getConnection();
			String sql = "SELECT * FROM userAddress WHERE userAddressNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userAddressNo);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new UserAddressDTO();
				dto.setUserAddressNo(rs.getInt("userAddressNo"));
				dto.setUserNo(rs.getInt("userNo"));
				dto.setAddressName(rs.getString("addressName"));
				dto.setIsDefault(rs.getInt("isDefault"));
				dto.setReceiverName(rs.getString("receiverName"));
				dto.setReceiverTel(rs.getString("receiverTel"));
				dto.setUserAddress(rs.getString("userAddress"));
				dto.setUserDetailAddress(rs.getString("userDetailAddress"));
				dto.setUserZipcode(rs.getString("userZipcode"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return dto;
	}
	
	// 배송지 정보 수정
		public int updateUserAddress(UserAddressDTO address) {
			Connection con = null;
			PreparedStatement pstmt = null;
			int result = 0;
			
			String sql = "UPDATE userAddress SET addressName=?, isDefault=?, receiverName=?, "
					   + "receiverTel=?, userAddress=?, userDetailAddress=?, userZipcode=? "
					   + "WHERE userAddressNo=?";
			try {
				con = pool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, address.getAddressName());
				pstmt.setInt(2, address.getIsDefault());
				pstmt.setString(3, address.getReceiverName());
				pstmt.setString(4, address.getReceiverTel());
				pstmt.setString(5, address.getUserAddress());
				pstmt.setString(6, address.getUserDetailAddress());
				pstmt.setString(7, address.getUserZipcode());
				pstmt.setInt(8, address.getUserAddressNo());

				result = pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt);
			}
			return result;
		}
		
		//배송지 추가, 기존 insertUserAddress는 트랜잭션용이므로, 편의를 위해 하나 더 만듭니다.
		public int insertUserAddress(UserAddressDTO address) {
			Connection con = null;
			PreparedStatement pstmt = null;
			int result = 0;
			
			String sql = "INSERT INTO userAddress (userNo, addressName, isDefault, receiverName, "
					   + "receiverTel, userAddress, userDetailAddress, userZipcode) "
					   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			try {
				con = pool.getConnection();
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
				pool.freeConnection(con, pstmt);
			}
			return result;
		}
		
		//배송지 삭제
		public int deleteUserAddress(int userAddressNo) {
		    Connection con = null;
		    PreparedStatement pstmt = null;
		    int result = 0;
		    
		    String sql = "DELETE FROM userAddress WHERE userAddressNo = ?";
		    try {
		        con = pool.getConnection();
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, userAddressNo);
		        result = pstmt.executeUpdate();
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        pool.freeConnection(con, pstmt);
		    }
		    return result;
		}
		
		//'기본' 선택
		public int updateDefaultAddress(int userNo, int addressNo) {
		    Connection con = null;
		    PreparedStatement pstmt = null;
		    int result = 0;

		    try {
		        con = pool.getConnection();
		        con.setAutoCommit(false); 

		        String sql1 = "UPDATE userAddress SET isDefault = 0 WHERE userNo = ?";
		        pstmt = con.prepareStatement(sql1);
		        pstmt.setInt(1, userNo);
		        pstmt.executeUpdate();
		        pstmt.close();
		        
		        String sql2 = "UPDATE userAddress SET isDefault = 1 WHERE userAddressNo = ?";
		        pstmt = con.prepareStatement(sql2);
		        pstmt.setInt(1, addressNo);
		        result = pstmt.executeUpdate();
		        con.commit(); 

		    } catch (Exception e) {
		        try { if(con != null) con.rollback(); } catch(Exception ex) {}
		        e.printStackTrace();
		    } finally {
		        try { if(con != null) con.setAutoCommit(true); } catch(Exception e) {}
		        pool.freeConnection(con, pstmt);
		    }
		    return result;
		}
		
		// 모든 기본 배송지 설정을 해제하는 메서드
		public void resetDefaultAddress(int userNo) {
		    Connection con = null;
		    PreparedStatement pstmt = null;
		    try {
		        con = pool.getConnection();
		        String sql = "UPDATE userAddress SET isDefault = 0 WHERE userNo = ?";
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, userNo);
		        pstmt.executeUpdate();
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        pool.freeConnection(con, pstmt);
		    }
		}
		
		// [추가] 유저의 '기본 배송지(isDefault=1)' 정보만 콕 집어서 가져오는 메서드
		public UserAddressDTO getDefaultAddress(int userNo) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			UserAddressDTO dto = null;
			
			// 요청하신 4개의 컬럼만 정확하게 지정하여 성능을 최적화한 SQL 쿼리입니다. (26/04/09 1248i isDefault 컬럼 추가)
			String sql = "SELECT addressName, userAddress, userDetailAddress, userZipcode, "
			           + "receiverName, receiverTel, isDefault "
			           + "FROM userAddress "
			           + "WHERE userNo = ? AND isDefault = 1";
			
			try {
				con = pool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, userNo);
				rs = pstmt.executeQuery();

				// 기본 배송지가 존재한다면 객체에 값을 담아줍니다.
				if (rs.next()) {
					dto = new UserAddressDTO();
					dto.setAddressName(rs.getString("addressName"));
					dto.setUserAddress(rs.getString("userAddress"));
					dto.setUserDetailAddress(rs.getString("userDetailAddress"));
					dto.setUserZipcode(rs.getString("userZipcode"));
					dto.setReceiverName(rs.getString("receiverName"));
					dto.setReceiverTel(rs.getString("receiverTel"));
					dto.setIsDefault(rs.getInt("isDefault"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			
			// 기본 배송지가 없으면 null을 반환합니다.
			return dto;
		}
		
		// 1. 배송지 개수 확인
		public int countAddresses(Connection con, int userNo) {
		    int count = 0;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    String sql = "SELECT COUNT(*) FROM userAddress WHERE userNo = ?";
		    try {
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, userNo);
		        rs = pstmt.executeQuery();
		        if (rs.next()) {
		            count = rs.getInt(1);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        try { if (rs != null) rs.close(); } catch (Exception e) {}
		        try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
		    }
		    return count;
		}

		// 2. 기본 배송지 설정 해제 
		public void resetDefaultAddress(Connection con, int userNo) {
		    PreparedStatement pstmt = null;
		    String sql = "UPDATE userAddress SET isDefault = 0 WHERE userNo = ?";
		    try {
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, userNo);
		        pstmt.executeUpdate();
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
		    }
		}

		// 3. 배송지 정보 수정
		public int updateUserAddress(Connection con, UserAddressDTO address) {
		    int result = 0;
		    PreparedStatement pstmt = null;
		    String sql = "UPDATE userAddress SET addressName=?, isDefault=?, receiverName=?, "
		               + "receiverTel=?, userAddress=?, userDetailAddress=?, userZipcode=? "
		               + "WHERE userAddressNo=?";
		    try {
		        pstmt = con.prepareStatement(sql);
		        pstmt.setString(1, address.getAddressName());
		        pstmt.setInt(2, address.getIsDefault());
		        pstmt.setString(3, address.getReceiverName());
		        pstmt.setString(4, address.getReceiverTel());
		        pstmt.setString(5, address.getUserAddress());
		        pstmt.setString(6, address.getUserDetailAddress());
		        pstmt.setString(7, address.getUserZipcode());
		        pstmt.setInt(8, address.getUserAddressNo());
		        result = pstmt.executeUpdate();
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
		    }
		    return result;
		}
}
