package com.ondam.user.controller;

import java.util.ResourceBundle;

import com.ondam.common.controller.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class KakaoLoginController implements Controller {
	private static final ResourceBundle rb = ResourceBundle.getBundle("config");
	private final String CLIENT_ID = rb.getString("kakao.rest.api.key");
    
    //private final String REDIRECT_URI = "http://localhost/ondam/kakao-callback";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String scheme = request.getScheme(); 
        String serverName = request.getServerName(); 
        int serverPort = request.getServerPort(); 
        String contextPath = request.getContextPath();
        String portStr = (serverPort == 80 || serverPort == 443) ? "" : ":" + serverPort;
        
        String dynamicRedirectUri = scheme + "://" + serverName + portStr + contextPath + "/kakao-callback";
        
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?client_id="  + CLIENT_ID 
                            + "&redirect_uri=" + dynamicRedirectUri
                            + "&response_type=code";

        response.sendRedirect(kakaoAuthUrl);
        
        return null; 
    }
}