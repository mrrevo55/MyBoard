package com.revo.myboard.comment;

import java.sql.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.exception.ObjectNotExistsException;
import com.revo.myboard.group.Authority;
import com.revo.myboard.like.LikeService;
import com.revo.myboard.post.PostService;
import com.revo.myboard.user.UserService;

import lombok.AllArgsConstructor;

/*
 * Created By Revo
 */

@Service
@Transactional
@AllArgsConstructor
public class CommentService {

	private final CommentRepository repository;
	private final UserService userService;
	private final PostService postService;
	private final LikeService likeService;

	public void createComment(String token, long post_id, String content) {
		postService.getPostById(post_id).setLastActiveDate(new Date(System.currentTimeMillis()));
		repository.save(Comment.builder().author(userService.currentLoggedUser(token)).content(content)
				.date(new Date(System.currentTimeMillis())).post(postService.getPostById(post_id))
				.lastEditDate(new Date(System.currentTimeMillis())).build());
	}

	public Comment getCommentById(long id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotExistsException(String.valueOf(id)));
	}

	public void editCommentById(String token, long id, String content) {
		var user = userService.currentLoggedUser(token);
		if (user.getGroup().getAuthority().equals(Authority.USER) && !user.getComments().contains(getCommentById(id))) {
			throw new ObjectNotExistsException(String.valueOf(id));
		}
		var comment = getCommentById(id);
		comment.setContent(content);
		comment.setLastEditDate(new Date(System.currentTimeMillis()));
		postService.getPostById(comment.getPost().getId()).setLastActiveDate(new Date(System.currentTimeMillis()));
	}

	public void deleteCommentById(String token, long id) {
		var user = userService.currentLoggedUser(token);
		if (user.getGroup().getAuthority().equals(Authority.USER)) {
			if(user.getComments().stream().filter(target -> target.getId() == id).toList().isEmpty()) {
				throw new ObjectNotExistsException(String.valueOf(id));
			}
		}
		repository.delete(getCommentById(id));
	}

	public void giveLikeForCommentById(String token, long id) {
		likeService.giveForCommentById(token, id);
	}

	public void removeLikeFromCommentById(String token, long id) {
		likeService.removeFromCommentById(token, id);
	}

}
