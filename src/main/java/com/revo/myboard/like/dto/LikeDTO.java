package com.revo.myboard.like.dto;

import com.revo.myboard.like.Like;

/*
 * Created By Revo
 */

public class LikeDTO {

	private long post;
	private long comment;
	private String who;
	
	public long getPost() {
		return post;
	}
	public long getComment() {
		return comment;
	}
	public String getWho() {
		return who;
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
		this.setWho(like.getWho().getLogin());
		return this;
	}
	
}
