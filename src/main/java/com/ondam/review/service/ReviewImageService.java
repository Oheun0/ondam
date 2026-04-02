package com.ondam.review.service;

import java.util.Vector;

import com.ondam.review.dao.ReviewImageDAO;
import com.ondam.review.dto.ReviewImageDTO;

public class ReviewImageService {

    private ReviewImageDAO dao;

    public ReviewImageService() {
        this.dao = new ReviewImageDAO();
    }

    public ReviewImageDTO getReviewImage(int reviewImgNo) {
        return dao.getReviewImageByNo(reviewImgNo);
    }

    public Vector<ReviewImageDTO> getImagesByReviewNo(int reviewNo) {
        return dao.getReviewImagesByReviewNo(reviewNo);
    }

    public boolean uploadReviewImage(ReviewImageDTO dto) {
        return dao.insertReviewImage(dto);
    }

    public boolean modifyReviewImage(ReviewImageDTO dto) {
        return dao.updateReviewImage(dto);
    }

    public boolean removeReviewImage(int reviewImgNo) {
        return dao.deleteReviewImage(reviewImgNo);
    }

    public boolean removeAllImagesByReviewNo(int reviewNo) {
        return dao.deleteReviewImagesByReviewNo(reviewNo);
    }
}