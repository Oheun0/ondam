package com.ondam.inquiry.service;

import java.util.Vector;
import com.ondam.inquiry.dao.InquiryDAO;
import com.ondam.inquiry.dto.InquiryDTO;

public class InquiryService {
    
    private InquiryDAO inquiryDao = new InquiryDAO();

    public Vector<InquiryDTO> getMyInquiries(int userNo) {
        return inquiryDao.getMyInquiries(userNo);
    }
    
    public InquiryDTO getProductInfo(int productNo) {
        return inquiryDao.getProductInfoForInquiry(productNo);
    }
    
    public boolean insertInquiry(InquiryDTO dto) {
        return inquiryDao.insertInquiry(dto);
    }
    
    public boolean updateInquiry(InquiryDTO dto) { 
    	return inquiryDao.updateInquiry(dto); 
    }
    
    public boolean deleteInquiry(int inquiryNo) { 
    	return inquiryDao.deleteInquiry(inquiryNo); 
    }
    
    public InquiryDTO getInquiryDetail(int inquiryNo) { 
    	return inquiryDao.getInquiryDetail(inquiryNo); 
    }
    
    public Vector<InquiryDTO> getInquiriesByProductNo(int productNo) {
        return inquiryDao.getInquiriesByProductNo(productNo);
    }
}