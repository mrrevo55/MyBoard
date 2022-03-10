package com.revo.myboard.exception;

/*
 * Created By Revo
 */

public class EmailSendingException extends RuntimeException {

	private static final long serialVersionUID = -8161591741801388944L;

	public EmailSendingException(String email) {
		super("Server has problems while sending email to "+ email);
	}

	public EmailSendingException(String email, String message) {
		super("Server has problems while sending email to "+ email + ", error message: "+message);
	}
	
}
