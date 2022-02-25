package com.revo.myboard.like;

import java.util.Date;

/*
 * Created By Revo
 */

public class ProfileLikeDTO extends LikeDTO{

	private String postTitle;
	private Date lastActivity;
	
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public Date getLastActivity() {
		return lastActivity;
	}
	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}
	
	public ProfileLikeDTO mapFromLike(Like like) {
		super.mapFromLike(like);
		this.setPostTitle(like.getPost().getTitle());
		this.setLastActivity(like.getPost().getLastActiveDate());
		return this;
	}
	
}
