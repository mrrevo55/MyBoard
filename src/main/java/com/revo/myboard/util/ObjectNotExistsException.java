package com.revo.myboard.util;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/*
 * Created By Revo
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class ObjectNotExistsException extends RuntimeException {

	private static final long serialVersionUID = 1352427019002376434L;
	private final String name;
	
	public ObjectNotExistsException(String name) {
		this.name = name;
	}
	
	public String getMessage() {
		return name+" not exits!";
	}
}
