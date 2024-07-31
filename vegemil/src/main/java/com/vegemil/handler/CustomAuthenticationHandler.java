package com.vegemil.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler{
	
	private HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
	
	       if (isAjaxRequest(request)) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	            PrintWriter writer = response.getWriter();
	            writer.println(objectMapper.writeValueAsString("{'success': false, 'message': 'Authentication failed'}"));
	        } else {
	            // Handle non-AJAX (regular form submit) failure
	            response.sendRedirect("/member/login?error=true");
		
	        }
	}
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		 if (isAjaxRequest(request)) {
	            response.setStatus(HttpServletResponse.SC_OK);
	            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	            PrintWriter writer = response.getWriter();
	            writer.println(objectMapper.writeValueAsString("{'success': true, 'message': 'Authentication successful'}"));
	        } else {
	            // Handle non-AJAX (regular form submit) success
	        	SavedRequest prevRequest = requestCache.getRequest(request, response);
	        	
	        	if (prevRequest != null) {

	        		String url = prevRequest.getRedirectUrl();
                 	response.sendRedirect(url);
                 	
	        	} else {
	        		// 세션에 저장된 원래 요청 경로가 없는 경우
	        		response.sendRedirect("/");
	        	}
	          
	        }
		
	}
	

    private boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
