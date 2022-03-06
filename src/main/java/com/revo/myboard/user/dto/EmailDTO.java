package com.revo.myboard.user.dto;

import javax.validation.constraints.Email;

/*
 * Created By Revo
 */

public class EmailDTO {

	@Email
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
