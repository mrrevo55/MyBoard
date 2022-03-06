package com.revo.myboard.group.dto;

import javax.validation.constraints.Size;

/*
 * Created By Revo
 */

public class AuthortiyDTO {

	@Size(min = 4, max = 9)
	private String newAuthority;

	public String getNewAuthority() {
		return newAuthority;
	}

	public void setNewAuthority(String newAuthority) {
		this.newAuthority = newAuthority;
	}
	
}
