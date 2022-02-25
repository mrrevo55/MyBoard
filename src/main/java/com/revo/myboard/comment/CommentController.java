package com.revo.myboard.comment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	 * PUBLIC
	 */
	
	@GetMapping("/{id}")
	public CommentDTO getCommentById(@PathVariable long id) {
		return new CommentDTO().mapFromComment(commentService.getCommentById(id));
	}
	
	/*
	 * USER
	 */

	@PostMapping("/create")
	@ForUser
	@ResponseStatus(HttpStatus.CREATED)
	public void createComment(@RequestHeader("Authorization") String token, @RequestBody CreateDTO commentDTO) {
		commentService.createComment(token, commentDTO.getPost(), commentDTO.getContent());
	}
	
	@PutMapping("/edit/{id}/logged")
	@ForUser
	public void editCommentById(@RequestHeader("Authorization") String token, @PathVariable long id, @RequestParam String new_content) {
		commentService.editCommentByIdAsUser(token, id, new_content);
	}
	
	@DeleteMapping("/delete/{id}/logged")
	@ForUser
	public void deleteCommentByIdAsUser(@RequestHeader("Authorization") String token, @PathVariable long id) {
		commentService.deleteCommentByIdAsUser(token, id);
	}
	
	@PostMapping("/like/{id}")
	@ForUser
	public void giveLikeForCommentById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		commentService.giveLikeForCommentById(token, id);
	}
	
	@DeleteMapping("/unlike/{id}")
	@ForUser
	public void removeLikeFromCommentById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		commentService.removeLikeFromCommentById(token, id);
	}
	
	/*
	 * MODERATOR
	 */
	
	@PutMapping("/edit/{id}")
	@ForModerator
	public void editCommentById(@PathVariable long id, @RequestParam String new_content) {
		commentService.editCommentById(id, new_content);
	}
	
	@DeleteMapping("/delete/{id}")
	@ForModerator
	public void deleteCommentById(@PathVariable long id) {
		commentService.deleteCommentById(id);
	}
	
}
