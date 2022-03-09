package com.revo.myboard.exception;

import lombok.Getter;

/*
 * Created By Revo
 */

@Getter
public class ArgumentInUseException extends RuntimeException {

	private static final long serialVersionUID = -7209638586243774961L;
	
	public ArgumentInUseException(String name) {
		super("Argument "+name+" is in use!");
	}

}
