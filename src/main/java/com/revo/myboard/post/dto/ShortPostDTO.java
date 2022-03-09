package com.revo.myboard.post.dto;

import java.util.Date;

import com.revo.myboard.post.Post;

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
public class ShortPostDTO {

	private long id;
	private String title;
	private Date lastActivity;
	private String author;
	private int answers;

	public static ShortPostDTO mapFromPost(Post post) {
		return ShortPostDTO.builder().id(post.getId()).lastActivity(post.getLastActiveDate()).title(post.getTitle())
				.author(post.getAuthor().getLogin()).answers(post.getComments().size()).build();
	}

}
