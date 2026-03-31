package com.ondam.wish.service;

import java.util.Vector;
import com.ondam.wish.dao.WishDAO;
import com.ondam.wish.dto.WishDTO;

public class WishService {

    private WishDAO dao;

    public WishService() {
        this.dao = new WishDAO();
    }

    public Vector<WishDTO> getWishList() {
        return dao.getWish();
    }

    public boolean createWish(WishDTO dto) {
        return dao.insertWish(dto);
    }

    public boolean modifyWish(WishDTO dto, int wishNo) {
        return dao.updateWish(dto, wishNo);
    }

    public boolean removeWish(int wishNo) {
        return dao.deleteWish(wishNo);
    }
}