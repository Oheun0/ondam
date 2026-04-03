package com.ondam.wish.service;

import java.util.Vector;

import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dao.ProductImageDAO;
import com.ondam.product.dao.ProductOptionDAO;
import com.ondam.product.dto.ProductDTO;
import com.ondam.product.dto.ProductImageDTO;
import com.ondam.product.dto.ProductOptionDTO;
import com.ondam.wish.dao.WishDAO;
import com.ondam.wish.dto.WishDTO;
// import com.ondam.product.dao.ProductDAO; // 상품 정보 조립용

public class WishService {

    private final WishDAO dao = new WishDAO();
    private final ProductDAO productDao = new ProductDAO();
    private final ProductImageDAO productImageDao = new ProductImageDAO();
    private final ProductOptionDAO optionDao = new ProductOptionDAO();


    // 1. 내 찜목록 가져오기 (상품 정보 조립 로직 추가 필요)
    public Vector<WishDTO> getMyWishList(int userNo) {
        // 1. 순수 찜 데이터 가져오기
        Vector<WishDTO> vlist = dao.getMyWish(userNo);
        
        // 2. 각 아이템에 상품 정보 조립하기
        for(WishDTO wish : vlist) {
            ProductDTO pDto = productDao.getProductById(wish.getProductNo());
            ProductImageDTO pIDto = productImageDao.getProductImageById(wish.getProductNo());
            ProductOptionDTO optDto = optionDao.getProductOptionByNo(wish.getProductOptionNo());
            
            if(pDto != null) {
                wish.setProductName(pDto.getProductName());
                wish.setProductPrice(pDto.getProductPrice());
            }
            if(pIDto != null) {
                wish.setProductImg(pIDto.getImgFile());
            }
            if(optDto != null) {
                wish.setOptionSize(optDto.getOptionSize());
                wish.setOptionColor(optDto.getOptionColor());
                // 옵션 추가금 합산
                wish.setProductPrice(wish.getProductPrice() + optDto.getOptionAddPrice());
            }
        }
        return vlist;
    }

    // 2. 찜 토글 로직 (있으면 지우고, 없으면 등록)
    public void toggleWish(int userNo, int productNo, int productOptionNo) {
        if(dao.checkWish(userNo, productNo, productOptionNo) != null) {
            dao.deleteWishByInfo(userNo, productNo, productOptionNo);
        } else {
            WishDTO dto = new WishDTO();
            dto.setUserNo(userNo);
            dto.setProductNo(productNo);
            dto.setProductOptionNo(productOptionNo);
            dao.insertWish(dto);
        }
    }
}