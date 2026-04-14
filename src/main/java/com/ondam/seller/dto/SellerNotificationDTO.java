package com.ondam.seller.dto;

public class SellerNotificationDTO {
    // 공통 필드
    private String id;       // 예: INQ-1, ORD-5, REV-3
    private String kind;     // inquiry, order, review
    private String status;   // pending, done, need
    private String date;     // 생성일
    private String product;  // 상품명
    private String author;   // 작성자/주문자명
    private String title;    // 제목 (프론트에서 보여줄 요약 텍스트)
    private String body;     // 본문 내용

    // 문의 전용
    private String orderNo;  // 주문 코드(orderCode)
    private String option;   // 옵션 정보
    private boolean answered;
    private String answer;
    private String answerDate;

    // 주문 전용
    private String orderType; // 일반/선물/조르기
    private String payMethod; // 카드/함께지갑/계좌
    private int qty;
    private String request;   // 배송 요청사항

    // 리뷰 전용
    private int rating;
    private String image;     // 리뷰 이미지 1장

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getKind() { return kind; }
    public void setKind(String kind) { this.kind = kind; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public String getOption() { return option; }
    public void setOption(String option) { this.option = option; }
    public boolean isAnswered() { return answered; }
    public void setAnswered(boolean answered) { this.answered = answered; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public String getAnswerDate() { return answerDate; }
    public void setAnswerDate(String answerDate) { this.answerDate = answerDate; }

    public String getOrderType() { return orderType; }
    public void setOrderType(String orderType) { this.orderType = orderType; }
    public String getPayMethod() { return payMethod; }
    public void setPayMethod(String payMethod) { this.payMethod = payMethod; }
    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
    public String getRequest() { return request; }
    public void setRequest(String request) { this.request = request; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}