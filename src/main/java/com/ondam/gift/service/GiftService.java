package com.ondam.gift.service;

import java.util.Vector;

import com.ondam.gift.dao.GiftDAO;
import com.ondam.gift.dto.GiftDTO;
import com.ondam.orders.dao.OrdersProductDAO;
import com.ondam.product.dao.ProductDAO;
import com.ondam.user.dao.UserAddressDAO;
import com.ondam.user.dao.UserDAO;
import com.ondam.user.dto.UserAddressDTO;

public class GiftService {

	private final GiftDAO dao;
	private final UserDAO userDao;
    private final OrdersProductDAO ordersProductDao;
    private final ProductDAO productDao;
    private final UserAddressDAO userAddressDao;
    
	public GiftService() {
		this.dao = new GiftDAO();
		this.userDao = new UserDAO();
        this.ordersProductDao = new OrdersProductDAO();
        this.productDao = new ProductDAO();
        this.userAddressDao = new UserAddressDAO();
	}

	// [조회] 관리자용 전체 선물 목록
	public Vector<GiftDTO> getGiftList() {
		return dao.getGift();
	}

	// [조회] 특정 선물 단건 조회
	public GiftDTO getGiftById(int giftNo) {
		return dao.getGiftById(giftNo);
	}

	// [조회] 주문 번호로 선물 정보 조회
	public GiftDTO getGiftInfoByOrder(int orderNo) {
		return dao.getGiftByOrderNo(orderNo);
	}

	// [조회] 내가 받은 선물 목록
	public Vector<GiftDTO> getMyReceivedGifts(int userNo) {
		Vector<GiftDTO> list = dao.getReceivedGifts(userNo);
		UserAddressDTO defaultAddress = userAddressDao.getDefaultAddress(userNo);
		
        for (GiftDTO gift : list) {
            gift.setSenderName(userDao.getUserName(gift.getSenderNo()));
            int productNo = ordersProductDao.getOrderProductNo(gift.getOrderNo());
            gift.setProductBrand(productDao.getProductBrand(productNo));
            gift.setProductName(productDao.getProductName(productNo));
            gift.setProductImg(productDao.getProductImage(productNo));
            
            if(defaultAddress == null) {
            	gift.setReceiverAddressName("기본 배송지가 설정되어 있지 않습니다.");
                gift.setReceiverAddress("");
                gift.setReceiverDetailAddress("");
                gift.setReceiverZipcode("");
                gift.setReceiverPhoneNumber("");
                gift.setReceiverName("");
            }else {
            	gift.setAddressNo(defaultAddress.getUserAddressNo());
            	gift.setReceiverAddressName(defaultAddress.getAddressName());
                gift.setReceiverAddress(defaultAddress.getUserAddress());
                gift.setReceiverDetailAddress(defaultAddress.getUserDetailAddress());
                gift.setReceiverZipcode(defaultAddress.getUserZipcode());
                gift.setReceiverName(defaultAddress.getReceiverName());
                gift.setReceiverPhoneNumber(defaultAddress.getReceiverTel());
            }            
        }
		return list;
	}

	// [조회] 내가 보낸 선물 목록
	public Vector<GiftDTO> getMySentGifts(int userNo) {
		Vector<GiftDTO> list = dao.getSentGifts(userNo);
        
        for (GiftDTO gift : list) {
            gift.setSenderName(userDao.getUserName(userNo));
            gift.setReceiverName(userDao.getUserName(gift.getReceiverNo()));
            int productNo = ordersProductDao.getOrderProductNo(gift.getOrderNo());
            gift.setProductBrand(productDao.getProductBrand(productNo));
            gift.setProductName(productDao.getProductName(productNo));
            gift.setProductImg(productDao.getProductImage(productNo));
        }
		return list;
	}
	

	// [생성] 선물 보내기 로직 (유효성 검증 포함)
	public boolean createGift(GiftDTO dto) {
		// 1. 자기 자신에게 선물 보내기 방지
		if (dto.getSenderNo() == dto.getReceiverNo()) {
			System.out.println("[GiftService] 에러: 자기 자신에게는 선물할 수 없습니다.");
			return false;
		}
		
		// 2. 1주문 1선물 제약조건 사전 검사 (동일 주문 번호 존재 여부 확인)
		if (dao.getGiftByOrderNo(dto.getOrderNo()) != null) {
			System.out.println("[GiftService] 에러: 해당 주문 번호로 이미 선물이 존재합니다.");
			return false;
		}
		
		return dao.insertGift(dto);
	}

	// [상태 변경] 선물 수락
	public boolean acceptGift(int giftNo, int addressNo) {
	    return dao.updateGiftState(giftNo, 1, addressNo);
	}

	// [상태 변경] 선물 거절 (상태값: 2)
	public boolean rejectGift(int giftNo) {
		return dao.updateGiftState(giftNo, 2);
	}

	// [상태 변경] 선물 기한 만료 처리 (상태값: 3)
	public boolean expireGift(int giftNo) {
		return dao.updateGiftState(giftNo, 3);
	}

	// [수정] 전체 내용 수정 (관리자용)
	public boolean modifyGift(GiftDTO dto, int giftNo) {
		return dao.updateGift(dto, giftNo);
	}

	// [삭제] 선물 삭제
	public boolean removeGift(int giftNo) {
		return dao.deleteGift(giftNo);
	}
	
	// gift-chat.jsp 채팅 목록 구성용 Service
	public Vector<GiftDTO> getGiftsBetween(int myNo, int otherNo) {
	    Vector<GiftDTO> list = dao.getGiftsBetween(myNo, otherNo);
	    for (GiftDTO gift : list) {
	        gift.setSenderName(userDao.getUserName(gift.getSenderNo()));
	        gift.setReceiverName(userDao.getUserName(gift.getReceiverNo()));
	        int productNo = ordersProductDao.getOrderProductNo(gift.getOrderNo());
	        gift.setProductBrand(productDao.getProductBrand(productNo));
	        gift.setProductName(productDao.getProductName(productNo));
	        gift.setProductImg(productDao.getProductImage(productNo));
	    }
	    return list;
	}
	
	public int getFamilyNoBetween(int senderNo, int receiverNo) {
	    return dao.getFamilyNoBetween(senderNo, receiverNo);
	}
}