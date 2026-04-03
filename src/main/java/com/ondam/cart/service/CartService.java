package com.ondam.cart.service;

import java.util.Vector;
import com.ondam.cart.dao.CartDAO;
import com.ondam.cart.dao.CartItemDAO;
import com.ondam.cart.dto.CartItemDTO;
import com.ondam.product.dao.ProductDAO;
import com.ondam.product.dao.ProductImageDAO;
import com.ondam.product.dao.ProductOptionDAO;
import com.ondam.product.dto.ProductDTO;
import com.ondam.product.dto.ProductImageDTO;
import com.ondam.product.dto.ProductOptionDTO;

public class CartService {

    private CartDAO cartDao = new CartDAO();
    private CartItemDAO itemDao = new CartItemDAO();
    private ProductDAO productDao = new ProductDAO(); // 기존 상품 DAO 활용
    private ProductImageDAO productImageDao = new ProductImageDAO(); 
    private ProductOptionDAO optionDao = new ProductOptionDAO(); 
    
    // 장바구니 목록 보기 (Cart가 없으면 자동 생성)
    public Vector<CartItemDTO> getCartList(int userNo) {
        int cartNo = cartDao.getOrCreateCart(userNo);
        Vector<CartItemDTO> vlist = itemDao.getCartItems(cartNo);
        
        for(CartItemDTO item : vlist) {
            // 1. 상품 기본 정보 채우기
            ProductDTO pDto = productDao.getProductById(item.getProductNo());
            ProductImageDTO pIDto = productImageDao.getProductImageById(item.getProductNo());
            
            if(pDto != null) {
                item.setProductName(pDto.getProductName());
                item.setProductPrice(pDto.getProductPrice());
            }
            if(pIDto != null) {
                item.setProductImg(pIDto.getImgFile());
            }
            
            // 2. 옵션 정보 상세 채우기
            ProductOptionDTO optDto = optionDao.getProductOptionByNo(item.getProductOptionNo());
            if(optDto != null) {
                item.setOptionSize(optDto.getOptionSize());
                item.setOptionColor(optDto.getOptionColor());
                item.setOptionStock(optDto.getOptionStock());
                // 옵션에 따른 추가 금액이 있다면 가격에 합산 가능
                int totalPrice = item.getProductPrice() + optDto.getOptionAddPrice();
                item.setProductPrice(totalPrice);
            } else {
                item.setOptionSize("N/A");
                item.setOptionColor("기본");
            }
        }
        return vlist;
    }

    // 장바구니 상품 추가 (중복 체크 포함)
    public void addItemToCart(int userNo, int productNo, int productOptionNo, int quantity) {
        int cartNo = cartDao.getOrCreateCart(userNo);
        
        // 동일 상품 & 옵션이 이미 있는지 확인
        CartItemDTO existing = itemDao.checkExistingItem(cartNo, productNo, productOptionNo);
        
        if (existing != null) {
            // 있으면 수량 업데이트
            itemDao.updateQuantity(existing.getCartItemNo(), existing.getCartQuantity() + quantity);
        } else {
            // 없으면 새로 추가
            itemDao.insertCartItem(cartNo, productNo, productOptionNo, quantity);
        }
    }

    // 개별 삭제
    public void removeItem(int cartItemNo) {
        itemDao.deleteItem(cartItemNo);
    }

    // 전체 비우기
    public void clearCart(int userNo) {
        int cartNo = cartDao.getOrCreateCart(userNo);
        itemDao.deleteAllItems(cartNo);
    }
    public void removeSelectedItems(String[] cartItemNos) {
        if (cartItemNos == null || cartItemNos.length == 0) return;
        
        // String 배열을 int 배열로 변환
        int[] itemNos = new int[cartItemNos.length];
        for (int i = 0; i < cartItemNos.length; i++) {
            itemNos[i] = Integer.parseInt(cartItemNos[i]);
        }
        
        itemDao.deleteSelectedItems(itemNos);
    }
    
    // 수량 조절 시 최대 재고 검증 로직 추가
    public void updateItemQuantity(int userNo, int cartItemNo, int quantity) {
        if (quantity <= 0) {
            itemDao.deleteItem(cartItemNo);
            return;
        }

        CartItemDTO targetItem = itemDao.getCartItemByNo(cartItemNo);
        if (targetItem != null) {
            ProductOptionDTO optDto = optionDao.getProductOptionByNo(targetItem.getProductOptionNo());
            int maxStock = (optDto != null) ? optDto.getOptionStock() : 0;
            
            int finalQuantity = Math.min(quantity, maxStock);
            if (finalQuantity > 0) {
                itemDao.updateQuantity(cartItemNo, finalQuantity);
            }
        }
    }
    
    // 세션 갱신을 위한 장바구니 전체 수량 조회
    public int refreshCartTotalQuantity(int userNo) {
        int cartNo = cartDao.getOrCreateCart(userNo);
        return itemDao.getCartTotalQuantity(cartNo);
    }
    
}