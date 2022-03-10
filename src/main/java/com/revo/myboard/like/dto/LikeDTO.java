package com.revo.myboard.like.dto;

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
public class LikeDTO {

	private long post;
	private long comment;
	private String who;

	public static LikeDTO mapFromLike(Like like) {
		if(like.getComment() != null) {
			return LikeDTO.builder().comment(like.getComment().getId())
					.who(like.getWho().getLogin()).build();
		} else {
			return LikeDTO.builder().post(like.getPost().getId())
					.who(like.getWho().getLogin()).build();
		}
	}

}
