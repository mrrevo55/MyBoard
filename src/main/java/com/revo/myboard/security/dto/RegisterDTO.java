package com.revo.myboard.security.dto;

import javax.validation.constraints.Email;

/*
 * Created By Revo
 */

public class RegisterDTO extends CredentialsDTO{

	@Email
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
