package com.ondam.review.service;

import java.util.Vector;

import com.ondam.review.dao.ReviewDAO;
import com.ondam.review.dto.ReviewDTO;

public class ReviewService {

	private ReviewDAO dao;

	public ReviewService() {
		this.dao = new ReviewDAO();
	}

	public Vector<ReviewDTO> getReviewList() {
		return dao.getReview();
	}

	public boolean createReview(ReviewDTO dto) {
		return dao.insertReview(dto);
	}

	public boolean modifyReview(ReviewDTO dto, int reviewNo) {
		return dao.updateReview(dto, reviewNo);
	}

	public boolean removeReview(int reviewNo) {
		return dao.deleteReview(reviewNo);
	}
}

