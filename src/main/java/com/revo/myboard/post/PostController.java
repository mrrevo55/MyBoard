package com.revo.myboard.post;

import java.util.List;

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
@RequestMapping("/post")
public class PostController {
	
	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	/*
	 * PUBLIC
	 */

	@GetMapping("/{id}")
	public PostDTO getPostById(@PathVariable long id) {
		return new PostDTO().mapFromPost(postService.getPostById(id));
	}
	
	@GetMapping("/search")
	public List<ShortPostDTO> searchPostsByContent(@RequestParam String content){
		return postService.searchPostsByContent(content).stream().map(post -> new ShortPostDTO().mapFromPost(post)).toList();
	}
	
	@GetMapping("/lastActive")
	public List<ShortPostDTO> getLastActivePotsts(){
		return postService.getTenLastActivitedPosts().stream().map(post -> new ShortPostDTO().mapFromPost(post)).toList();
	}
	
	@GetMapping("/mostLiked")
	public List<ShortPostDTO> getMostLikedPosts(){
		return postService.getTenMostLikedPosts().stream().map(post -> new ShortPostDTO().mapFromPost(post)).toList();
	}
	
	/*
	 * USER
	 */
	
	@GetMapping("/logged")
	@ForUser
	public List<PostDTO> getPostsByLogged(@RequestHeader("Authorization") String token){
		return postService.getPostsByLogged(token).stream().map(post -> new PostDTO().mapFromPost(post)).toList();
	}
	
	@GetMapping("/user/{login}")
	@ForUser
	public List<PostDTO> getPostByLogin(@PathVariable String login){
		return postService.getPostsByLogin(login).stream().map(post -> new PostDTO().mapFromPost(post)).toList();
	}
	
	@PostMapping("/create")
	@ForUser
	@ResponseStatus(HttpStatus.CREATED)
	public long createPost(@RequestHeader("Authorization") String token, @RequestBody CreateDTO createDTO) {
		return postService.createPost(token, createDTO.getCategory(), createDTO.getTitle(), createDTO.getContent());
	}
	
	@DeleteMapping("/delete/{id}/logged")
	@ForUser
	public void deletePostByTitleAsUser(@RequestHeader("Authorization") String token, @PathVariable long id) {
		postService.deletePostByIdAsUser(token, id);
	}
	
	@PutMapping("/edit/title/{id}/logged")
	@ForUser
	public String editPostTitleAsUser(@RequestHeader("Authorization") String token, @PathVariable long id, @RequestParam String new_title) {
		postService.editPostTitleAsUser(token, id, new_title);
		return new_title;
	}
	
	@PutMapping("/edit/content/{id}/logged")
	@ForUser
	public String editPostContentAsUser(@RequestHeader("Authorization") String token, @PathVariable long id, @RequestParam String new_content) {
		postService.editPostContentAsUser(token, id, new_content);
		return new_content;
	}
	
	@PostMapping("/like/{id}")
	@ForUser
	public void giveLikeForPostById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		postService.giveLikeForPostById(token, id);
	}

	@DeleteMapping("/unlike/{id}")
	@ForUser
	public void removeLikeFromPostById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		postService.removeLikeFromPostById(token, id);
	}
	
	/*
	 * MODERATOR
	 */
	
	@PutMapping("/edit/title/{id}")
	@ForModerator
	public void editPostTitle(@PathVariable long id, @RequestParam String new_title) {
		postService.editPostTitle(id, new_title);
	}

	@PutMapping("/edit/content/{id}")
	@ForModerator
	public void editPostContent(@PathVariable long id, @RequestParam String new_content) {
		postService.editPostContent(id, new_content);
	}
	
	@DeleteMapping("/delete/{id}")
	@ForModerator
	public void deletePostById(@PathVariable long id) {
		postService.deletePostById(id);
	}
	
}
