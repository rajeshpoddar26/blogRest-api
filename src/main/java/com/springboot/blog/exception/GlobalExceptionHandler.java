package com.springboot.blog.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.blog.payload.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//	Specific exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
		
		ErrorDetails errorDetails= new ErrorDetails(new Date(), exception.getMessage(), 
				webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BlogAPIException.class)
	public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception, WebRequest webRequest){
		
		ErrorDetails errorDetails= new ErrorDetails(new Date(), exception.getMessage(), 
				webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	
//	Global exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest){
		
		ErrorDetails errorDetails= new ErrorDetails(new Date(), exception.getMessage(), 
				webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
//	First approach
	@Override
		protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
				HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Map<String, String> errors= new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) ->{
			String fieldName= ((FieldError) error).getField();
			
			String message= error.getDefaultMessage();
			
			errors.put(fieldName, message);
		});
		
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
		}
	
//	Second approach
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest webRequest){
//		
//		Map<String, String> errors= new HashMap<>();
//		exception.getBindingResult().getAllErrors().forEach((error) ->{
//			String fieldName= ((FieldError) error).getField();
//			
//			String message= error.getDefaultMessage();
//			
//			errors.put(fieldName, message);
//		});
//		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//	}
	
	
}
