package com.revo.myboard.comment.dto;

import javax.validation.constraints.NotEmpty;

/*
 * Created By Revo
 */

public class ContentDTO {

	@NotEmpty
	private String newContent;

	public String getNewContent() {
		return newContent;
	}

	public void setNewContent(String newContent) {
		this.newContent = newContent;
	}
	
}
