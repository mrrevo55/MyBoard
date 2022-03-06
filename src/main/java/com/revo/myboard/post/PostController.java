package com.revo.myboard.post;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.post.dto.CreateDTO;
import com.revo.myboard.post.dto.EditDTO;
import com.revo.myboard.post.dto.PostDTO;
import com.revo.myboard.post.dto.ShortPostDTO;
import com.revo.myboard.security.annotation.ForUser;

/* 
 *  Created By Revo
 */

@RestController
@RequestMapping("/post")
@Validated
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
	
	@GetMapping("/search/{content}")
	public List<ShortPostDTO> searchPostsByContent(@PathVariable String content){
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
	
	@PostMapping("/create")
	@ForUser
	@ResponseStatus(HttpStatus.CREATED)
	public long createPost(@RequestHeader("Authorization") String token, @RequestBody @Valid CreateDTO createDTO) {
		return postService.createPost(token, createDTO.getCategory(), createDTO.getTitle(), createDTO.getContent());
	}
	
	@DeleteMapping("/delete/{id}")
	@ForUser
	public void deletePostByTitleAsUser(@RequestHeader("Authorization") String token, @PathVariable long id) {
		postService.deletePostById(token, id);
	}
	
	@PatchMapping("/edit/{id}")
	@ForUser
	public void editPostTitleAsUser(@RequestHeader("Authorization") String token, @PathVariable long id, @RequestBody @Valid EditDTO editDTO) {
		postService.editPost(token, id, editDTO.getTitle(), editDTO.getContent());
	}
	
	@PostMapping("/like/{id}")
	@ForUser
	@ResponseStatus(HttpStatus.CREATED)
	public void giveLikeForPostById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		postService.giveLikeForPostById(token, id);
	}

	@DeleteMapping("/unlike/{id}")
	@ForUser
	public void removeLikeFromPostById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		postService.removeLikeFromPostById(token, id);
	}
	
}
