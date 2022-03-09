package com.revo.myboard.comment;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 *  Created By Revo
 */

@RestController
@RequestMapping("/comment")
@Validated
@AllArgsConstructor
@Slf4j
public class CommentController {

	private final CommentService commentService;

	/*
	 * PUBLIC
	 */

	@GetMapping("/{id}")
	public ResponseEntity<CommentDTO> getCommentById(@PathVariable long id, HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " getting comment with id: " + id);
		var comment = CommentDTO.mapFromComment(commentService.getCommentById(id));
		log.info("User with ip: " + request.getRemoteAddr() + " success got comment with id: " + id);
		return ResponseEntity.ok(comment);
	}

	/*
	 * USER
	 */

	@PostMapping("/create")
	@ForUser
	@ResponseStatus(HttpStatus.CREATED)
	public void createComment(@RequestHeader("Authorization") String token, @RequestBody @Valid CreateDTO createDTO,
			HttpServletRequest request) {
		log.info(
				"User with ip: " + request.getRemoteAddr() + " creating comment with details: " + createDTO.toString());
		commentService.createComment(token, createDTO.getPost(), createDTO.getContent());
		log.info("User with ip: " + request.getRemoteAddr() + " success created comment with details: "
				+ createDTO.toString());
	}

	@PatchMapping("/edit/{id}")
	@ForUser
	public void editCommentById(@RequestHeader("Authorization") String token, @PathVariable long id,
			@RequestBody @Valid ContentDTO editDTO, HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " editing comment with id: " + id + " with details: "
				+ editDTO.toString());
		commentService.editCommentById(token, id, editDTO.getNewContent());
		log.info("User with ip: " + request.getRemoteAddr() + " success edited comment with id: " + id
				+ " with details: " + editDTO.toString());
	}

	@DeleteMapping("/delete/{id}")
	@ForUser
	public void deleteCommentByIdAsUser(@RequestHeader("Authorization") String token, @PathVariable long id,
			HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + "deleting comment with id: " + id);
		commentService.deleteCommentById(token, id);
		log.info("User with ip: " + request.getRemoteAddr() + "success deleted comment with id: " + id);
	}

	@PostMapping("/like/{id}")
	@ForUser
	@ResponseStatus(HttpStatus.CREATED)
	public void giveLikeForCommentById(@RequestHeader("Authorization") String token, @PathVariable long id,
			HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " giving like for comment with id: " + id);
		commentService.giveLikeForCommentById(token, id);
		log.info("User with ip: " + request.getRemoteAddr() + " success gived like for comment with id: " + id);
	}

	@DeleteMapping("/unlike/{id}")
	@ForUser
	public void removeLikeFromCommentById(@RequestHeader("Authorization") String token, @PathVariable long id,
			HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " removing like from comment with id: " + id);
		commentService.removeLikeFromCommentById(token, id);
		log.info("User with ip: " + request.getRemoteAddr() + " success removed like from comment with id: " + id);
	}

}
