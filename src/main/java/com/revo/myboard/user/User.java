package com.revo.myboard.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.revo.myboard.comment.Comment;
import com.revo.myboard.group.Group;
import com.revo.myboard.like.Like;
import com.revo.myboard.post.Post;
import com.revo.myboard.report.Report;

/*
 * Created By Revo
 */

@Entity
public final class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(unique=true)
	private String login;
	private String password;
	@Column(unique=true)
	private String email;
	private boolean blocked;
	private boolean active;
	@OneToMany(mappedBy="user")
	private List<Post> posts;
	@OneToMany(mappedBy="user")
	private List<Comment> comments;
	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="reporter")
	private List<Report> reports;
	@Embedded
	private Data data;
	@OneToMany(mappedBy="who", cascade=CascadeType.REMOVE)
	private List<Like> liked;
	@OneToOne
	@JoinColumn(referencedColumnName = "id", name="group_id")
	private Group group;
	private String code;
	
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
	public List<Like> getLiked() {
		return liked;
	}
	public void setLiked(List<Like> liked) {
		this.liked = liked;
	}
	
}
