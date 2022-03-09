package com.revo.myboard.post;

import java.util.List;

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

import com.revo.myboard.post.dto.CreateDTO;
import com.revo.myboard.post.dto.EditDTO;
import com.revo.myboard.post.dto.PostDTO;
import com.revo.myboard.post.dto.ShortPostDTO;
import com.revo.myboard.security.annotation.ForUser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/* 
 *  Created By Revo
 */

@RestController
@RequestMapping("/post")
@Validated
@AllArgsConstructor
@Slf4j
public class PostController {
	
	private final PostService postService;
	
	/*
	 * PUBLIC
	 */

	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable long id, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" getting post with id: "+id);
		var post = PostDTO.mapFromPost(postService.getPostById(id));
		log.info("User with ip: "+request.getRemoteAddr()+" success got post with id: "+id);
		return ResponseEntity.ok(post);
	}
	
	@GetMapping("/search/{content}")
	public ResponseEntity<List<ShortPostDTO>> searchPostsByContent(@PathVariable String content, HttpServletRequest request){
		log.info("User with ip: "+request.getRemoteAddr()+" searching posts with content: "+content);
		var posts = postService.searchPostsByContent(content).stream().map(post -> ShortPostDTO.mapFromPost(post)).toList();
		log.info("User with ip: "+request.getRemoteAddr()+" success searched posts with content: "+content);
		return ResponseEntity.ok(posts);
	}
	
	@GetMapping("/lastActive")
	public ResponseEntity<List<ShortPostDTO>> getLastActivePotsts(HttpServletRequest request){
		log.info("User with ip: "+request.getRemoteAddr()+" getting last active posts");
		var posts = postService.getTenLastActivitedPosts().stream().map(post -> ShortPostDTO.mapFromPost(post)).toList();
		log.info("User with ip: "+request.getRemoteAddr()+" success got last active posts");
		return ResponseEntity.ok(posts);
	}
	
	@GetMapping("/mostLiked")
	public ResponseEntity<List<ShortPostDTO>> getMostLikedPosts(HttpServletRequest request){
		log.info("User with ip: "+request.getRemoteAddr()+" getting most liked posts");
		var posts = postService.getTenMostLikedPosts().stream().map(post -> ShortPostDTO.mapFromPost(post)).toList();
		log.info("User with ip: "+request.getRemoteAddr()+" success got most liked posts");
		return ResponseEntity.ok(posts);
	}
	
	/*
	 * USER
	 */
	
	@PostMapping("/create")
	@ForUser
	public ResponseEntity<Long> createPost(@RequestHeader("Authorization") String token, @RequestBody @Valid CreateDTO createDTO, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" creating post with details: "+ createDTO.toString());
		var id = postService.createPost(token, createDTO.getCategory(), createDTO.getTitle(), createDTO.getContent());
		log.info("User with ip: "+request.getRemoteAddr()+" success created post with details: "+ createDTO.toString());
		return ResponseEntity.status(HttpStatus.CREATED).body(id);
	}
	
	@DeleteMapping("/delete/{id}")
	@ForUser
	public void deletePostById(@RequestHeader("Authorization") String token, @PathVariable long id, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" deleting post with id: "+id);
		postService.deletePostById(token, id);
		log.info("User with ip: "+request.getRemoteAddr()+" success deleted post with id: "+id);
	}
	
	@PatchMapping("/edit/{id}")
	@ForUser
	public void editPostById(@RequestHeader("Authorization") String token, @PathVariable long id, @RequestBody @Valid EditDTO editDTO, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" editing post with id: "+id+" with details: "+editDTO.toString());
		postService.editPostById(token, id, editDTO.getTitle(), editDTO.getContent());
		log.info("User with ip: "+request.getRemoteAddr()+" success edited post with id: "+id+" with details: "+editDTO.toString());
	}
	
	@PostMapping("/like/{id}")
	@ForUser
	@ResponseStatus(HttpStatus.CREATED)
	public void giveLikeForPostById(@RequestHeader("Authorization") String token, @PathVariable long id, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" giving like for post with id: "+id);
		postService.giveLikeForPostById(token, id);
		log.info("User with ip: "+request.getRemoteAddr()+" success gived like for post with id: "+id);
	}

	@DeleteMapping("/unlike/{id}")
	@ForUser
	public void removeLikeFromPostById(@RequestHeader("Authorization") String token, @PathVariable long id, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" removing like for post with id: "+id);
		postService.removeLikeFromPostById(token, id);
		log.info("User with ip: "+request.getRemoteAddr()+" success removed like for post with id: "+id);
	}
	
}
