package com.revo.myboard.post;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import javax.persistence.Temporal;

import com.revo.myboard.category.Category;
import com.revo.myboard.comment.Comment;
import com.revo.myboard.like.Like;
import com.revo.myboard.report.Report;
import com.revo.myboard.user.User;

/*
 * Created By Revo
 */

@Entity
public final class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true)
	private long id;
	@ManyToOne(optional = true, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	private String title;
	@Type(type="text")
	private String content;
	@OneToMany(mappedBy="post", cascade=CascadeType.REMOVE)
	private List<Comment> comments;
	@OneToMany(mappedBy="post", cascade=CascadeType.REMOVE)
	private List<Like> myLikes;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastEditDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastActiveDate;
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name="category_id")
	private Category category;
	@OneToMany(mappedBy="post", cascade=CascadeType.REMOVE)
	private List<Report> reports;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public Date getLastActiveDate() {
		return lastActiveDate;
	}
	public void setLastActiveDate(Date lastActiveDate) {
		this.lastActiveDate = lastActiveDate;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
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
	public void setUser(User user) {
		this.user = user;
	}
	public void setMyLikes(List<Like> myLikes) {
		this.myLikes = myLikes;
	}
	public Date getLastEditDate() {
		return lastEditDate;
	}
	public void setLastEditDate(Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}
	
}
