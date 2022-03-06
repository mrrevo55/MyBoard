package com.revo.myboard.like;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.revo.myboard.comment.Comment;
import com.revo.myboard.post.Post;
import com.revo.myboard.user.User;

/*
 * Created By Revo
 */

@Entity
@Table(name="likes")
public class Like {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@ManyToOne(optional = true, fetch=FetchType.LAZY)
	@JoinColumn(name="post_id")
	private Post post;
	@ManyToOne(optional = true, fetch=FetchType.LAZY)
	@JoinColumn(name="comment_id")
	private Comment comment;
	@ManyToOne
	@JoinColumn(name="who_id")
	private User who;
	
	public long getId() {
		return id;
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
