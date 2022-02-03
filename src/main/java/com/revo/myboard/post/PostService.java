package com.revo.myboard.post;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.category.CategoryService;
import com.revo.myboard.like.LikeService;
import com.revo.myboard.user.UserService;
import com.revo.myboard.util.ObjectNotExistsException;

/*
 * Created By Revo
 */

@Service
@Transactional
public class PostService {

	private PostRepository repo;
	private UserService userService;
	private CategoryService categoryService;
	private LikeService likeService;

	public PostService(PostRepository repo, UserService userService, CategoryService categoryService,
			LikeService likeService) {
		this.repo = repo;
		this.userService = userService;
		this.categoryService = categoryService;
		this.likeService = likeService;
	}

	public boolean createPost(String token, String category_name, String title, String content) {
		var user = userService.currentLoggedUser(token);
		if (user == null) {
			return false;
		}
		var category = categoryService.getCategoryByName(category_name);
		if (category == null) {
			return false;
		}
		var post = new Post();
		post.setAuthor(user);
		post.setCategory(category);
		post.setContent(content);
		post.setDate(new Date(System.currentTimeMillis()));
		post.setTitle(title);
		repo.save(post);
		return true;
	}

	public Post getPostByTitle(String title) {
		return repo.findByTitle(title).orElseThrow(() -> new ObjectNotExistsException(title));
	}

	public List<Post> getPostByLogged(String token) {
		return userService.currentLoggedUser(token).getPosts();
	}

	public List<Post> getPostByLogin(String login) {
		return userService.getUserByLogin(login).getPosts();
	}

	public boolean editPostContent(String title, String content) {
		var post = getPostByTitle(title);
		if (post == null) {
			return false;
		}
		post.setContent(content);
		post.setLastEditDate(new Date(System.currentTimeMillis()));
		return true;
	}

	public boolean editPostContentAsUser(String token, String title, String content) {
		var user = userService.currentLoggedUser(token);
		if (user == null || !user.getPosts().contains(getPostByTitle(title))) {
			return false;
		}
		return editPostContent(title, content);
	}

	public boolean editPostTitle(String title, String new_title) {
		var post = getPostByTitle(title);
		if (post == null) {
			return false;
		}
		if (getPostByTitle(new_title) != null) {
			return false;
		}
		post.setTitle(new_title);
		post.setLastEditDate(new Date(System.currentTimeMillis()));
		return true;
	}

	public boolean editPostTitleAsUser(String token, String title, String new_title) {
		var user = userService.currentLoggedUser(token);
		if (user == null || !user.getPosts().contains(getPostByTitle(title))) {
			return false;
		}
		return editPostTitle(title, new_title);
	}

	public boolean giveLikeForPostByTitle(String token, String title) {
		return likeService.giveForPostByTitle(token, title);
	}

	public boolean removeLikeFromPostByTitle(String token, String title) {
		return likeService.removeFromPostByTitle(token, title);
	}

	public boolean deletePostByTitle(String title) {
		var post = getPostByTitle(title);
		if (post == null) {
			return false;
		}
		repo.delete(post);
		return true;
	}

	public boolean deletePostByTitleAsUser(String token, String title) {
		for (var target : userService.currentLoggedUser(token).getPosts()) {
			if (target.getTitle().equals(title)) {
				repo.delete(target);
				return true;
			}
		}
		return false;
	}

}
