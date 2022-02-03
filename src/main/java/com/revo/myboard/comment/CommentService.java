package com.revo.myboard.comment;

import java.sql.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.like.LikeService;
import com.revo.myboard.post.PostService;
import com.revo.myboard.user.UserService;
import com.revo.myboard.util.ObjectNotExistsException;

/*
 * Created By Revo
 */

@Service
@Transactional
public class CommentService {

	private CommentRepository repo;
	private UserService userService;
	private PostService postService;
	private LikeService likeService;

	public CommentService(CommentRepository repo, UserService userService, PostService postService,
			LikeService likeService) {
		this.repo = repo;
		this.userService = userService;
		this.postService = postService;
		this.likeService = likeService;
	}

	public boolean createComment(String token, String post_title, String content) {
		var user = userService.currentLoggedUser(token);
		if (user == null) {
			return false;
		}
		var post = postService.getPostByTitle(post_title);
		if (post == null) {
			return false;
		}
		var comment = new Comment();
		comment.setAuthor(user);
		comment.setContent(content);
		comment.setDate(new Date(System.currentTimeMillis()));
		comment.setPost(post);
		repo.save(comment);
		return true;
	}

	public Comment getCommentById(long id) {
		return repo.findById(id).orElseThrow(() -> new ObjectNotExistsException("C" + String.valueOf(id)));
	}

	public boolean editCommentById(long id, String content) {
		var comment = getCommentById(id);
		if (comment == null){
			return false;
		}
		comment.setContent(content);
		comment.setLastEditDate(new Date(System.currentTimeMillis()));
		return true;
	}

	public boolean editCommentById(String token, long id, String content) {
		var user = userService.currentLoggedUser(token);
		if (user == null || !user.getComments().contains(getCommentById(id))){
			return false;
		}
		return editCommentById(id, content);
	}

	public boolean deleteCommentById(long id) {
		var comment = getCommentById(id);
		if (comment == null){
			return false;
		}
		repo.delete(comment);
		return true;
	}

	public boolean deleteCommentByIdAsUser(String token, long id) {
		for (var target : userService.currentLoggedUser(token).getComments()) {
			if (target.getId() == id) {
				repo.delete(target);
				return true;
			}
		}
		return false;
	}

	public boolean giveLikeForCommentById(String token, long id) {
		return likeService.giveForCommentById(token, id);
	}

	public boolean removeLikeFromCommentById(String token, long id) {
		return likeService.removeFromCommentById(token, id);
	}

}
