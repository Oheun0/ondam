package com.ondam.review.service;

import java.util.Vector;

import com.ondam.review.dao.ReviewImageDAO;
import com.ondam.review.dto.ReviewImageDTO;

public class ReviewImageService {

	private ReviewImageDAO dao;

	public ReviewImageService() {
		this.dao = new ReviewImageDAO();
	}

	public Vector<ReviewImageDTO> getReviewImageList() {
		return dao.getReviewImage();
	}

	public boolean createReviewImage(ReviewImageDTO dto) {
		return dao.insertReviewImage(dto);
	}

	public boolean modifyReviewImage(ReviewImageDTO dto, int reviewImgNo) {
		return dao.updateReviewImage(dto, reviewImgNo);
	}

	public boolean removeReviewImage(int reviewImgNo) {
		return dao.deleteReviewImage(reviewImgNo);
	}
}

