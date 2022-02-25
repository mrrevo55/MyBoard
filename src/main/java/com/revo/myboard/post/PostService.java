package com.revo.myboard.post;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.category.CategoryService;
import com.revo.myboard.like.LikeService;
import com.revo.myboard.user.UserService;

/*
 * Created By Revo
 */

@Service
@Transactional
public class PostService {

	private PostRepository repository;
	private UserService userService;
	private CategoryService categoryService;
	private LikeService likeService;

	public PostService(PostRepository repository, UserService userService, CategoryService categoryService,
			LikeService likeService) {
		this.repository = repository;
		this.userService = userService;
		this.categoryService = categoryService;
		this.likeService = likeService;
	}

	public long createPost(String token, long category_id, String title, String content) {
		if(repository.existsByTitle(title)) {
			throw new IllegalArgumentException(title);
		}
		var post = new Post();
		post.setAuthor(userService.currentLoggedUser(token));
		post.setCategory(categoryService.getCategoryById(category_id));
		post.setContent(content);
		post.setDate(new Date(System.currentTimeMillis()));
		post.setLastActiveDate(new Date(System.currentTimeMillis()));
		post.setLastEditDate(new Date(System.currentTimeMillis()));
		post.setTitle(title);
		return repository.save(post).getId();
	}
	
	public List<Post> searchPostsByContent(String content){
		return repository.findAll().stream().filter(post -> post.getTitle().toLowerCase().contains(content.toLowerCase())).filter(post -> post.getComments().stream().filter(comment -> comment.getContent().toLowerCase().contains(content.toLowerCase())).toList() != null).toList();
	}
	
	public Post getPostById(long id) {
		return repository.getById(id);
	}

	public List<Post> getPostsByLogged(String token) {
		return userService.currentLoggedUser(token).getPosts();
	}

	public List<Post> getPostsByLogin(String login) {
		return userService.getUserByLogin(login).getPosts();
	}

	public void editPostContent(long id, String content) {
		var post = getPostById(id);
		post.setContent(content);
		post.setLastEditDate(new Date(System.currentTimeMillis()));
	}

	public void editPostContentAsUser(String token, long id, String content) {
		if (!userService.currentLoggedUser(token).getPosts().contains(getPostById(id))) {
			throw new NullPointerException(String.valueOf(id));
		}
		editPostContent(id, content);
	}

	public void editPostTitle(long id, String new_title) {
		var post = getPostById(id);
		if (repository.existsByTitle(new_title)) {
			throw new IllegalArgumentException(new_title);
		}
		post.setTitle(new_title);
		post.setLastEditDate(new Date(System.currentTimeMillis()));
	}

	public void editPostTitleAsUser(String token, long id, String new_title) {
		if (!userService.currentLoggedUser(token).getPosts().contains(getPostById(id))) {
			throw new NullPointerException(String.valueOf(id));
		}
		editPostTitle(id, new_title);
	}

	public void giveLikeForPostById(String token, long id) {
		likeService.giveForPostById(token, id);
	}

	public void removeLikeFromPostById(String token, long id) {
		likeService.removeFromPostById(token, id);
	}

	public void deletePostById(long id) {
		repository.delete(getPostById(id));
	}

	public void deletePostByIdAsUser(String token, long id) {
		var user = userService.currentLoggedUser(token);
		for (var target : user.getPosts()) {
			if (target.getId() == id) {
				repository.deleteById(id);
				return;
			}
		}
		throw new NullPointerException(String.valueOf(id));
	}

	public List<Post> getTenLastActivitedPosts(){
		List<Post> tenLastActivited = new ArrayList<Post>();
		List<Post> postsToCheck = new ArrayList<Post>(repository.findAll());
		for(int i = 0; i < 10; i++) {
			if(postsToCheck.size() == 0) {
				break;
			}
			var post = this.getNewActivity(postsToCheck);
			postsToCheck.remove(post);
			tenLastActivited.add(post);
		}
		return tenLastActivited;
	}
	
	private Post getNewActivity(List<Post> posts) {
		Post lastPost = posts.get(0);
		for(Post post : posts) {
			if(lastPost.getLastActiveDate().before(post.getLastEditDate())) {
				lastPost = post;
			}
		}
		return lastPost;
	}
	
	public List<Post> getTenMostLikedPosts(){
		List<Post> mostLikedPosts = new ArrayList<Post>();
		List<Post> postsToCheck = new ArrayList<Post>(repository.findAll());
		for(int i = 0; i < 10; i++) {
			if(postsToCheck.size() == 0) {
				break;
			}
			var post = this.getMostLikedPostFromList(postsToCheck);
			mostLikedPosts.add(post);
			postsToCheck.remove(post);
		}
		return mostLikedPosts;
	}
	
	private Post getMostLikedPostFromList(List<Post> posts) {
		Post result = posts.get(0);
		for(Post post : posts) {
			if(post.getMyLikes().size() > result.getMyLikes().size()) {
				result = post;
			}
		}
		return result;
	}
}
