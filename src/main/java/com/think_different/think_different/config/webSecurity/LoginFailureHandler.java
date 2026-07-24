package com.think_different.think_different.config.webSecurity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    private static final String LOGIN_URL = "/members/login";

    private final FlashMapManager flashMapManager = new SessionFlashMapManager();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException exception) throws IOException {
        String targetUrl = request.getContextPath() + LOGIN_URL;

        FlashMap flashMap = new FlashMap();
        flashMap.put("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
        flashMap.setTargetRequestPath(targetUrl);
        flashMapManager.saveOutputFlashMap(flashMap, request, response);

        response.sendRedirect(targetUrl);
    }
}
