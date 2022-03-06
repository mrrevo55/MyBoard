package com.revo.myboard.comment.dto;

import java.util.Date;
import java.util.List;

import com.revo.myboard.comment.Comment;
import com.revo.myboard.like.dto.LikeDTO;
import com.revo.myboard.report.dto.ReportDTO;

/*
 * Created By Revo
 */

public class CommentDTO {
	
	private long id;
	private String user;
	private String content;
	private Date date;
	private Date lastEditDate;
	private List<ReportDTO> reports;
	private long post;
	private List<LikeDTO> likes;
	
	public long getId() {
		return id;
	}
	public String getUser() {
		return user;
	}
	public String getContent() {
		return content;
	}
	public Date getDate() {
		return date;
	}
	public Date getLastEditDate() {
		return lastEditDate;
	}
	public List<ReportDTO> getReports() {
		return reports;
	}
	public List<LikeDTO> getLikes() {
		return likes;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setLastEditDate(Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}
	public void setReports(List<ReportDTO> reports) {
		this.reports = reports;
	}
	public void setLikes(List<LikeDTO>  likes) {
		this.likes = likes;
	}
	public long getPost() {
		return post;
	}
	public void setPost(long post) {
		this.post = post;
	}
	
	public CommentDTO mapFromComment(Comment comment) {
		this.setContent(comment.getContent());
		this.setDate(comment.getDate());
		this.setId(comment.getId());
		this.setLastEditDate(comment.getLastEditDate());
		this.setLikes(comment.getMyLikes().stream().map(like -> new LikeDTO().mapFromLike(like)).toList());
		this.setReports(comment.getReports().stream().map(report -> new ReportDTO().mapFromReport(report)).toList());
		this.setUser(comment.getUser().getLogin());
		this.setPost(comment.getPost().getId());
		return this;
	}
	
}
