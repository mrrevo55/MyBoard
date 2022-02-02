package com.revo.myboard.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/*
 * Like Entity Class
 * 
 * 
 * Created By Revo
 */

@Entity
@Table(name="likes")
public class Like {
	
	/*
	 * Data
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	private long id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="post_id")
	private Post post;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="comment_id")
	private Comment comment;
	@NotNull
	@ManyToOne
	@JoinColumn(name="who_id")
	private User who;
	
	/*
	 * Getters & Setters
	 */
	
	public long getId() {
		return id;
	}
	public User getUser() {
		return user;
	}
	public Post getPost() {
		return post;
	}
	public Comment getComment() {
		return comment;
	}
	public User getWho() {
		return who;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public void setWho(User who) {
		this.who = who;
	}
	
}
