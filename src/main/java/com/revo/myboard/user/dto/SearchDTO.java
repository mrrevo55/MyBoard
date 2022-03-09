package com.revo.myboard.user.dto;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.revo.myboard.comment.Comment;
import com.revo.myboard.post.Post;
import com.revo.myboard.user.User;

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
public class SearchDTO {

	private String login;
	private Date lastActivity;

	public static SearchDTO mapFromUser(User user) {
		return SearchDTO.builder().login(user.getLogin()).lastActivity(SearchDTO.getLastActivity(user)).build();
	}
	
	private static Date getLastActivity(User user) {
		List<Post> sortedPosts = user.getPosts();
		Collections.sort(sortedPosts, (p1, p2) -> p1.getLastActiveDate().before(p2.getLastActiveDate()) ? 1 : -1);
		List<Comment> sortedComments = user.getComments();
		Collections.sort(sortedComments, (p1, p2) -> p1.getLastEditDate().before(p2.getLastEditDate()) ? 1 : -1);
		if (!sortedPosts.isEmpty()) {
			if (!sortedComments.isEmpty()) {
				return sortedPosts.get(0).getLastActiveDate().before(sortedComments.get(0).getLastEditDate())
						? sortedComments.get(0).getLastEditDate()
						: sortedPosts.get(0).getLastActiveDate();
			}
			return sortedPosts.get(0).getLastActiveDate();
		}else {
			if (!sortedComments.isEmpty()) {
				return sortedComments.get(0).getLastEditDate();
			}
			return null;
		}
	}
}
