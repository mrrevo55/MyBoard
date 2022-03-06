package com.revo.myboard.post.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/*
 * Created By Revo
 */

public class EditDTO {

	@Size(min = 4, max = 30)
	private String title;
	@NotEmpty
	private String content;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
