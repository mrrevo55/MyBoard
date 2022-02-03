package com.revo.myboard.comment;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.security.annotation.ForModerator;
import com.revo.myboard.security.annotation.ForUser;

/*
 *  Created By Revo
 */

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	private CommentService commentService;	
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	/*
	 * USER
	 */

	@PostMapping("/create")
	@ForUser
	public boolean createComment(@RequestHeader("Authorization") String token, @RequestParam String title, @RequestParam String content) {
		return commentService.createComment(token, title, content);
	}
	
	@PutMapping("/edit/{id}/logged")
	@ForUser
	public boolean editCommentById(@RequestHeader("Authorization") String token, @PathVariable long id, @RequestParam String new_content) {
		return commentService.editCommentById(token, id, new_content);
	}
	
	@DeleteMapping("/delete/{id}/logged")
	@ForUser
	public boolean deleteCommentByIdAsUser(@RequestHeader("Authorization") String token, @PathVariable long id) {
		return commentService.deleteCommentByIdAsUser(token, id);
	}
	
	@PostMapping("/like/{id}")
	@ForUser
	public boolean giveLikeForCommentById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		return commentService.giveLikeForCommentById(token, id);
	}
	
	@PostMapping("/unlike/{id}")
	@ForUser
	public boolean removeLikeFromCommentById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		return commentService.removeLikeFromCommentById(token, id);
	}
	
	/*
	 * MODERATOR
	 */
	
	@PutMapping("/edit/{id}")
	@ForModerator
	public boolean editCommentById(@PathVariable long id, @RequestParam String new_content) {
		return commentService.editCommentById(id, new_content);
	}
	
	@DeleteMapping("/delete/{id}")
	@ForModerator
	public boolean deleteCommentById(@PathVariable long id) {
		return commentService.deleteCommentById(id);
	}
	
}
