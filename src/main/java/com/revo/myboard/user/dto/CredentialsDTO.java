package com.revo.myboard.user.dto;

import com.revo.myboard.user.User;

/*
 * Created By Revo
 */

public class CredentialsDTO {

	private String login;
	private String authority;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	public CredentialsDTO mapFromUser(User user) {
		this.setAuthority(user.getGroup().getAuthority().toString());
		this.setLogin(user.getLogin()); 
		return this;
	}
	
}
