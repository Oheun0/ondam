package com.ondam.ai.controller;

import java.io.File;
import java.util.UUID;
import java.util.Vector;
import com.ondam.common.controller.Controller;
import com.ondam.ai.dto.aiSearchDTO;
import com.ondam.ai.service.aiSearchService;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 15)
public class aiSearchController implements Controller {
    private final aiSearchService searchService = new aiSearchService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        String action = request.getParameter("action");
        String realPath = request.getServletContext().getRealPath("/");
        String scriptPath = realPath + "scripts" + File.separator + "shop_search.py";

        if (action == null || action.isEmpty()) {
            return "ai/aiSearch";
        }

        switch (action) {
            case "build": return handleBuild(response, scriptPath);
            case "add": case "delete": case "update-image": 
                return handleManage(request, response, scriptPath, action);
            case "search": return handleSearch(request, response, scriptPath, realPath);
            case "view": return "ai/aiSearch";
            default: return null;
        }
    }

    private String handleBuild(HttpServletResponse response, String scriptPath) throws Exception {
        // 서비스에서 수정된 manageIndex를 호출합니다.[cite: 4, 6]
        boolean success = searchService.manageIndex(scriptPath, "build", null, null, null);
        response.getWriter().write("{\"status\":\"" + (success ? "success" : "error") + "\"}");
        return null;
    }

    private String handleManage(HttpServletRequest request, HttpServletResponse response, String scriptPath, String action) throws Exception {
        String pNo = request.getParameter("productNo");
        String img = request.getParameter("imgFile"); 
        String imgPath = (img != null) ? request.getServletContext().getRealPath("/") + "uploads" + File.separator + "products" + File.separator + img : null;
        
        // 5개의 인자를 정확히 전달합니다.
        boolean success = searchService.manageIndex(scriptPath, action, imgPath, pNo, null);
        response.getWriter().write("{\"status\":\"" + (success ? "success" : "error") + "\"}");
        return null;
    }

    private String handleSearch(HttpServletRequest request, HttpServletResponse response, String scriptPath, String realPath) throws Exception {
        Part part = request.getPart("searchImage");
        String tempDir = realPath + "uploads" + File.separator + "temp_search";
        new File(tempDir).mkdirs();
        String tempFile = tempDir + File.separator + UUID.randomUUID().toString() + ".jpg";
        part.write(tempFile);

        Vector<aiSearchDTO> results = searchService.searchProducts(scriptPath, tempFile);
        
        // [수정] seasons 데이터(Vector)를 포함하도록 JSON 조립 보완[cite: 4, 5]
        StringBuilder sb = new StringBuilder("{\"status\":\"success\", \"data\":[");
        for (int i = 0; i < results.size(); i++) {
            aiSearchDTO d = results.get(i);
            
            // Vector<String>을 JSON 배열 형태의 문자열로 변환
            String seasonStr = "[]";
            if (d.getSeasons() != null && !d.getSeasons().isEmpty()) {
                seasonStr = "[\"" + String.join("\",\"", d.getSeasons()) + "\"]";
            }

            sb.append(String.format("{\"rank\":%d, \"productNo\":%d, \"productName\":\"%s\", \"productPrice\":%d, \"imgPath\":\"uploads/products/%s\", \"score\":%.4f, \"seasons\":%s}",
                      d.getRank(), d.getProductNo(), d.getProductName().replace("\"", "\\\""), d.getProductPrice(), d.getImgFile(), d.getScore(), seasonStr));
            if (i < results.size() - 1) sb.append(",");
        }
        sb.append("]}");
        
        response.getWriter().write(sb.toString());
        new File(tempFile).delete();
        return null;
    }
}