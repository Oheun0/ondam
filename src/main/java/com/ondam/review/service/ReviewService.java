package com.ondam.review.service;

import java.util.Vector;

import com.ondam.review.dao.ReviewDAO;
import com.ondam.review.dto.ReviewDTO;

public class ReviewService {

    private final ReviewDAO reviewDAO;

    public ReviewService() {
        this.reviewDAO = new ReviewDAO();
    }

    // 1. 리뷰 작성
    public boolean writeReview(ReviewDTO dto) {
        // 비즈니스 검증 로직이 필요하다면 이곳에 추가
        if (dto.getReviewContent() == null || dto.getReviewContent().trim().isEmpty()) {
            return false; // 빈 리뷰 방지
        }
        return reviewDAO.insertReview(dto);
    }

    // 2. 다른 사용자들의 전체 리뷰 보기
    public Vector<ReviewDTO> getOtherUsersReviews(int myUserNo) {
        return reviewDAO.getOtherUsersReviews(myUserNo);
    }

    // 3. 특정 상품에 대해 다른 사용자들의 리뷰 보기
    public Vector<ReviewDTO> getOtherUsersReviewsByItem(int orderItemNo, int myUserNo) {
        return reviewDAO.getOtherUsersReviewsByItem(orderItemNo, myUserNo);
    }

    // 4. 내 리뷰 보기
    public Vector<ReviewDTO> getMyReviews(int userNo) {
        return reviewDAO.getReviewsByUserNo(userNo);
    }

    // 5. 내 리뷰 수정
    public boolean editMyReview(ReviewDTO dto) {
        if (dto.getReviewNo() <= 0 || dto.getUserNo() <= 0) {
            return false;
        }
        return reviewDAO.updateMyReview(dto);
    }

    // 6. 내 리뷰 삭제
    public boolean deleteMyReview(int reviewNo, int userNo) {
        if (reviewNo <= 0 || userNo <= 0) {
            return false;
        }
        return reviewDAO.deleteMyReview(reviewNo, userNo);
    }
}