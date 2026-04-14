package com.ondam.shorts.controller;

import java.io.IOException;
import java.io.File;
import com.ondam.common.ProjectWebappPaths;
import com.ondam.common.controller.Controller;
import com.ondam.common.ProjectWebappPaths;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.shorts.service.ShortsService;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  
    maxFileSize = 1024 * 1024 * 60,       
    maxRequestSize = 1024 * 1024 * 65     
)
public class ShortsGeneratorController implements Controller {

    private final ShortsService shortsService = new ShortsService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession();
        SellerDTO loginSeller = (SellerDTO) session.getAttribute("loginSeller");
        
        if (loginSeller == null) {
            sendJson(response, "error", "판매자 권한이 없습니다. 다시 로그인해주세요.");
            return null;
        }

        int vendorNo = loginSeller.getVendorNo();
        String action = request.getParameter("action");
        String productNoStr = request.getParameter("productNo");
        String webappRootPath = ProjectWebappPaths.getWebappRoot(request.getServletContext()).getAbsolutePath();
        if (!webappRootPath.endsWith(File.separator)) {
            webappRootPath = webappRootPath + File.separator;
        }

        if (action == null || action.trim().isEmpty() || productNoStr == null || productNoStr.trim().isEmpty()) {
            sendJson(response, "error", "필수 파라미터가 누락되었습니다.");
            return null;
        }

        int productNo;
        try {
            productNo = Integer.parseInt(productNoStr.trim());
        } catch (NumberFormatException e) {
            sendJson(response, "error", "유효하지 않은 상품 번호입니다.");
            return null;
        }

        try {
            String resultMsg = "";
            switch (action.trim()) {
                case "generate":
                    // [해결] 에러 발생 원인: title과 content를 받아서 넘기도록 파라미터 개수 동기화
                    String genTitle = request.getParameter("shortsTitle");
                    String genContent = request.getParameter("shortsContent");
                    
                    if(genTitle == null || genTitle.trim().isEmpty()) {
                        sendJson(response, "error", "쇼츠 제목을 입력해주세요.");
                        return null;
                    }
                    
                    resultMsg = shortsService.requestGenerateShorts(vendorNo, productNo, genTitle, genContent, webappRootPath);
                    if ("success".equals(resultMsg)) sendJson(response, "success", "숏폼 생성이 시작되었습니다.");
                    else sendJson(response, "error", resultMsg);
                    break;
                    
                case "delete":
                    resultMsg = shortsService.removeShortsWithValidation(vendorNo, productNo, webappRootPath);
                    if ("success".equals(resultMsg)) sendJson(response, "success", "영상과 데이터가 완전히 삭제되었습니다.");
                    else sendJson(response, "error", resultMsg);
                    break;
                    
                case "toggle":
                    resultMsg = shortsService.toggleVisibilityWithValidation(vendorNo, productNo);
                    if ("success".equals(resultMsg)) sendJson(response, "success", "영상 공개 상태가 변경되었습니다.");
                    else sendJson(response, "error", resultMsg);
                    break;

                case "upload":
                    Part videoPart = request.getPart("videoFile");
                    String title = request.getParameter("shortsTitle");
                    String content = request.getParameter("shortsContent");
                    String thumbnailBase64 = request.getParameter("thumbnailBase64");
                    
                    resultMsg = shortsService.uploadManualShorts(vendorNo, productNo, title, content, videoPart, thumbnailBase64, webappRootPath);
                    if ("success".equals(resultMsg)) sendJson(response, "success", "수동 영상 업로드가 완료되었습니다.");
                    else sendJson(response, "error", resultMsg);
                    break;
                    
                default:
                    sendJson(response, "error", "알 수 없는 요청입니다.");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sendJson(response, "error", "서버 내부 오류가 발생했습니다: " + e.getMessage());
        }
        return null;
    }

    private void sendJson(HttpServletResponse response, String status, String message) throws IOException {
        String json = String.format("{\"status\":\"%s\", \"message\":\"%s\"}", status, message.replace("\"", "\\\""));
        response.getWriter().write(json);
    }
}