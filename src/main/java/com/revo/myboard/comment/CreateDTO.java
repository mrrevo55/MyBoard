package com.revo.myboard.comment;

/*
 * Created By Revo
 */

public class CreateDTO {

	private long post;
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
