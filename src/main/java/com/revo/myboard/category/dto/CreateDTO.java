package com.revo.myboard.category.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
 * Created By Revo
 */

public class CreateDTO {

	@Size(min = 2, max = 20)
	private String name;
	@NotNull
	private long section;
	
	public String getName() {
		return name;
	}
	public long getSection() {
		return section;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSection(long server) {
		this.section = server;
	}
	
}
