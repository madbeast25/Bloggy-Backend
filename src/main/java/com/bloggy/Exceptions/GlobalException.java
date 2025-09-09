package com.bloggy.Exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(EmailAlreadyExistException.class)
	public Map<String,String> EmailAlreadyExist(EmailAlreadyExistException ex){
		Map<String,String> errors=new HashMap<>();
		
		errors.put("message",ex.getMessage());
		errors.put("errorCode",HttpStatus.BAD_REQUEST.toString());
		
		return errors;
	}
}
