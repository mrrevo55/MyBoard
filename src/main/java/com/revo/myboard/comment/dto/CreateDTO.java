package com.revo.myboard.comment.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/*
 * Created By Revo
 */

public class CreateDTO {

	@NotNull
	private long post;
	@NotEmpty
	private String content;
	
	public long getPost() {
		return post;
	}
	public String getContent() {
		return content;
	}
	public void setPost(long post) {
		this.post = post;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
