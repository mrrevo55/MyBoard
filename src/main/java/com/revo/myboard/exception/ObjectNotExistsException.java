package com.revo.myboard.exception;

/*
 * Created By Revo
 */

public class ObjectNotExistsException extends RuntimeException {

	private static final long serialVersionUID = -6053516686921460172L;

	public ObjectNotExistsException(String property) {
		super("Object with property "+property+" not exists!");
	}

}
