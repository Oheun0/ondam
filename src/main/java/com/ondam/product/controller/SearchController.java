package com.ondam.product.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.product.dao.ProductImageDAO;
import com.ondam.product.dto.ProductDTO;
import com.ondam.product.dto.ProductImageDTO;
import com.ondam.product.dto.SearchDTO;
import com.ondam.product.service.ProductService;
import com.ondam.product.service.SearchService;
import com.ondam.user.dto.UserDTO;
import com.ondam.wish.service.WishService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SearchController implements Controller {

    private ProductService productService = new ProductService();
    private ProductImageDAO productImageDAO = new ProductImageDAO();
    private WishService wishService = new WishService();
    private SearchService searchService = new SearchService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String keyword = request.getParameter("q");
        String action  = request.getParameter("action");
        
        if ("deleteRecent".equals(action)) {
            response.setContentType("application/json;charset=UTF-8");
            UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
            if (loginUser != null && keyword != null) {
                boolean ok = searchService.deleteSearchKeyword(loginUser.getUserNo(), keyword.trim());
                response.getWriter().write("{\"success\":" + ok + "}");
            } else {
                response.getWriter().write("{\"success\":false}");
            }
            return null;
        }
        
        if (keyword == null || keyword.trim().isEmpty()) {
            UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
            
            // 최근 검색어 (로그인 유저만)
            if (loginUser != null) {
                request.setAttribute("recentSearchList",
                    searchService.getRecentSearchList(loginUser.getUserNo()));
            }

            // 인기 검색어
            request.setAttribute("popularSearchList", searchService.getPopularSearchList());

            return "product/search";
        }

        keyword = keyword.trim();

        // 필터 파라미터
        String   sort     = request.getParameter("sort");
        String[] colors   = request.getParameterValues("color");
        String   season   = request.getParameter("season");
        String[] features = request.getParameterValues("feature");
        
        // 로그인 유저면 검색어 저장
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
        if (loginUser != null) {
            SearchDTO searchDTO = new SearchDTO();
            searchDTO.setUserNo(loginUser.getUserNo());
            searchDTO.setSearchKeyword(keyword);
            searchDTO.setSearchDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new java.util.Date()));
            searchService.addSearchKeyword(searchDTO);
        }

        // 검색 + 필터
        Vector<ProductDTO> productList = productService.searchProductsWithFilter(
            keyword, sort, colors, season, features
        );

        // 썸네일
        Map<Integer, String> thumbnailMap = new HashMap<>();
        for (ProductDTO p : productList) {
            ProductImageDTO img = productImageDAO.getProductImageById(p.getProductNo());
            if (img != null) thumbnailMap.put(p.getProductNo(), img.getImgFile());
        }

        // 찜 Set
        Set<Integer> wishSet = new HashSet<>();
        if (loginUser != null) {
            wishSet = wishService.getWishedProductNos(loginUser.getUserNo());
        }

        // 필터 복원용 attribute
        request.setAttribute("currentSort",     sort     != null ? sort     : "전체");
        request.setAttribute("currentColors",   colors   != null ? java.util.Arrays.asList(colors)   : new java.util.ArrayList<>());
        request.setAttribute("currentSeason",   season   != null ? season   : "");
        request.setAttribute("currentFeatures", features != null ? java.util.Arrays.asList(features) : new java.util.ArrayList<>());
        request.setAttribute("currentCategoryName", keyword);

        request.setAttribute("searchQuery",  keyword);
        request.setAttribute("productList",  productList);
        request.setAttribute("thumbnailMap", thumbnailMap);
        request.setAttribute("wishSet",      wishSet);

        return "product/search-result";
    }
}