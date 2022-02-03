package com.revo.myboard.like;

import java.util.List;

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

	private LikeRepository repo;
	private UserService userService;
	private PostService postService;
	private CommentService commentService;

	public LikeService(LikeRepository repo, @Lazy UserService userService, @Lazy PostService postService,
			@Lazy CommentService commentService) {
		this.repo = repo;
		this.userService = userService;
		this.postService = postService;
		this.commentService = commentService;
	}

	public List<Like> getLikesByToken(String token) {
		return userService.currentLoggedUser(token).getMyLikes();
	}

	public List<Like> getLikedByToken(String token) {
		return userService.currentLoggedUser(token).getLiked();
	}

	public List<Like> getLikesByTitle(String title) {
		return postService.getPostByTitle(title).getMyLikes();
	}

	public List<Like> getLikesById(long id) {
		return commentService.getCommentById(id).getMyLikes();
	}

	public List<Like> getLikesByLogin(String token) {
		return userService.currentLoggedUser(token).getMyLikes();
	}

	public List<Like> getLikedByLogin(String login) {
		return userService.getUserByLogin(login).getLiked();
	}

	public boolean giveForUserByLogin(String token, String login) {
		var loggedUser = userService.currentLoggedUser(token);
		if (loggedUser == null) {
			return false;
		}
		var targetUser = userService.getUserByLogin(login);
		if (targetUser == null) {
			return false;
		}
		for (var like : targetUser.getMyLikes()) {
			if (like.getWho().equals(loggedUser)) {
				return false;
			}
		}
		var like = new Like();
		like.setWho(loggedUser);
		like.setUser(targetUser);
		repo.save(like);
		return true;
	}

	public boolean giveForPostByTitle(String token, String title) {
		var user = userService.currentLoggedUser(token);
		if (user == null) {
			return false;
		}
		var post = postService.getPostByTitle(title);
		if (post == null) {
			return false;
		}
		for (var like : post.getMyLikes()) {
			if (like.getWho().equals(user)) {
				return false;
			}
		}
		var like = new Like();
		like.setWho(user);
		like.setPost(post);
		repo.save(like);
		return true;
	}

	public boolean giveForCommentById(String token, long id) {
		var user = userService.currentLoggedUser(token);
		if (user == null) {
			return false;
		}
		var comment = commentService.getCommentById(id);
		if (comment == null) {
			return false;
		}
		for (var like : comment.getMyLikes()) {
			if (like.getWho().equals(user)) {
				return false;
			}
		}
		var like = new Like();
		like.setWho(user);
		like.setComment(comment);
		repo.save(like);
		return true;
	}

	public boolean removeFromUserByLogin(String token, String login) {
		var loggedUser = userService.currentLoggedUser(token);
		if (loggedUser != null) {
			var targetUser = userService.getUserByLogin(login);
			if (targetUser != null) {
				for (var like : targetUser.getMyLikes()) {
					if (like.getWho().equals(loggedUser)) {
						repo.delete(like);
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean removeFromPostByTitle(String token, String title) {
		var user = userService.currentLoggedUser(token);
		if (user != null) {
			var post = postService.getPostByTitle(title);
			if (post != null) {
				for (var like : post.getMyLikes()) {
					if (like.getWho().equals(user)) {
						repo.delete(like);
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean removeFromCommentById(String token, long id) {
		var user = userService.currentLoggedUser(token);
		if (user != null) {
			var comment = commentService.getCommentById(id);
			if (comment != null) {
				for (var like : comment.getMyLikes()) {
					if (like.getWho().equals(user)) {
						repo.delete(like);
						return true;
					}
				}
			}
		}
		return false;
	}

}
