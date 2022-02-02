package com.revo.myboard.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.revo.myboard.annotations.ForModerator;
import com.revo.myboard.annotations.ForUser;
import com.revo.myboard.entities.Post;
import com.revo.myboard.services.PostService;
import com.revo.myboard.views.PostActivityView;
import com.revo.myboard.views.PostGetView;

/*
 *  Post Controller
 *  
 *  Created By Revo
 */

@RestController
@RequestMapping("/post")
public class PostController {
	
	/*
	 * Data
	 */
	
	@Autowired
	private PostService postService;
	
	//USER
	
	/*
	 * Get Posts By Token
	 */
	
	@GetMapping("/logged")
	@ForUser
	@JsonView(PostActivityView.class)
	public List<Post> getByLogged(@RequestHeader("Authorization") String token){
		return postService.getByLogged(token);
	}

	/*
	 * Get Posts By Login
	 */
	
	@GetMapping("/user")
	@ForUser
	@JsonView(PostActivityView.class)
	public List<Post> getByLogin(@RequestParam String login){
		return postService.getByLogin(login);
	}
	
	/*
	 * Get Post By Title
	 */
	
	@GetMapping
	@ForUser
	@JsonView(PostGetView.class)
	public Post get(@RequestParam String title) {
		return postService.get(title);
	}
	
	/*
	 * Create Post
	 */
	
	@GetMapping("/create")
	@ForUser
	public boolean create(@RequestHeader("Authorization") String token, @RequestParam String category, @RequestParam String title, @RequestParam String content) {
		return postService.create(token, category, title, content);
	}
	
	/*
	 * Delete Post
	 */
	
	@GetMapping("/delete/logged")
	@ForUser
	public boolean delete(@RequestHeader("Authorization") String token, @RequestParam String title) {
		return postService.delete(token, title);
	}
	
	/*
	 * Edit Title
	 */
	
	@GetMapping("/edit/title/logged")
	@ForUser
	public boolean editTitleByUser(@RequestHeader("Authorization") String token, @RequestParam String title, @RequestParam String new_title) {
		return postService.editTitle(token, title, new_title);
	}
	
	/*
	 * Edit Content
	 */
	
	@GetMapping("/edit/content/logged")
	@ForUser
	public boolean editContentByUser(@RequestHeader("Authorization") String token, @RequestParam String content, @RequestParam String new_content) {
		return postService.editContent(token, content, new_content);
	}
	
	/*
	 * Give Like
	 */
	
	@GetMapping("/like")
	@ForUser
	public boolean like(@RequestHeader("Authorization") String token, @RequestParam String title) {
		return postService.giveLike(token, title);
	}

	/*
	 * Remove Like
	 */
	
	@GetMapping("/unlike")
	@ForUser
	public boolean unlike(@RequestHeader("Authorization") String token, @RequestParam String title) {
		return postService.removeLike(token, title);
	}
	
	//MODERATOR
	
	/*
	 * Edit Title
	 */
	
	@GetMapping("/edit/title")
	@ForModerator
	public boolean editTitle(@RequestHeader("Authorization") String token, @RequestParam String title, @RequestParam String new_title) {
		return postService.editTitle(title, new_title);
	}
	
	/*
	 * Edit Content
	 */
	
	@GetMapping("/edit/content")
	@ForModerator
	public boolean editContent(@RequestHeader("Authorization") String token, @RequestParam String content, @RequestParam String new_content) {
		return postService.editContent(content, new_content);
	}
	

	/*
	 * Delete Post
	 */
	
	@GetMapping("/delete")
	@ForModerator
	public boolean delete(@RequestParam String title) {
		return postService.delete(title);
	}
	
}
