package com.revo.myboard.security.dto;

import javax.validation.constraints.Size;

/*
 * Created By Revo
 */

public class CredentialsDTO {

	@Size(min = 4, max = 20)
	private String login;
	@Size(min = 4, max = 20)
	private String password;
	
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
