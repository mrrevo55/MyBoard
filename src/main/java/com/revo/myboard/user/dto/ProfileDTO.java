package com.revo.myboard.user.dto;

import java.util.List;

import com.revo.myboard.comment.dto.ShortCommentDTO;
import com.revo.myboard.like.dto.ProfileLikeDTO;
import com.revo.myboard.post.dto.ShortPostDTO;
import com.revo.myboard.report.dto.ReportDTO;
import com.revo.myboard.user.User;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class ProfileDTO {

	private String login;
	private String email;
	private List<ShortPostDTO> posts;
	private List<ShortCommentDTO> comments;
	private List<ReportDTO> reports;
	private List<ProfileLikeDTO> liked;
	private String group;
	private DataDTO data;

	public static ProfileDTO mapFromUser(User user) {
		return ProfileDTO.builder()
				.data(DataDTO.mapFromData(user.getData()))
				.comments(user.getComments().stream().map(comment -> ShortCommentDTO.mapFromComment(comment)).toList())
				.email(user.getEmail()).group(user.getGroup().getName())
				.liked(user.getLiked().stream().map(like -> ProfileLikeDTO.mapFromLike(like)).toList())
				.login(user.getLogin())
				.posts(user.getPosts().stream().map(post -> ShortPostDTO.mapFromPost(post)).toList())
				.reports(user.getReports().stream().map(report -> ReportDTO.mapFromReport(report)).toList()).build();
	}
}
