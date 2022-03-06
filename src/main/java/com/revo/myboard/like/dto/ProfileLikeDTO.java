package com.revo.myboard.like.dto;

import java.util.Date;

import com.revo.myboard.like.Like;

/*
 * Created By Revo
 */

public class ProfileLikeDTO extends LikeDTO {

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
		if (like.getPost() != null) {
			this.setPostTitle(like.getPost().getTitle());
			this.setLastActivity(like.getPost().getLastActiveDate());
		}
		return this;
	}

}
