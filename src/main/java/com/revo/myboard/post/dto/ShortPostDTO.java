package com.revo.myboard.post.dto;

import java.util.Date;

import com.revo.myboard.post.Post;

/*
 * Created By Revo
 */

public class ShortPostDTO {

	private long id;
	private String title;
	private Date lastActivity;
	private String author;
	private int answers;
	
	public long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public Date getLastActivity() {
		return lastActivity;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getAnswers() {
		return answers;
	}
	public void setAnswers(int answers) {
		this.answers = answers;
	}
	
	public ShortPostDTO mapFromPost(Post post) {
		this.setId(post.getId());
		this.setLastActivity(post.getLastActiveDate());
		this.setTitle(post.getTitle());
		this.setAuthor(post.getAuthor().getLogin());
		this.setAnswers(post.getComments().size());
		return this;
	}
	
}
