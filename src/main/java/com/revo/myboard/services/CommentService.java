package com.revo.myboard.services;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.entities.Comment;
import com.revo.myboard.entities.Post;
import com.revo.myboard.entities.User;
import com.revo.myboard.repositories.CommentRepository;

/*
 * Post Service Class
 * 
 * Created By Revo
 */

@Service
public class CommentService {

	/*
	 * 	Data
	 */
	
	@Autowired
	private CommentRepository repo;
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	@Autowired
	private LikeService likeService;
	
	/*
	 * Create New Comment
	 */
	
	public boolean create(String token, String post, String content) {
		User u = userService.currentLogged(token);
		if(u == null) return false;
		Post p = postService.get(post);
		if(p == null) return false;
		Comment c = new Comment();
		c.setAuthor(u);
		c.setContent(content);
		c.setDate(new Date(System.currentTimeMillis()));
		repo.save(c);
		return true;
	}
	
	/*
	 * Get Comment By Id
	 */
	
	public Comment get(long id) {
		return repo.getById(id);
	}
	
	/*
	 * Edit Content
	 */
	
	@Transactional
	public boolean edit(long id, String content) {
		Comment c = get(id);
		if(c == null) return false;
		c.setContent(content);
		c.setLastEditDate(new Date(System.currentTimeMillis()));
		return true;
	}
	
	@Transactional
	public boolean edit(String token, long id, String content) {
		if(userService.currentLogged(token) == null) return false;
		return edit(id, content);
	}
	
	/*
	 * Delete 
	 */
	
	public boolean delete(long id) {
		Comment c = get(id);
		if(c == null) return false;
		repo.delete(c);
		return true;
	}
	
	public boolean delete(String token, long id) {
		Comment c = get(id);
		if(c != null) 
			for(Comment t : userService.currentLogged(token).getComments())
				if(t.getId() == id) {
					repo.delete(c);
					return true;
				}
		return false;
	}
	
	/*
	 * Give Like
	 */
	
	@Transactional
	public boolean giveLike(String token, long id) {
		return likeService.giveForComment(token, id);
	}
	
	/*
	 * Remove Like
	 */
	
	@Transactional
	public boolean removeLike(String token, long id) {
		return likeService.removeForComment(token, id);
	}
	
}
