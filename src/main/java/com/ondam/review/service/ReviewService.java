package com.ondam.review.service;

import java.util.Set;
import java.util.Vector;

import com.ondam.review.dao.ReviewDAO;
import com.ondam.review.dao.ReviewImageDAO;
import com.ondam.review.dto.ReviewDTO;

public class ReviewService {

    private final ReviewDAO reviewDAO;
    private final ReviewImageDAO reviewImageDAO;
    
    public ReviewService() {
        this.reviewDAO = new ReviewDAO();
        this.reviewImageDAO = new ReviewImageDAO();
    }

    // 1. 리뷰 작성 (리턴 타입을 int로 변경)
    public int writeReview(ReviewDTO dto) {
        // 비즈니스 검증: 내용이 없으면 실패(0) 반환
        if (dto.getReviewContent() == null || dto.getReviewContent().trim().isEmpty()) {
            return 0; 
        }
        // DAO가 생성된 reviewNo를 돌려줄 것이므로 그대로 리턴합니다.
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
    public Vector<ReviewDTO> getReviewsByUserNo(int userNo) {
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

    // 7. 작성 가능한 후기 보기 (새로 추가!)
    public Vector<ReviewDTO> getWriteableReviews(int userNo) {
        return reviewDAO.getWriteableReviews(userNo);
    }
    
 // 8. 단일 리뷰 조회
    public ReviewDTO getReviewByNo(int reviewNo, int userNo) {
        return reviewDAO.getReviewByNo(reviewNo, userNo);
    }
    
 // 9. 주문 상품 정보 단일 조회
    public ReviewDTO getOrderProductByNo(int orderItemNo, int userNo) {
        return reviewDAO.getOrderProductByNo(orderItemNo, userNo);
    }
    
 // 10. 리뷰 사진 저장
    public boolean saveReviewImage(int reviewNo, String fileName, int order) {
        com.ondam.review.dto.ReviewImageDTO imgDto = new com.ondam.review.dto.ReviewImageDTO();
        imgDto.setReviewNo(reviewNo);
        imgDto.setReviewImg(fileName);
        imgDto.setImgOrder(order);
        return reviewImageDAO.insertReviewImage(imgDto);
    }
    
 //  11. 리뷰 사진 가져오기 (수정 화면용)
    public Vector<com.ondam.review.dto.ReviewImageDTO> getReviewImages(int reviewNo) {
        return reviewImageDAO.getReviewImagesByReviewNo(reviewNo);
    }

    //  12. 다음에 들어갈 사진 순서 번호 가져오기
    public int getNextImgOrder(int reviewNo) {
        int maxOrder = reviewImageDAO.getMaxImgOrder(reviewNo);
        return maxOrder + 1;
    }
    
    //13. 파일 삭제
    public void removeReviewImage(int imgNo, String savePath) {
        reviewImageDAO.deleteReviewImage(imgNo);
    }
    
 // 특정 상품(productNo)에 달린 모든 리뷰 가져오기 (상품 상세 페이지용)
    public Vector<ReviewDTO> getReviewsByProductNo(int productNo, String sort) {
        return reviewDAO.getReviewsByProductNo(productNo, sort);
    }
    
 // 리뷰 '도움돼요' 증가
    public boolean increaseReviewHelpful(int reviewNo, int userNo) {
        return reviewDAO.increaseReviewHelpful(reviewNo, userNo);
    }
    
    public Set<Integer> getHelpfulReviewNosByUser(int userNo) {
        return reviewDAO.getHelpfulReviewNosByUser(userNo);
    }
}