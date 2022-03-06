package com.revo.myboard.user.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.revo.myboard.comment.Comment;
import com.revo.myboard.post.Post;
import com.revo.myboard.user.User;

/*
 * Created By Revo
 */

public class SearchDTO {

	private String login;
	private Date lastActivity;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Date getLastActivity() {
		return lastActivity;
	}
	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}
	
	public SearchDTO mapFromUser(User user) {
		this.setLastActivity(this.getLastActivity(user));
		this.setLogin(user.getLogin());
		return this;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date getLastActivity(User user) {
		Date last = null;
		for(Post post : user.getPosts()) {
			if(last == null) {
				last = post.getLastActiveDate();
			}
			if(last.before(post.getLastActiveDate())) {
				last = post.getLastActiveDate();
			}
		}
		for(Comment comment : user.getComments()) {
			if(last == null) {
				comment.getLastEditDate();
			}
			if(last.before(comment.getLastEditDate())) {
				last = comment.getLastEditDate();
			}
		}
		return last;
	}
}
