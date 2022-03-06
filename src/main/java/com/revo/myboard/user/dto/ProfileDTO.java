package com.revo.myboard.user.dto;

import java.util.List;

import com.revo.myboard.comment.dto.ShortCommentDTO;
import com.revo.myboard.like.dto.ProfileLikeDTO;
import com.revo.myboard.post.dto.ShortPostDTO;
import com.revo.myboard.report.dto.ReportDTO;
import com.revo.myboard.user.User;

public class ProfileDTO {
	
	private String login;
	private String email;
	private List<ShortPostDTO> posts;
	private List<ShortCommentDTO> comments;
	private List<ReportDTO> reports;
	private List<ProfileLikeDTO> liked;
	private String group;
	private DataDTO data;
	
	public String getLogin() {
		return login;
	}
	public String getEmail() {
		return email;
	}
	public List<ShortPostDTO> getPosts() {
		return posts;
	}
	public List<ShortCommentDTO> getComments() {
		return comments;
	}
	public List<ReportDTO> getReports() {
		return reports;
	}
	public List<ProfileLikeDTO> getLiked() {
		return liked;
	}
	public String getGroup() {
		return group;
	}
	public DataDTO getData() {
		return data;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPosts(List<ShortPostDTO> posts) {
		this.posts = posts;
	}
	public void setComments(List<ShortCommentDTO> comments) {
		this.comments = comments;
	}
	public void setReports(List<ReportDTO> reports) {
		this.reports = reports;
	}
	public void setLiked(List<ProfileLikeDTO> liked) {
		this.liked = liked;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public void setData(DataDTO data) {
		this.data = data;
	}
	
	public ProfileDTO mapFromUser(User user) {
		this.setComments(user.getComments().stream().map(comment -> new ShortCommentDTO().mapFromComment(comment)).toList());
		this.setData(new DataDTO().mapFromData(user.getData()));
		this.setEmail(user.getEmail());
		this.setGroup(user.getGroup().getName());
		this.setLiked(user.getLiked().stream().map(like -> new ProfileLikeDTO().mapFromLike(like)).toList());
		this.setLogin(user.getLogin());
		this.setPosts(user.getPosts().stream().map(post -> new ShortPostDTO().mapFromPost(post)).toList());
		this.setReports(user.getReports().stream().map(report -> new ReportDTO().mapFromReport(report)).toList());
		return this;
	}
}
