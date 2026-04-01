package com.ondam.common.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ondam.notification.controller.NotificationController;
import com.ondam.product.controller.CategoryController;
import com.ondam.user.controller.KakaoCallbackController;
import com.ondam.user.controller.KakaoLoginController;
import com.ondam.user.controller.LoginController;
import com.ondam.user.controller.SignupCompleteController;
import com.ondam.user.controller.SignupStartController;
import com.ondam.user.controller.SignupStep1BasicController;
import com.ondam.user.controller.SignupStep2AddressController;
import com.ondam.user.controller.SignupStep3PreferenceController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/") // 모든 요청 받기
public class DispatcherServlet extends HttpServlet {
    
    // URL과 실행할 컨트롤러를 매핑해서 저장하는 저장소
    private Map<String, Controller> handlerMapping = new HashMap<>();

    @Override
    public void init() throws ServletException {
        // 매핑 정보 등록
        handlerMapping.put("/login", new LoginController());
        handlerMapping.put("/notification", new NotificationController());
        handlerMapping.put("/main", new MainController());
        handlerMapping.put("/category", new CategoryController());
        handlerMapping.put("/signup-start", new SignupStartController());
        handlerMapping.put("/signup-step1-basic", new SignupStep1BasicController());
        handlerMapping.put("/signup-step2-address", new SignupStep2AddressController());
        handlerMapping.put("/signup-step3-preference", new SignupStep3PreferenceController());
        handlerMapping.put("/signup-complete", new SignupCompleteController());
        handlerMapping.put("/kakao-login", new KakaoLoginController());
        handlerMapping.put("/kakao-callback", new KakaoCallbackController());
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // URL 분석 (ContextPath 제외한 순수 경로)
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length()); 
        
        // 확장자가 있거나(파일), 특정 정적 폴더 경로인 경우 톰캣 기본 서블릿에 위임
        if (path.contains(".") || path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/")) {
            request.getServletContext().getNamedDispatcher("default").forward(request, response);
            return;
        }

        // 담당 컨트롤러 꺼내기
        Controller controller = handlerMapping.get(path);
        
        if (controller == null) {
            response.sendError(404, "해당 경로를 찾을 수 없습니다.");
            return;
        }

        try {
            // 논리 컨트롤러 실행 (비즈니스 로직 처리)
            String viewName = controller.execute(request, response);
            
            // 뷰 리졸버 역할 (경로 조립 및 포워딩)
            if (viewName != null) {
                if (viewName.startsWith("redirect:")) {
                    // 리다이렉트 처리 (예: "redirect:/main") '/' 가 오기 전이 9글자
                    String redirectPath = contextPath + viewName.substring(9);
                    response.sendRedirect(redirectPath);
                } else {
                    // 포워딩 처리 (WEB-INF 경로 자동 조립)
                    String viewPath = "/WEB-INF/views/" + viewName + ".jsp";
                    request.getRequestDispatcher(viewPath).forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "서버 내부 오류 발생");
        }
    }
}