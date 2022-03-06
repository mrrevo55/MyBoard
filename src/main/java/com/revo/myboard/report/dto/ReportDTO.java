package com.revo.myboard.report.dto;

import java.util.Date;

import com.revo.myboard.report.Report;

/*
 * Created By Revo
 */

public class ReportDTO {

	private long id;
	private String reporter;
	private long post;
	private String postTitle;
	private long comment;
	private Date date;
	private boolean checked;
	private String content;
	
	public long getId() {
		return id;
	}
	public String getReporter() {
		return reporter;
	}
	public long getPost() {
		return post;
	}
	public long getComment() {
		return comment;
	}
	public Date getDate() {
		return date;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public void setPost(long post) {
		this.post = post;
	}
	public void setComment(long comment) {
		this.comment = comment;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isChecked() {
		return checked;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ReportDTO mapFromReport(Report report) {
		this.setId(report.getId());
		if(report.getComment() != null) {
			this.setComment(report.getComment().getId());
		}
		this.setDate(report.getDate());
		if(report.getPost() != null) {
			this.setPost(report.getPost().getId());
			this.setPostTitle(report.getPost().getTitle());
		}else {
			this.setPost(report.getComment().getPost().getId());
			this.setPostTitle(report.getComment().getPost().getTitle());
		}
		this.setReporter(report.getReporter().getLogin());
		this.setChecked(report.isChecked());
		this.setContent(report.getContent());
		return this;
	}
	
}
