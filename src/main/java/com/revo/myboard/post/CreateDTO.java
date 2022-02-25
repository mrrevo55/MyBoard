package com.revo.myboard.post;

/*
 * Created By Revo
 */

public class CreateDTO {

	private long category;
	private String title;
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
