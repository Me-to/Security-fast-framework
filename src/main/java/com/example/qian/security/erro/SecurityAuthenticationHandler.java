package com.example.qian.security.erro;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 错误提示
 */
@Component
public class SecurityAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    @Autowired
    private ObjectMapper mapper;
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=utf-8");

        PrintWriter printWriter = response.getWriter();
        if (exception.getMessage().equals("Bad credentials")) {
            printWriter.write(mapper.writeValueAsString("账号或密码错误"));
            printWriter.flush();
            printWriter.close();

        } else if (exception.getMessage().equals("User is disabled")) {
            printWriter.write(mapper.writeValueAsString("账号被禁用"));
            printWriter.flush();
            printWriter.close();

        } else {
            printWriter.write(mapper.writeValueAsString(exception.getMessage()));
            printWriter.flush();
            printWriter.close();

        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        redirectStrategy.sendRedirect(request, response, "/index");

    }

}
