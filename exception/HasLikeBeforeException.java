package com.revo.myboard.exception;

/*
 * Created By Revo
 */

public class HasLikeBeforeException extends RuntimeException {

	private static final long serialVersionUID = 5043683997010449722L;

	public HasLikeBeforeException(String id) {
		super("Probably object of id "+id+" has like from you!");
	}

}
