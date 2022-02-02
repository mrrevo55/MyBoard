package com.revo.myboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.annotations.ForModerator;
import com.revo.myboard.annotations.ForUser;
import com.revo.myboard.services.CommentService;

/*
 *  Post Controller
 *  
 *  Created By Revo
 */

@RestController
@RequestMapping("/comment")
public class CommentController {

	/*
	 * Data
	 */
	
	@Autowired
	private CommentService commentService;
	
	//USER
	
	/*
	 * Create
	 */
	
	@GetMapping("/create")
	@ForUser
	public boolean create(@RequestHeader("Authorization") String token, @RequestParam String title, @RequestParam String content) {
		return commentService.create(token, title, content);
	}
	
	/*
	 * Edit
	 */
	
	@GetMapping("/edit/logged")
	@ForUser
	public boolean edit(@RequestHeader("Authorization") String token, @RequestParam long id, @RequestParam String content) {
		return commentService.edit(token, id, content);
	}
	
	/*
	 * Delete
	 */
	
	@GetMapping("/delete/logged")
	@ForUser
	public boolean delete(@RequestHeader("Authorization") String token, @RequestParam long id) {
		return commentService.delete(token, id);
	}
	
	/*
	 * Give Like
	 */
	
	@GetMapping("/like")
	@ForUser
	public boolean like(@RequestHeader("Authorization") String token, @RequestParam long id) {
		return commentService.giveLike(token, id);
	}
	
	/*
	 * Remove Like
	 */
	
	@GetMapping("/unlike")
	@ForUser
	public boolean unlike(@RequestHeader("Authorization") String token, @RequestParam long id) {
		return commentService.removeLike(token, id);
	}
	
	//MODERATOR
	
	/*
	 * Edit
	 */
	
	@GetMapping("/edit")
	@ForModerator
	public boolean edit(@RequestParam long id, @RequestParam String content) {
		return commentService.edit(id, content);
	}
	
	/*
	 * Delete
	 */
	
	@GetMapping("/delete")
	@ForModerator
	public boolean delete(@RequestParam long id) {
		return commentService.delete(id);
	}
	
}
