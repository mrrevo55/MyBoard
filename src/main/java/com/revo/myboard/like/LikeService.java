package com.revo.myboard.like;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.comment.CommentService;
import com.revo.myboard.exception.HasLikeBeforeException;
import com.revo.myboard.exception.ObjectNotExistsException;
import com.revo.myboard.post.PostService;
import com.revo.myboard.user.UserService;

import lombok.AllArgsConstructor;

/*
 * Created By Revo
 */

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class LikeService {

	private final LikeRepository repository;
	private final UserService userService;
	private final PostService postService;
	private final CommentService commentService;

	public void giveForPostById(String token, long post_id) {
		var user = userService.currentLoggedUser(token);
		var post = postService.getPostById(post_id);
		if (!post.getMyLikes().stream().filter(like -> like.getWho().equals(user)).toList().isEmpty()) {
			throw new HasLikeBeforeException(String.valueOf(post_id));
		}
		repository.save(Like.builder().who(user).post(post).build());
	}

	public void giveForCommentById(String token, long comment_id) {
		var user = userService.currentLoggedUser(token);
		var comment = commentService.getCommentById(comment_id);
		if (!comment.getMyLikes().stream().filter(like -> like.getWho().equals(user)).toList().isEmpty()) {
			throw new HasLikeBeforeException(String.valueOf(comment_id));
		}
		repository.save(Like.builder().who(user).comment(comment).build());
	}

	public void removeFromPostById(String token, long id) {
		List<Like> likes = postService.getPostById(id).getMyLikes().stream()
				.filter(like -> like.getWho().equals(userService.currentLoggedUser(token))).toList();
		if (likes.isEmpty()) {
			throw new ObjectNotExistsException(String.valueOf(id));
		}
		repository.delete(likes.get(0));
	}

	public void removeFromCommentById(String token, long id) {
		List<Like> likes = commentService.getCommentById(id).getMyLikes().stream()
				.filter(like -> like.getWho().equals(userService.currentLoggedUser(token))).toList();
		if (likes.isEmpty()) {
			throw new ObjectNotExistsException(String.valueOf(id));
		}
		repository.delete(likes.get(0));
	}

}
