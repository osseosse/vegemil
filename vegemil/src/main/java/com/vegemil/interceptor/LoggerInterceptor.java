package com.vegemil.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		logger.info("==================== BEGIN ====================");
		logger.info("Request URI ===> " + request.getRequestURI());
		
		// java vm이 사용할수 있는 총 메모리(bytes), -Xmx
		long maxMem = Runtime.getRuntime().maxMemory()/1024/1024;
		// java vm에 할당된 총 메모리
		long totalMem = Runtime.getRuntime().totalMemory()/1024/1024;
		// java vm이 추가로 할당 가능한 메모리
		long freeMem = Runtime.getRuntime().freeMemory()/1024/1024;

		// 현재 사용중인 메모리
		long usedMem = totalMem - freeMem;
		// 퍼센트
		double pct = usedMem * 100 / maxMem;
					

		String resourceInfo = "maxMem > "+maxMem+" | usedMem  : " + usedMem +" | resource pct :" + pct + "%";
		
		logger.info(resourceInfo);
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		logger.info("==================== END ======================");
				
	}

}
