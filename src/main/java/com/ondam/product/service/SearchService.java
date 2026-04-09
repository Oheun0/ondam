package com.ondam.product.service;

import java.util.Vector;
import com.ondam.product.dao.SearchDAO;
import com.ondam.product.dto.SearchDTO;

public class SearchService {

    private SearchDAO dao;

    public SearchService() {
        this.dao = new SearchDAO();
    }

    public Vector<SearchDTO> getRecentSearchList(int userNo) {
        return dao.getRecentSearch(userNo);
    }

    public Vector<SearchDTO> getPopularSearchList() {
        return dao.getPopularSearch();
    }

    // 검색어 저장 (비즈니스 로직: trim 및 유효성 검사 추가)
    public boolean addSearchKeyword(SearchDTO dto) {
        // 1. 검색어가 null이 아닌지 확인
        if (dto.getSearchKeyword() == null) {
            return false;
        }

        // 2. 앞뒤 공백 제거 (Trim)
        String trimmedKeyword = dto.getSearchKeyword().trim();
        dto.setSearchKeyword(trimmedKeyword);

        // 3. 공백을 제거했는데 빈 문자열이라면 저장하지 않음
        if (trimmedKeyword.isEmpty()) {
            return false;
        }

        return dao.insertSearch(dto);
    }
    
    public boolean deleteSearchKeyword(int userNo, String keyword) {
    	return dao.deleteSearch(userNo, keyword);
    }
}