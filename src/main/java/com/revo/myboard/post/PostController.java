package com.revo.myboard.post;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.revo.myboard.security.annotation.ForModerator;
import com.revo.myboard.security.annotation.ForUser;

/* 
 *  Created By Revo
 */

@RestController
@RequestMapping("/post")
public class PostController {
	
	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	/*
	 * USER
	 */

	@GetMapping("/logged")
	@ForUser
	@JsonView(PostActivityView.class)
	public List<Post> getPostByLogged(@RequestHeader("Authorization") String token){
		return postService.getPostByLogged(token);
	}
	
	@GetMapping("/user/{login}")
	@ForUser
	@JsonView(PostActivityView.class)
	public List<Post> getPostByLogin(@PathVariable String login){
		return postService.getPostByLogin(login);
	}
	
	@GetMapping("/{title}")
	@ForUser
	@JsonView(PostGetView.class)
	public Post getPostByTitle(@PathVariable String title) {
		return postService.getPostByTitle(title);
	}
	
	@PostMapping("/create")
	@ForUser
	public boolean createPost(@RequestHeader("Authorization") String token, @RequestParam String category, @RequestParam String title, @RequestParam String content) {
		return postService.createPost(token, category, title, content);
	}
	
	@DeleteMapping("/delete/{title}/logged")
	@ForUser
	public boolean deletePostByTitleAsUser(@RequestHeader("Authorization") String token, @PathVariable String title) {
		return postService.deletePostByTitleAsUser(token, title);
	}
	
	@PutMapping("/edit/title/{title}/logged")
	@ForUser
	public boolean editPostTitleAsUser(@RequestHeader("Authorization") String token, @PathVariable String title, @RequestParam String new_title) {
		return postService.editPostTitleAsUser(token, title, new_title);
	}
	
	@PutMapping("/edit/content/{title}/logged")
	@ForUser
	public boolean editPostContentByUser(@RequestHeader("Authorization") String token, @PathVariable String title, @RequestParam String new_content) {
		return postService.editPostContentAsUser(token, title, new_content);
	}
	
	@PostMapping("/like/{title}")
	@ForUser
	public boolean giveLikeForPostByTitle(@RequestHeader("Authorization") String token, @PathVariable String title) {
		return postService.giveLikeForPostByTitle(token, title);
	}

	@PostMapping("/unlike/{title}")
	@ForUser
	public boolean removeLikeFromPostByTitle(@RequestHeader("Authorization") String token, @PathVariable String title) {
		return postService.removeLikeFromPostByTitle(token, title);
	}
	
	/*
	 * MODERATOR
	 */
	
	@PutMapping("/edit/title/{title}")
	@ForModerator
	public boolean editPostTitle(@RequestHeader("Authorization") String token, @PathVariable String title, @RequestParam String new_title) {
		return postService.editPostTitle(title, new_title);
	}

	@PutMapping("/edit/content/{title}")
	@ForModerator
	public boolean editPostContent(@RequestHeader("Authorization") String token, @PathVariable String title, @RequestParam String new_content) {
		return postService.editPostContent(title, new_content);
	}
	
	@DeleteMapping("/delete/{title}")
	@ForModerator
	public boolean deletePostByTitle(@PathVariable String title) {
		return postService.deletePostByTitle(title);
	}
	
}
