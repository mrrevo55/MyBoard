package com.revo.myboard.exception;

import org.springframework.security.core.AuthenticationException;

/*
 * Created By Revo
 */

public class NonActiveAccountException extends AuthenticationException {

	private static final long serialVersionUID = 5601431547709096921L;

	public NonActiveAccountException(String login) {
		super("Account with login "+login+" is not activated!");
	}

}
