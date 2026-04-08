package com.ondam.wish.service;

import java.util.Set;
import java.util.Vector;

import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dao.ProductImageDAO;
import com.ondam.wish.dao.WishDAO;
import com.ondam.wish.dto.WishDTO;
// import com.ondam.product.dao.ProductDAO; // 상품 정보 조립용

public class WishService {

    private final WishDAO dao = new WishDAO();
    private final ProductDAO productDao = new ProductDAO();

    // 1. 내 찜목록 가져오기 (옵션 조립 제거)
    public Vector<WishDTO> getMyWishList(int userNo, String sort, String part) {
        return dao.getMyWish(userNo, sort, part);
    }

    // 2. 찜 토글 로직 (옵션 파라미터 제거)
    public boolean toggleWish(int userNo, int productNo) {
        if (dao.checkWish(userNo, productNo) != null) {
            dao.deleteWishByInfo(userNo, productNo);
            productDao.decreaseWishCount(productNo);
            return false;
        } else {
            WishDTO dto = new WishDTO();
            dto.setUserNo(userNo);
            dto.setProductNo(productNo);
            dao.insertWish(dto);
            productDao.increaseWishCount(productNo);
            return true;
        }
    }
    
    public Set<Integer> getWishedProductNos(int userNo) {
        return dao.getWishedProductNos(userNo);
    }
}