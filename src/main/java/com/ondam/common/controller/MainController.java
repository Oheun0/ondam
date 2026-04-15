package com.ondam.common.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.ondam.product.dto.ProductDTO;
import com.ondam.product.dto.ProductImageDTO;
import com.ondam.product.service.ProductImageService;
import com.ondam.product.service.ProductService;
import com.ondam.user.dto.UserDTO;
import com.ondam.wish.service.WishService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MainController implements Controller {
    private final ProductService productService = new ProductService();
    private final ProductImageService productImageService = new ProductImageService();
    private final WishService wishService = new WishService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "main";
        }
        switch (action) {
            case "spring-sale": {
            	Vector<ProductDTO> productList = productService.getProductsBySeason("봄");
            	
                Map<Integer, String> thumbnailMap = new HashMap<>();
                if (productList != null) {
                    for (ProductDTO p : productList) {
                        Vector<ProductImageDTO> imgs = productImageService.getImagesByProductNo(p.getProductNo());
                        for (ProductImageDTO img : imgs) {
                            if (img.getImgType() == 0) {
                                thumbnailMap.put(p.getProductNo(), img.getImgFile());
                                break;
                            }
                        }
                    }
                }
                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("loginUser") != null) {
                    UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
                    Set<Integer> wishSet = wishService.getWishedProductNos(loginUser.getUserNo());
                    request.setAttribute("wishSet", wishSet);
                }
                request.setAttribute("productList", productList);
                request.setAttribute("thumbnailMap", thumbnailMap); 
                
                return "home/spring-sale";
            }

            case "guide":
                return "home/guide";

            case "main":
            default: {
                Vector<ProductDTO> newProducts = productService.getNewProducts();
                Map<Integer, String> newThumbnailMap = new HashMap<>();
                
                if (newProducts != null) {
                    for (ProductDTO p : newProducts) {
                        Vector<ProductImageDTO> imgs = productImageService.getImagesByProductNo(p.getProductNo());
                        for (ProductImageDTO img : imgs) {
                            if (img.getImgType() == 0) {
                                newThumbnailMap.put(p.getProductNo(), img.getImgFile());
                                break;
                            }
                        }
                    }
                }

                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("loginUser") != null) {
                    UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
                    Set<Integer> wishSet = wishService.getWishedProductNos(loginUser.getUserNo());
                    request.setAttribute("wishSet", wishSet);
                }
                // ---------------------------------------------------------

                request.setAttribute("newProducts", newProducts);
                request.setAttribute("newThumbnailMap", newThumbnailMap);
                return "home/home"; 
            }
        }
    }
}