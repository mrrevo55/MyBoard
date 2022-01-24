package com.revo.myboard.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

/*
 * Post Entity Class
 * 
 * 
 * Created By Revo
 */

@Entity
public final class Post {

	/*
	 * Data
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	@Column(unique=true)
	private long id;
	@NotNull
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	@NotNull
	@Size(min=5, max=50)
	@Column(unique=true)
	private String title;
	@NotNull
	@Min(5)
	private String content;
	@NotNull
	@OneToMany(mappedBy="post", fetch=FetchType.LAZY)
	private List<Comment> comments;
	@NotNull
	private int likes;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	@NotNull
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name="category_id")
	private Category category;
	@NotNull
	@OneToMany(mappedBy="post", fetch=FetchType.LAZY)
	private List<Report> reports;
	
	/*
	 * Getters & Setters
	 */
	
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
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
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
	
}
