package com.revo.myboard.group.dto;

import javax.validation.constraints.Size;

/*
 * Created By Revo
 */

public class CreateDTO {

	@Size(min = 4, max = 20)
	private String name;
	@Size(min = 4, max = 9)
	private String authority;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}
