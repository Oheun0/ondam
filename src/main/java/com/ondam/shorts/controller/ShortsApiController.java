package com.ondam.shorts.controller;

import java.io.File;
import java.io.IOException;

import com.ondam.common.controller.Controller;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.shorts.dao.ShortsDAO;
import com.ondam.shorts.dto.ShortsDTO;
import com.ondam.shorts.service.ShortsGenerator;
import com.ondam.shorts.service.ShortsService;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/**
 * 숏폼 관련 API 처리를 담당하는 컨트롤러 (AJAX/Fetch 요청 전용)
 * 파일 처리를 위한 MultipartConfig 필수 적용
 */
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 50,       // 50MB
    maxRequestSize = 1024 * 1024 * 55     // 55MB
)
public class ShortsApiController implements Controller {

    private final ShortsGenerator shortsGenerator = new ShortsGenerator();
    private final ShortsService shortsService = new ShortsService();
    private final ShortsDAO shortsDAO = new ShortsDAO();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        response.setContentType("application/json;charset=UTF-8");

        // 1. 판매자 세션 기반 권한 검증 (BOLA/IDOR 방어)
        HttpSession session = request.getSession();
        SellerDTO loginSeller = (SellerDTO) session.getAttribute("loginSeller");
        
        if (loginSeller == null) {
            sendJson(response, "error", "판매자 권한이 없습니다. 다시 로그인해주세요.");
            return null;
        }

        // 클라이언트의 조작 가능한 파라미터가 아닌, 안전한 서버 세션에서 vendorNo 추출
        int vendorNo = loginSeller.getVendorNo();

        String action = request.getParameter("action");
        String productNoStr = request.getParameter("productNo");
        String realPath = request.getServletContext().getRealPath("/");

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

        // 2. Action 라우팅
        try {
            switch (action.trim()) {
                case "generate":
                    handleGenerate(response, vendorNo, productNo, realPath);
                    break;
                case "delete":
                    handleDelete(response, vendorNo, productNo, realPath);
                    break;
                case "toggle":
                    handleToggle(response, vendorNo, productNo);
                    break;
                case "upload":
                    handleUpload(request, response, vendorNo, productNo, realPath);
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

    private void handleGenerate(HttpServletResponse response, int vendorNo, int productNo, String realPath) throws IOException {
        ShortsDTO current = shortsDAO.getShortByProductNo(productNo);
        if (current != null && current.getShortsState() == 0) {
            sendJson(response, "error", "이미 해당 상품의 영상 제작이 진행 중입니다.");
            return;
        }
        
        // 타인 상품에 대한 검증은 로직상 ShortsGenerator 내부 조회 데이터와 매칭되므로 생성 허용 (혹은 추가 검증 가능)
        shortsGenerator.generateShortsAsync(vendorNo, productNo, realPath);
        sendJson(response, "success", "숏폼 생성이 시작되었습니다.");
    }

    private void handleDelete(HttpServletResponse response, int vendorNo, int productNo, String realPath) throws IOException {
        ShortsDTO target = shortsDAO.getShortByProductNo(productNo);
        
        if (target == null) {
            sendJson(response, "error", "삭제할 영상이 없습니다.");
            return;
        }
        
        // 소유권 방어 로직
        if (target.getVendorNo() != vendorNo) {
            sendJson(response, "error", "해당 영상을 삭제할 권한이 없습니다.");
            return;
        }

        boolean isDeleted = shortsService.removeShortsWithFiles(productNo, realPath);
        
        if (isDeleted) {
            sendJson(response, "success", "영상과 데이터가 완전히 삭제되었습니다.");
        } else {
            sendJson(response, "error", "삭제에 실패했습니다.");
        }
    }

    private void handleToggle(HttpServletResponse response, int vendorNo, int productNo) throws IOException {
        ShortsDTO target = shortsDAO.getShortByProductNo(productNo);
        
        if (target == null) {
            sendJson(response, "error", "상태를 변경할 영상이 존재하지 않습니다.");
            return;
        }

        // 소유권 방어 로직
        if (target.getVendorNo() != vendorNo) {
            sendJson(response, "error", "해당 영상의 상태를 변경할 권한이 없습니다.");
            return;
        }

        boolean isToggled = shortsService.toggleVisibility(productNo);
        
        if (isToggled) {
            sendJson(response, "success", "영상 공개 상태가 변경되었습니다.");
        } else {
            sendJson(response, "error", "상태 변경에 실패했습니다.");
        }
    }

    private void handleUpload(HttpServletRequest request, HttpServletResponse response, int vendorNo, int productNo, String realPath) throws Exception {
        Part filePart = request.getPart("videoFile");
        
        if (filePart == null || filePart.getSize() == 0) {
            sendJson(response, "error", "업로드할 영상 파일이 없습니다.");
            return;
        }

        String fileName = "shorts_manual_" + productNo + "_" + System.currentTimeMillis() + ".mp4";
        String savePath = realPath + "uploads" + File.separator + "shorts";
        
        File uploadDir = new File(savePath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        filePart.write(savePath + File.separator + fileName);

        ShortsDTO dto = new ShortsDTO();
        dto.setProductNo(productNo);
        dto.setVendorNo(vendorNo); // 서버에서 추출한 안전한 vendorNo 세팅
        dto.setVideoFile(fileName);
        dto.setThumbnailImg(fileName.replace(".mp4", "_thumb.jpg")); 
        dto.setShortsState(1); 

        ShortsDTO existing = shortsDAO.getShortByProductNo(productNo);
        
        // 기존 영상이 있을 경우 소유권 방어 로직
        if (existing != null && existing.getVendorNo() != vendorNo) {
            sendJson(response, "error", "타인의 상품에 영상을 덮어쓸 수 없습니다.");
            return;
        }

        boolean success = (existing == null) ? shortsDAO.insertShorts(dto) : shortsDAO.updateShortsByProductNo(dto);

        if (success) {
            sendJson(response, "success", "수동 영상 업로드가 완료되었습니다.");
        } else {
            sendJson(response, "error", "DB 기록에 실패했습니다.");
        }
    }

    private void sendJson(HttpServletResponse response, String status, String message) throws IOException {
        String json = String.format("{\"status\":\"%s\", \"message\":\"%s\"}", status, message.replace("\"", "\\\""));
        response.getWriter().write(json);
    }
}