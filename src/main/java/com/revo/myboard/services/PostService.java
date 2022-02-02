package com.revo.myboard.services;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.entities.Category;
import com.revo.myboard.entities.Post;
import com.revo.myboard.entities.User;
import com.revo.myboard.repositories.PostRepository;

/*
 * Post Service Class
 * 
 * Created By Revo
 */

@Service
public class PostService {

	/*
	 * Data
	 */

	@Autowired
	private PostRepository repo;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private LikeService likeService;

	/*
	 * Create New Post
	 */

	public boolean create(String token, String category, String title, String content) {
		User u = userService.currentLogged(token);
		if (u == null)
			return false;
		Category c = categoryService.get(category);
		if (c == null)
			return false;
		Post p = new Post();
		p.setAuthor(u);
		p.setCategory(c);
		p.setContent(content);
		p.setDate(new Date(System.currentTimeMillis()));
		p.setTitle(title);
		repo.save(p);
		return true;
	}

	/*
	 * Get By Title
	 */

	public Post get(String title) {
		try {
			return repo.findByTitle(title).get();
		} catch (NoSuchElementException e) { return null; }
	}

	/*
	 * Get By Token
	 */
	
	public List<Post> getByLogged(String token){
		return userService.currentLogged(token).getPosts();
	}
	
	/*
	 * Get By Login
	 */
	
	public List<Post> getByLogin(String login){
		return userService.get(login).getPosts();
	}
	
	/*
	 * Edit Content
	 */

	@Transactional
	public boolean editContent(String title, String content) {
		Post p = get(title);
		if (p == null)
			return false;
		p.setContent(content);
		p.setLastEditDate(new Date(System.currentTimeMillis()));
		return true;
	}
	
	@Transactional
	public boolean editContent(String token, String title, String content) {
		if(userService.currentLogged(token) == null) return false;
		return editContent(title, content);
	}

	/*
	 * Edit Title
	 */

	@Transactional
	public boolean editTitle(String title, String new_title) {
		Post p = get(title);
		if (p == null)
			return false;
		if (get(new_title) != null)
			return false;
		p.setTitle(new_title);
		p.setLastEditDate(new Date(System.currentTimeMillis()));
		return true;
	}
	
	@Transactional
	public boolean editTitle(String token, String title, String new_title) {
		if(userService.currentLogged(token) == null) return false;
		return editTitle(title, new_title);
	}

	/*
	 * Give Like
	 */

	@Transactional
	public boolean giveLike(String token, String title) {
		return likeService.giveForPost(token, title);
	}

	/*
	 * Remove Like
	 */

	@Transactional
	public boolean removeLike(String token, String title) {
		return likeService.removeForPost(token, title);
	}

	/*
	 * Delete
	 */

	public boolean delete(String title) {
		Post p = get(title);
		if (p == null)
			return false;
		repo.delete(p);
		return true;
	}

	public boolean delete(String token, String title) {
		Post p = get(title);
		if (p != null)
			for (Post t : userService.currentLogged(token).getPosts())
				if (t.getTitle().equals(title)) {
					repo.delete(p);
					return true;
				}
		return false;
	}
	
}
