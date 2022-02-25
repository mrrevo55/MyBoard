package com.revo.myboard.comment;

/*
 * Created By Revo
 */

public class ShortCommentDTO {
	private long id;
	private String user;
	private long post;
	
	public long getId() {
		return id;
	}
	public String getUser() {
		return user;
	}
	public long getPost() {
		return post;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setPost(long post) {
		this.post = post;
	}
	
	public ShortCommentDTO mapFromComment(Comment comment) {
		this.setId(comment.getId());
		this.setPost(comment.getPost().getId());
		this.setUser(comment.getUser().getLogin());
		return this;
	}
	
}
