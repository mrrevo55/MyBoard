package com.revo.myboard.post.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
 * Created By Revo
 */

public class CreateDTO {

	@NotNull
	private long category;
	@Size(min = 4, max = 30)
	private String title;
	@NotEmpty
	private String content;
	
	public long getCategory() {
		return category;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public void setCategory(long category) {
		this.category = category;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
