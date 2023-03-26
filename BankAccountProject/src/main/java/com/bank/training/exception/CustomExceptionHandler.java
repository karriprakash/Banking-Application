package com.bank.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bank.training.response.GenricResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler({UserNotFoundException.class, EmptyUserObject.class, AccountNotFoundException.class, InsufficientFundsException.class, InvalidMoneyRequest.class, Exception.class})
	public ResponseEntity<GenricResponse<String>> handleUserNotFound(Exception e) {
		var response = new GenricResponse<String>();
		response.setResponse(e.getMessage());
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		return ResponseEntity.internalServerError().body(response);
	}
	
}
