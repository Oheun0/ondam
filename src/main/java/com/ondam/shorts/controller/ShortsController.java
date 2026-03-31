package com.ondam.shorts.controller;

import com.ondam.shorts.service.ShortsGenerator;
import com.ondam.shorts.service.ShortsService;
import com.ondam.shorts.dao.ShortsDAO;
import com.ondam.shorts.dto.ShortsDTO;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/shorts")
@MultipartConfig(
    maxFileSize = 1024 * 1024 * 50,      // 파일 하나당 최대 50MB
    maxRequestSize = 1024 * 1024 * 60    // 요청 전체 최대 60MB
)
public class ShortsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ShortsGenerator shortsGenerator = new ShortsGenerator();
    private ShortsService shortsService = new ShortsService();
    private ShortsDAO shortsDAO = new ShortsDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            // @MultipartConfig가 있으면 request.getParameter()도 정상 작동합니다.
            String action = request.getParameter("action");
            String productNoStr = request.getParameter("productNo");
            String realPath = request.getServletContext().getRealPath("/");

            if (action == null || action.trim().isEmpty() || productNoStr == null) {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"필수 파라미터가 누락되었습니다.\"}");
                return;
            }

            int productNo = Integer.parseInt(productNoStr);

            // ==========================================
            // [기능 1] AI 자동 숏폼 생성
            // ==========================================
            if ("generate".equals(action)) {
                int vendorNo = Integer.parseInt(request.getParameter("vendorNo"));
                
                ShortsDTO current = shortsDAO.getShortByProductNo(productNo);
                if (current != null && current.getShortsState() == 0) {
                    response.getWriter().write("{\"status\":\"error\", \"message\":\"이미 영상 제작이 진행 중입니다.\"}");
                    return;
                }

                shortsGenerator.generateShortsAsync(vendorNo, productNo, realPath);
                response.getWriter().write("{\"status\":\"success\", \"message\":\"숏폼 생성이 시작되었습니다.\"}");
            }
            
            // ==========================================
            // [기능 2] 영상 완전 삭제
            // ==========================================
            else if ("delete".equals(action)) {
                boolean isDeleted = shortsService.removeShortsWithFiles(productNo, realPath);
                
                if (isDeleted) {
                    response.getWriter().write("{\"status\":\"success\", \"message\":\"영상과 데이터가 완전히 삭제되었습니다.\"}");
                } else {
                    response.getWriter().write("{\"status\":\"error\", \"message\":\"삭제할 영상이 없습니다.\"}");
                }
            }
            
            // ==========================================
            // [기능 3] 숨김 / 공개 상태 전환
            // ==========================================
            else if ("toggle".equals(action)) {
                boolean isToggled = shortsService.toggleVisibility(productNo);
                
                if (isToggled) {
                    response.getWriter().write("{\"status\":\"success\", \"message\":\"영상 공개 상태가 변경되었습니다.\"}");
                } else {
                    response.getWriter().write("{\"status\":\"error\", \"message\":\"상태를 변경할 영상이 존재하지 않습니다.\"}");
                }
            }
            
            // ==========================================
            // [기능 4] 수동 파일 업로드 (추후 구현을 위한 자리 비워두기)
            // ==========================================
            else if ("upload".equals(action)) {
                // jakarta.servlet.http.Part filePart = request.getPart("videoFile");
                // 여기에 수동 업로드 파일 저장 로직을 추가하시면 됩니다.
                response.getWriter().write("{\"status\":\"info\", \"message\":\"수동 업로드 기능은 준비 중입니다.\"}");
            }
            
            else {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"잘못된 요청입니다.\"}");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"서버 오류가 발생했습니다.\"}");
        }
    }
}