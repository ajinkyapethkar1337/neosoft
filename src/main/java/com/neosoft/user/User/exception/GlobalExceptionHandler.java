package com.neosoft.user.User.exception;

import java.util.Date;

import org.apache.logging.log4j.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception exception,WebRequest req)
	{
		ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),req.getDescription(false));
		return new ResponseEntity(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	@ExceptionHandler(APIException.class)
	public ResponseEntity<?> handleAPIException(APIException exception,WebRequest req)
	{
		ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),req.getDescription(false));
		return new ResponseEntity(errorDetails,HttpStatus.NOT_FOUND);
	}
}
