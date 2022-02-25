package com.revo.myboard.comment;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.revo.myboard.like.Like;
import com.revo.myboard.post.Post;
import com.revo.myboard.report.Report;
import com.revo.myboard.user.User;

/*
 * Created By Revo
 */

@Entity
public final class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	private long id;
	@ManyToOne(optional = true, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	@NotEmpty
	@NotBlank
	@Min(5)
	private String content;
	@NotNull
	@OneToMany(mappedBy="comment", cascade=CascadeType.REMOVE)
	private List<Like> myLikes;
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastEditDate;
	@OneToMany(mappedBy="comment", cascade=CascadeType.REMOVE)
	private List<Report> reports;
	@NotNull
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name="post_id")
	private Post post;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getAuthor() {
		return user;
	}
	public void setAuthor(User author) {
		this.user = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<Report> getReports() {
		return reports;
	}
	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
	public User getUser() {
		return user;
	}
	public List<Like> getMyLikes() {
		return myLikes;
	}
	public Post getPost() {
		return post;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setMyLikes(List<Like> myLikes) {
		this.myLikes = myLikes;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public Date getLastEditDate() {
		return lastEditDate;
	}
	public void setLastEditDate(Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}
	
}
