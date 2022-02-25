package com.revo.myboard.exception;

import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * Created By Revo
 */

@RestControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(value = { SendFailedException.class, AddressException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String invalidEmailExceptionHandler() { 
		return "Email is invalid!";
	}
	
	@ExceptionHandler(value = NullPointerException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String objectNotExistsExceptionHandler(NullPointerException exception) {
		return exception.getMessage() + " not found!";
	}
	
	@ExceptionHandler(value = MissingRequestHeaderException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String missingRequestHeaderHandler(MissingRequestHeaderException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String illegalArgumentHandler(IllegalArgumentException exception) {
		return exception.getMessage() + " is illegal argument!";
	}
	
	@ExceptionHandler(value = EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String entityNotFoundHandler(EntityNotFoundException exception) {
		return exception.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public String handleValidationExceptions(MethodArgumentNotValidException exception) {
	    return exception.getMessage();
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = ConstraintViolationException.class)
	public String handleValidationExceptions(ConstraintViolationException exception) {
	    return exception.getMessage();
	}
	
}
