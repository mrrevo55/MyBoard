package com.revo.myboard.post.dto;

import java.util.Date;
import java.util.List;

import com.revo.myboard.comment.dto.CommentDTO;
import com.revo.myboard.like.dto.LikeDTO;
import com.revo.myboard.post.Post;
import com.revo.myboard.report.dto.ReportDTO;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * Created By Revo
 */

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
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

	public static PostDTO mapFromPost(Post post) {
		return PostDTO.builder().id(post.getId()).category(post.getCategory().getId())
				.comments(post.getComments().stream().map(comment -> CommentDTO.mapFromComment(comment)).toList())
				.content(post.getContent()).date(post.getDate()).lastEditDate(post.getLastEditDate())
				.likes(post.getMyLikes().stream().map(like -> LikeDTO.mapFromLike(like)).toList())
				.reports(post.getReports().stream().map(report -> ReportDTO.mapFromReport(report)).toList())
				.title(post.getTitle()).user(post.getAuthor().getLogin()).build();
	}
}
