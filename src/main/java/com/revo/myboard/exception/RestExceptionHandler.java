package com.revo.myboard.exception;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.TokenExpiredException;

import lombok.extern.slf4j.Slf4j;

/*
 * Created By Revo
 */

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
	
	@ExceptionHandler(value = MissingRequestHeaderException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String missingRequestHeaderHandler(MissingRequestHeaderException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(value =  TokenExpiredException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String tokenExpiredHandler(TokenExpiredException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(value = ArgumentInUseException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String argumentInUseHandler(ArgumentInUseException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(value = HasLikeBeforeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String hasLikeBeforeHandler(HasLikeBeforeException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String illegalArgumentHandler(IllegalArgumentException exception) {
		return exception.getMessage() + " is illegal argument!";
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
	
	@ExceptionHandler(value = EmailSendingException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String emailSendingExceptionHandler(EmailSendingException exception) { 
		log.warn("Error while sending email, exception message: "+exception.getMessage());
		return exception.getMessage();
	}
	
	@ExceptionHandler(value = ObjectNotExistsException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String objectNotExistsHandler(ObjectNotExistsException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(value = EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String entityNotFoundHandler(EntityNotFoundException exception) {
		return exception.getMessage();
	}

	@ExceptionHandler(value = NullPointerException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String nullPointerExceptionHandler(NullPointerException exception) {
		return exception.getMessage() + " not found!";
	}
	
}
