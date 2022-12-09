package com.vegemil.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CertificationInterceptor implements HandlerInterceptor{
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	/*
        HttpSession session = request.getSession();
        AdminDTO adminDto = (AdminDTO) session.getAttribute("admin");
 
        if(ObjectUtils.isEmpty(adminDto)){
            response.sendRedirect("/admin/auth/login?msg=session");
            return false;
        }else{
            session.setMaxInactiveInterval(30*60);
            return true;
        }
        */
    	return true;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        
    }
 
}