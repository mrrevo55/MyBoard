package com.revo.myboard.comment;

import java.sql.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.group.Authority;
import com.revo.myboard.like.LikeService;
import com.revo.myboard.post.PostService;
import com.revo.myboard.user.UserService;

/*
 * Created By Revo
 */

@Service
@Transactional
public class CommentService {

	private CommentRepository repository;
	private UserService userService;
	private PostService postService;
	private LikeService likeService;

	public CommentService(CommentRepository repository, UserService userService, PostService postService,
			LikeService likeService) {
		this.repository = repository;
		this.userService = userService;
		this.postService = postService;
		this.likeService = likeService;
	}

	public void createComment(String token, long post_id, String content) {
		var comment = new Comment();
		comment.setAuthor(userService.currentLoggedUser(token));
		comment.setContent(content);
		comment.setDate(new Date(System.currentTimeMillis()));
		postService.getPostById(post_id).setLastActiveDate(new Date(System.currentTimeMillis()));
		comment.setPost(postService.getPostById(post_id));
		comment.setLastEditDate(new Date(System.currentTimeMillis()));
		repository.save(comment);
	}

	public Comment getCommentById(long id) {
		return repository.findById(id).orElseThrow(() -> new NullPointerException("C:"+id));
	}

	public void editCommentById(String token, long id, String content) {
		var user = userService.currentLoggedUser(token);
		if(user.getGroup().getAuthority().equals(Authority.USER) && !user.getComments().contains(getCommentById(id))){
			throw new NullPointerException("C:"+id);
		}
		var comment = getCommentById(id);
		comment.setContent(content);
		comment.setLastEditDate(new Date(System.currentTimeMillis()));
		postService.getPostById(comment.getPost().getId()).setLastActiveDate(new Date(System.currentTimeMillis()));
	}

	public void deleteCommentById(String token, long id) {
		var user = userService.currentLoggedUser(token);
		if(user.getGroup().getAuthority().equals(Authority.USER)) {
			for (var target : user.getComments()) {
				if (target.getId() == id) {
					repository.delete(target);
					return;
				}
			}
			throw new NullPointerException("C:"+id);
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
