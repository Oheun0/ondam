package com.ondam.seller.dto;

public class SellerNotificationDTO {
    private String id;       
    private String kind;     
    private String status;   
    private String date;     
    private String product;  
    private String author;   
    private String title;    
    private String body;     

    private String orderNo;  
    private String option;   
    private boolean answered;
    private String answer;
    private String answerDate;

    private String orderType; 
    private String payMethod; 
    private int qty;
    private String request;   

    private int rating;
    private String image;     
    
    // 💡 [추가] 리뷰 답변 전용 필드
    private String replyContent;
    private String replyDate;

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
    
    // 💡 [추가]
    public String getReplyContent() { return replyContent; }
    public void setReplyContent(String replyContent) { this.replyContent = replyContent; }
    public String getReplyDate() { return replyDate; }
    public void setReplyDate(String replyDate) { this.replyDate = replyDate; }
}