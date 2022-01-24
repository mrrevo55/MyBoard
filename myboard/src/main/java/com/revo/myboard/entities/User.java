package com.revo.myboard.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
 * User Entity Class
 * 
 * 
 * Created By Revo
 */

@Entity
public final class User {

	/*
	 * Data
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	private long id;
	@NotNull
	@Column(unique=true)
	@Size(min=5, max=20)
	private String login;
	@NotNull
	@Min(8)
	private String password;
	@NotNull
	@Email
	@Column(unique=true)
	private String email;
	@NotNull
	private boolean blocked;
	@NotNull
	private boolean active;
	@NotNull
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="user", fetch=FetchType.LAZY)
	private List<Post> posts;
	@NotNull
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="user", fetch=FetchType.LAZY)
	private List<Comment> comments;
	@NotNull
	@OneToMany(cascade= {CascadeType.REMOVE,CascadeType.PERSIST}, mappedBy="user", fetch=FetchType.LAZY)
	private List<Report> reports;
	@NotNull
	@Embedded
	private Data data;
	@NotNull
	@Column(columnDefinition = "integer default 0")
	private int likes;
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="group_name")
	private Group group;
	@NotNull
	private String code;
	
	/*
	 * Getters & Setters
	 */
	
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public List<Report> getReports() {
		return reports;
	}
	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
