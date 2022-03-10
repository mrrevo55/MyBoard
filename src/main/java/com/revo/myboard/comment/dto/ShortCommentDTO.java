package com.revo.myboard.comment.dto;

import com.revo.myboard.comment.Comment;

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
public class ShortCommentDTO {
	
	private long id;
	private String user;
	private long post;

	public static ShortCommentDTO mapFromComment(Comment comment) {
		return ShortCommentDTO.builder().id(comment.getId()).post(comment.getPost().getId())
				.user(comment.getAuthor().getLogin()).build();
	}

}
