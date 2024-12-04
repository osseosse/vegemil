package com.vegemil.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandleController {

	@ExceptionHandler(NullPointerException.class)
    public String handleNullPointer(Exception ex, Model model) {        
        return "error/404"; // 커스텀 에러 페이지
    }
	
	@ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(NoHandlerFoundException ex, Model model) {
		 return "error/404"; // 404 에러 페이지
    }
	
}
