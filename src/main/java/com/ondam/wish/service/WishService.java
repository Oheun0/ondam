package com.ondam.wish.service;

import java.util.Vector;

import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dao.ProductImageDAO;
import com.ondam.product.dto.ProductDTO;
import com.ondam.product.dto.ProductImageDTO;
import com.ondam.wish.dao.WishDAO;
import com.ondam.wish.dto.WishDTO;

public class WishService {

    private final WishDAO dao = new WishDAO();
    private final ProductDAO productDao = new ProductDAO();
    private final ProductImageDAO productImageDao = new ProductImageDAO();

    // 1. 내 찜목록 가져오기 (옵션 조립 제거)
    public Vector<WishDTO> getMyWishList(int userNo) {
        Vector<WishDTO> vlist = dao.getMyWish(userNo);
        
        for(WishDTO wish : vlist) {
            ProductDTO pDto = productDao.getProductById(wish.getProductNo());
            ProductImageDTO pIDto = productImageDao.getProductImageById(wish.getProductNo());
            
            if(pDto != null) {
                wish.setProductName(pDto.getProductName());
                wish.setProductPrice(pDto.getProductPrice());
            }
            if(pIDto != null) {
                wish.setProductImg(pIDto.getImgFile());
            }
        }
        return vlist;
    }

    // 2. 찜 토글 로직 (옵션 파라미터 제거)
    public void toggleWish(int userNo, int productNo) {
        if(dao.checkWish(userNo, productNo) != null) {
            // 이미 찜한 상태면 삭제
            dao.deleteWishByInfo(userNo, productNo);
        } else {
            // 찜한 상태가 아니면 등록
            WishDTO dto = new WishDTO();
            dto.setUserNo(userNo);
            dto.setProductNo(productNo);
            dao.insertWish(dto);
        }
    }
}