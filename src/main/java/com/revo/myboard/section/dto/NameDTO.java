package com.revo.myboard.section.dto;

import javax.validation.constraints.Size;

/*
 * Created By Revo
 */

public class NameDTO {

	@Size(min = 4, max = 20)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
