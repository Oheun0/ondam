package com.ondam.common.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.ondam.product.dto.ProductDTO;
import com.ondam.product.dto.ProductImageDTO;
import com.ondam.product.service.ProductService;
import com.ondam.product.service.ProductImageService;
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
            case "spring-sale":
                Vector<ProductDTO> productList = productService.getProductListByFilter(
                    "type", "", "인기순", null, "따뜻해요", null
                );
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

            case "guide":
                return "home/guide";

            case "main":
            default:
                return "home/home"; 
        }
    }
}