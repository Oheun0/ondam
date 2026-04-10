package com.ondam.ai.controller;

import java.io.File;
import java.util.UUID;
import java.util.Vector;
import com.ondam.common.controller.Controller;
import com.ondam.ai.dto.AiSearchDTO;
import com.ondam.ai.service.AiSearchService;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 15)
public class AiSearchController implements Controller {
    private final AiSearchService searchService = new AiSearchService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        String action = request.getParameter("action");
        String realPath = request.getServletContext().getRealPath("/");
        String scriptPath = realPath + "scripts" + File.separator + "shop_search.py";

        if (action == null || action.isEmpty()) return "ai/aiSearch";

        switch (action) {
            case "build": 
                boolean bSuccess = searchService.manageIndex(scriptPath, "build", null, null, null);
                response.getWriter().write("{\"status\":\"" + (bSuccess ? "success" : "error") + "\"}");
                return null;
            case "search":
                Part part = request.getPart("searchImage");
                String tempDir = realPath + "uploads" + File.separator + "temp_search";
                new File(tempDir).mkdirs();
                String tempFile = tempDir + File.separator + UUID.randomUUID().toString() + ".jpg";
                part.write(tempFile);
                
                System.out.println("[Controller Debug] 임시파일생성: " + tempFile);
                Vector<AiSearchDTO> results = searchService.searchProducts(scriptPath, tempFile);
                
                StringBuilder sb = new StringBuilder("{\"status\":\"success\", \"data\":[");
                for (int i = 0; i < results.size(); i++) {
                    AiSearchDTO d = results.get(i);
                    sb.append(String.format("{\"rank\":%d, \"productNo\":%d, \"productName\":\"%s\", \"productBrand\":\"%s\", \"productPrice\":%d, \"productOriginPrice\":%d, \"imgPath\":\"uploads/products/%s\", \"score\":%.4f}",
                            d.getRank(), 
                            d.getProductNo(), 
                            d.getProductName().replace("\"", "\\\""), 
                            (d.getProductBrand() != null ? d.getProductBrand().replace("\"", "\\\"") : ""), // 브랜드 null 방지
                            d.getProductPrice(), 
                            d.getProductOriginPrice(), // 원가 출력!
                            d.getImgFile(), 
                            d.getScore()));
                            
                    if (i < results.size() - 1) sb.append(",");
                }
                sb.append("]}");
                response.getWriter().write(sb.toString());
                new File(tempFile).delete();
                return null;
            default: return null;
        }
    }
}