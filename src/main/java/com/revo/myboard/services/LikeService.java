package com.revo.myboard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.entities.Comment;
import com.revo.myboard.entities.Like;
import com.revo.myboard.entities.Post;
import com.revo.myboard.entities.User;
import com.revo.myboard.repositories.LikeRepository;

/*
 * User Service Class
 * 
 * Created By Revo
 */

@Service
public class LikeService {

	/*
	 * Data
	 */
	
	@Autowired
	private LikeRepository repo;
	@Autowired
	@Lazy
	private UserService userService;
	@Autowired
	@Lazy
	private PostService postService;
	@Autowired
	@Lazy
	private CommentService commentService;
	
	/*
	 * Get By Token
	 */
	
	public List<Like> getLikesByToken(String token){
		return userService.currentLogged(token).getMyLikes();
	}
	
	public List<Like> getLikedByToken(String token){
		return userService.currentLogged(token).getLiked();
	}
	
	public List<Like> getLikesByTitle(String title){
		return postService.get(title).getMyLikes();
	}
	
	public List<Like> getLikesById(long id){
		return commentService.get(id).getMyLikes();
	}
	
	/*
	 * Get By Login
	 */
	
	public List<Like> getLikesByLogin(String token){
		return userService.currentLogged(token).getMyLikes();
	}
	
	public List<Like> getLikedByLogin(String login){
		return userService.get(login).getLiked();
	}
	
	/*
	 * Give Like
	 */
	
	@Transactional
	public boolean giveForUser(String token, String login) {
		User u = userService.currentLogged(token);
		if(u == null) return false;
		User t = userService.get(login);
		if(t == null) return false;
		for(Like l : t.getMyLikes())
			if(l.getWho().equals(u)) return false;
		Like l = new Like();
		l.setWho(u);
		l.setUser(t);
		repo.save(l);
		return true;
	}
	
	@Transactional
	public boolean giveForPost(String token, String title) {
		User u = userService.currentLogged(token);
		if(u == null) return false;
		Post t = postService.get(title);
		if(t == null) return false;
		for(Like l : t.getMyLikes())
			if(l.getWho().equals(u)) return false;
		Like l = new Like();
		l.setWho(u);
		l.setPost(t);
		repo.save(l);
		return true;
	}
	
	@Transactional
	public boolean giveForComment(String token, long id) {
		User u = userService.currentLogged(token);
		if(u == null) return false;
		Comment t = commentService.get(id);
		if(t == null) return false;
		for(Like l : t.getMyLikes())
			if(l.getWho().equals(u)) return false;
		Like l = new Like();
		l.setWho(u);
		l.setComment(t);
		repo.save(l);
		return true;
	}
	
	/*
	 * Remove Like
	 */
	
	@Transactional
	public boolean removeForUser(String token, String login) {
		User u = userService.currentLogged(token);
		if(u != null) {
			User t = userService.get(login);
				if(t != null) 
					for(Like l : t.getMyLikes())
						if(l.getWho().equals(u)) {
							repo.delete(l);
							return true;
						}
		}
		return false;
	}
	
	@Transactional
	public boolean removeForPost(String token, String title) {
		User u = userService.currentLogged(token);
		if(u != null) {
			Post t = postService.get(title);
				if(t != null) 
					for(Like l : t.getMyLikes())
						if(l.getWho().equals(u)) {
							repo.delete(l);
							return true;
						}
		}
		return false;
	}
	
	@Transactional
	public boolean removeForComment(String token, long id) {
		User u = userService.currentLogged(token);
		if(u != null) {
			Comment t = commentService.get(id);
				if(t != null) 
					for(Like l : t.getMyLikes())
						if(l.getWho().equals(u)) {
							repo.delete(l);
							return true;
						}
		}
		return false;
	}
	
}
