package com.ondam.seller.service;

import java.util.Vector;
import com.ondam.seller.dao.SellerNotificationDAO;
import com.ondam.seller.dto.SellerNotificationDTO;

public class SellerNotificationService {
    private SellerNotificationDAO dao = new SellerNotificationDAO();

    public Vector<SellerNotificationDTO> getNotifications(int vendorNo) {
        return dao.getNotifications(vendorNo);
    }

    public boolean answerInquiry(int inquiryNo, String answerContent) {
        return dao.updateInquiryAnswer(inquiryNo, answerContent);
    }
}