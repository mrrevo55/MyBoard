package com.revo.myboard.like.dto;

import java.util.Date;

import com.revo.myboard.like.Like;

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
public class ProfileLikeDTO {

	private long post;
	private long comment;
	private String who;
	private String postTitle;
	private Date lastActivity;

	public static ProfileLikeDTO mapFromLike(Like like) {
		if (like.getComment() != null) {
			return ProfileLikeDTO.builder().comment(like.getComment().getId()).who(like.getWho().getLogin()).build();
		} else {
			return ProfileLikeDTO.builder().post(like.getPost().getId()).who(like.getWho().getLogin())
					.postTitle(like.getPost().getTitle()).lastActivity(like.getPost().getLastActiveDate()).build();
		}

	}

}
