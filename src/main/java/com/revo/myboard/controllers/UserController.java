package com.revo.myboard.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.revo.myboard.annotations.ForAdmin;
import com.revo.myboard.annotations.ForModerator;
import com.revo.myboard.annotations.ForUser;
import com.revo.myboard.entities.Like;
import com.revo.myboard.entities.User;
import com.revo.myboard.services.LikeService;
import com.revo.myboard.services.UserService;
import com.revo.myboard.views.UserProfileView;

/*
 * User Controller Class
 * 
 * Created By Revo
 * 
 */

@RestController
@RequestMapping("/user")
public class UserController {

	/*
	 * Data
	 */

	@Autowired
	private UserService userService;
	@Autowired
	private LikeService likeService;
	
	// USER

	/*
	 * Change User Password
	 */

	@GetMapping("/change/password")
	@ForUser
	public boolean changePassword(@RequestHeader("Authorization") String token,
			@RequestParam String new_password) {
		return userService.changePassword(token, new_password);
	}

	/*
	 * Change User Email
	 */

	@GetMapping("/change/email")
	@ForUser
	public boolean changeEmail(@RequestHeader("Authorization") String token, @RequestParam String new_email) {
		return userService.changeEmail(token, new_email);
	}

	/*
	 * Change User Data
	 */

	@GetMapping("/change/data")
	@ForUser
	public boolean changeData(@RequestHeader("Authorization") String token, @RequestParam String description,
			@RequestParam int age, @RequestParam String country, @RequestParam String page, @RequestParam String sex) {
		if (userService.changeDescription(token, description) && userService.changeAge(token, age)
				&& userService.changeCountry(token, country.toUpperCase()) && userService.changePage(token, page)
				&& userService.changeSex(token, sex.toUpperCase()))
			return true;
		return false;
	}
	
	/*
	 * Get Current Logged Likes
	 */
	
	@GetMapping("/likes/logged")
	@ForUser
	public List<Like> likesLogged(@RequestHeader("Authorization") String token){
		return likeService.getLikesByToken(token);
	}
	
	/*
	 * Get Likes By Login
	 */
	
	@GetMapping("/likes")
	@ForUser
	public List<Like> likes(@RequestParam String login){
		return likeService.getLikesByLogin(login);
	}
	
	/*
	 * Get Current Logged Liked
	 */
	
	@GetMapping("/liked/logged")
	@ForUser
	public List<Like> likedLogged(@RequestHeader("Authorization") String token){
		return likeService.getLikedByToken(token);
	}
	
	/*
	 * Get Liked By Login
	 */
	
	@GetMapping("/liked")
	@ForUser
	public List<Like> liked(@RequestParam String login){
		return likeService.getLikedByLogin(login);
	}
	
	/*
	 * Give Like For User
	 */
	
	@GetMapping("/like")
	@ForUser
	public boolean giveLike(@RequestHeader("Authorization") String token, @RequestParam String login) {
		return userService.giveLike(token, login);
	}
	
	/*
	 * Remove Like From User
	 */
	
	@GetMapping("/unlike")
	@ForUser
	public boolean removeLike(@RequestHeader("Authorization") String token, @RequestParam String login) {
		return userService.removeLike(token, login);
	}

	/*
	 * Get User Profile Data
	 */

	@GetMapping("/profile")
	@ForUser
	@JsonView(UserProfileView.class)
	public User profile(@RequestHeader("Authorization") String token) {
		return userService.currentLogged(token);
	}
	
	/*
	 * Delete
	 */
	
	@GetMapping("/delete/logged")
	@ForUser
	public boolean deleteByUser(@RequestHeader("Authorization") String token) {
		return userService.deleteByUser(token);
	}
	
	// MODERATOR

	/*
	 * Ban User
	 */

	@GetMapping("/ban")
	@ForModerator
	public boolean ban(@RequestParam String login) {
		return userService.banUser(login);
	}

	/*
	 * Unban User
	 */

	@GetMapping("/unban")
	@ForModerator
	public boolean unban(String login) {
		return userService.unbanUser(login);
	}

	// ADMIN
	
	/*
	 * Set Users Group
	 */
	
	@GetMapping("/group")
	@ForAdmin
	public boolean group(String login, String group) {
		return userService.setGroup(login, group);
	}
	
	@GetMapping("/delete")
	@ForAdmin
	public boolean delete(@RequestParam String login) {
		return userService.delete(login);
	}

}
