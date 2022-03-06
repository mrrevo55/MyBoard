package com.revo.myboard.security.dto;

import javax.validation.constraints.Size;

/*
 * Created By Revo
 */

public class CodeDTO {

	@Size(min = 10, max = 10)
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
