package com.revo.myboard.user.dto;

import javax.validation.constraints.Size;

public class PasswordDTO {

	@Size(min = 4, max = 20)
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
