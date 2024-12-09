package com.iugo.eron.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.iugo.eron.dto.ErrorMessage;

@RestControllerAdvice
public class HandlerController {

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorMessage> validationMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException  ex) {
		return  new ResponseEntity<ErrorMessage>(
			new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage()), 
			HttpStatus.BAD_REQUEST);
	}
}
