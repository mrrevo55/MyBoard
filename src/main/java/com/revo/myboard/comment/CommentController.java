package com.revo.myboard.comment;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.comment.dto.CommentDTO;
import com.revo.myboard.comment.dto.ContentDTO;
import com.revo.myboard.comment.dto.CreateDTO;
import com.revo.myboard.security.annotation.ForUser;

/*
 *  Created By Revo
 */

@RestController
@RequestMapping("/comment")
@Validated
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
	public void createComment(@RequestHeader("Authorization") String token, @RequestBody @Valid CreateDTO createDTO) {
		commentService.createComment(token, createDTO.getPost(), createDTO.getContent());
	}

	@PatchMapping("/edit/{id}")
	@ForUser
	public void editCommentById(@RequestHeader("Authorization") String token, @PathVariable long id,
			@RequestBody @Valid ContentDTO editDTO) {
		commentService.editCommentById(token, id, editDTO.getNewContent());
	}

	@DeleteMapping("/delete/{id}")
	@ForUser
	public void deleteCommentByIdAsUser(@RequestHeader("Authorization") String token, @PathVariable long id) {
		commentService.deleteCommentById(token, id);
	}

	@PostMapping("/like/{id}")
	@ForUser
	@ResponseStatus(HttpStatus.CREATED)
	public void giveLikeForCommentById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		commentService.giveLikeForCommentById(token, id);
	}

	@DeleteMapping("/unlike/{id}")
	@ForUser
	public void removeLikeFromCommentById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		commentService.removeLikeFromCommentById(token, id);
	}

}
