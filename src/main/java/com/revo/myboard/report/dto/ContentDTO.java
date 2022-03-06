package com.revo.myboard.report.dto;

import javax.validation.constraints.NotEmpty;

/*
 * Created By Revo
 */

public class ContentDTO {

	@NotEmpty
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
