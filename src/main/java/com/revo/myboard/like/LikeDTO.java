package com.revo.myboard.like;

/*
 * Created By Revo
 */

public class LikeDTO {

	private String user;
	private long post;
	private long comment;
	private String who;
	
	public String getUser() {
		return user;
	}
	public long getPost() {
		return post;
	}
	public long getComment() {
		return comment;
	}
	public String getWho() {
		return who;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setPost(long post) {
		this.post = post;
	}
	public void setComment(long comment) {
		this.comment = comment;
	}
	public void setWho(String who) {
		this.who = who;
	}
	
	public LikeDTO mapFromLike(Like like) {
		if(like.getComment() != null) {
			this.setComment(like.getComment().getId());
		}
		if(like.getPost() != null) {
			this.setPost(like.getPost().getId());
		}
		if(like.getUser() != null) {
			this.setUser(like.getUser().getLogin());
		}
		this.setWho(like.getWho().getLogin());
		return this;
	}
	
}
