package com.revo.myboard.like;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.comment.CommentService;
import com.revo.myboard.post.PostService;
import com.revo.myboard.user.UserService;

/*
 * Created By Revo
 */

@Service
@Transactional
public class LikeService {

	private LikeRepository repository;
	private UserService userService;
	private PostService postService;
	private CommentService commentService;

	public LikeService(LikeRepository repository, @Lazy UserService userService, @Lazy PostService postService,
			@Lazy CommentService commentService) {
		this.repository = repository;
		this.userService = userService;
		this.postService = postService;
		this.commentService = commentService;
	}

	public void giveForPostById(String token, long post_id) {
		var user = userService.currentLoggedUser(token);
		var post = postService.getPostById(post_id);
		for (var like : post.getMyLikes()) {
			if (like.getWho().equals(user)) {
				throw new IllegalArgumentException(String.valueOf(post_id));
			}
		}
		var like = new Like();
		like.setWho(user);
		like.setPost(post);
		repository.save(like);
	}

	public void giveForCommentById(String token, long comment_id) {
		var user = userService.currentLoggedUser(token);
		var comment = commentService.getCommentById(comment_id);
		for (var like : comment.getMyLikes()) {
			if (like.getWho().equals(user)) {
				throw new IllegalArgumentException("C:" + comment_id);
			}
		}
		var like = new Like();
		like.setWho(user);
		like.setComment(comment);
		repository.save(like);
	}

	public void removeFromPostById(String token, long id) {
		var user = userService.currentLoggedUser(token);
		for (var like : postService.getPostById(id).getMyLikes()) {
			if (like.getWho().equals(user)) {
				repository.delete(like);
				return;
			}
		}
		throw new NullPointerException(String.valueOf(id));
	}

	public void removeFromCommentById(String token, long id) {
		var user = userService.currentLoggedUser(token);
		for (var like : commentService.getCommentById(id).getMyLikes()) {
			if (like.getWho().equals(user)) {
				repository.delete(like);
				return;
			}
		}
		throw new NullPointerException("C:" + id);
	}

}
