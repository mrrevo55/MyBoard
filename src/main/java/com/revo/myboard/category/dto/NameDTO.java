package com.revo.myboard.category.dto;

import javax.validation.constraints.Size;

/*
 * Created By Revo
 */

public class NameDTO {
	
	@Size(min = 2, max = 20)
	private String newName;

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}
	
}
