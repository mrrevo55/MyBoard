package com.revo.myboard.post.dto;

import java.util.Date;
import java.util.List;

import com.revo.myboard.comment.dto.CommentDTO;
import com.revo.myboard.like.dto.LikeDTO;
import com.revo.myboard.post.Post;
import com.revo.myboard.report.dto.ReportDTO;

/*
 * Created By Revo
 */

public class PostDTO {

	private long id;
	private String user;
	private String title;
	private String content;
	private List<CommentDTO> comments;
	private Date date;
	private Date lastEditDate;
	private long category;
	private List<ReportDTO> reports;
	private List<LikeDTO> likes;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public List<CommentDTO> getComments() {
		return comments;
	}
	public Date getDate() {
		return date;
	}
	public Date getLastEditDate() {
		return lastEditDate;
	}
	public long getCategory() {
		return category;
	}
	public List<ReportDTO> getReports() {
		return reports;
	}
	public List<LikeDTO> getLikes() {
		return likes;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setLastEditDate(Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}
	public void setCategory(long category) {
		this.category = category;
	}
	public void setReports(List<ReportDTO> reports) {
		this.reports = reports;
	}
	public void setLikes(List<LikeDTO> likes) {
		this.likes = likes;
	}
	
	public PostDTO mapFromPost(Post post) {
		this.setId(post.getId());
		this.setCategory(post.getCategory().getId());
		this.setComments(post.getComments().stream().map(comment -> new CommentDTO().mapFromComment(comment)).toList());
		this.setContent(post.getContent());
		this.setDate(post.getDate());
		this.setLastEditDate(post.getLastEditDate());
		this.setLikes(post.getMyLikes().stream().map(like -> new LikeDTO().mapFromLike(like)).toList());
		this.setReports(post.getReports().stream().map(report -> new ReportDTO().mapFromReport(report)).toList());
		this.setTitle(post.getTitle());
		this.setUser(post.getUser().getLogin());
		return this;
	}
}
