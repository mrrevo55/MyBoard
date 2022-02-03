package com.revo.myboard.user;

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
import com.revo.myboard.like.Like;
import com.revo.myboard.like.LikeService;
import com.revo.myboard.security.annotation.ForAdmin;
import com.revo.myboard.security.annotation.ForModerator;
import com.revo.myboard.security.annotation.ForUser;

/*
 * Created By Revo
 */

@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;
	private LikeService likeService;
	
	public UserController(UserService userService, LikeService likeService) {
		super();
		this.userService = userService;
		this.likeService = likeService;
	}

	/*
	 *  USER
	 */

	@PutMapping("/change/password")
	@ForUser
	public boolean changeUserPassword(@RequestHeader("Authorization") String token,
			@RequestParam String new_password) {
		return userService.changeUserPassword(token, new_password);
	}

	@PutMapping("/change/email")
	@ForUser
	public boolean changeUserEmail(@RequestHeader("Authorization") String token, @RequestParam String new_email) {
		return userService.changeUserEmail(token, new_email);
	}

	@PutMapping("/change/data")
	@ForUser
	public boolean changeUserData(@RequestHeader("Authorization") String token, @RequestParam String description,
			@RequestParam int age, @RequestParam String country, @RequestParam String page, @RequestParam String sex) {
		if (userService.changeUserDescription(token, description) && userService.changeUserAge(token, age)
				&& userService.changeUserCountry(token, country.toUpperCase()) && userService.changeUserPage(token, page)
				&& userService.changeUserSex(token, sex.toUpperCase())) {
			return true;
		}
		return false;
	}
	
	@GetMapping("/likes/logged")
	@ForUser
	public List<Like> getLikesByToken(@RequestHeader("Authorization") String token){
		return likeService.getLikesByToken(token);
	}
	
	@GetMapping("/likes")
	@ForUser
	public List<Like> getLikesByLogin(@RequestParam String login){
		return likeService.getLikesByLogin(login);
	}
	
	@GetMapping("/liked/logged")
	@ForUser
	public List<Like> getLikedByToken(@RequestHeader("Authorization") String token){
		return likeService.getLikedByToken(token);
	}
	
	@GetMapping("/liked")
	@ForUser
	public List<Like> getLikedByLogin(@RequestParam String login){
		return likeService.getLikedByLogin(login);
	}
	
	@PostMapping("/like/{login}")
	@ForUser
	public boolean giveLikeForUserByLogin(@RequestHeader("Authorization") String token, @PathVariable String login) {
		return userService.giveLikeForUserByLogin(token, login);
	}
	
	@GetMapping("/unlike/{login}")
	@ForUser
	public boolean removeLikeFromUserByLogin(@RequestHeader("Authorization") String token, @PathVariable String login) {
		return userService.removeLikeFromUserByLogin(token, login);
	}

	@GetMapping("/profile")
	@ForUser
	@JsonView(UserProfileView.class)
	public User currentLoggedUserProfile(@RequestHeader("Authorization") String token) {
		return userService.currentLoggedUser(token);
	}
	
	@DeleteMapping("/delete/logged")
	@ForUser
	public void deleteUserByLoggedUser(@RequestHeader("Authorization") String token) {
		userService.deleteUserByLoggedUser(token);
	}
	
	@GetMapping("/groups")
	@ForUser
	public List<String> getGroupList(){
		return userService.getGroupList();
	}
	
	@GetMapping("/sexes")
	@ForUser
	public List<String> getSexList(){
		return userService.getSexList();
	}
	
	@GetMapping("/countries")
	@ForUser
	public List<String> getCountryList(){
		return userService.getCountryList();
	}
	
	/*
	 *  MODERATOR
	 */

	@PutMapping("/ban/{login}")
	@ForModerator
	public void banUserByLogin(@PathVariable String login) {
		userService.banUserByLogin(login);
	}

	@PutMapping("/unban/{login}")
	@ForModerator
	public boolean unbanUserByLogin(@PathVariable String login) {
		return userService.unbanUserByLogin(login);
	}

	/*
	 *  ADMIN
	 */
	
	@PutMapping("/group/{login}")
	@ForAdmin
	public boolean setGroupByLogin(@PathVariable String login, @RequestParam String group) {
		return userService.setGroupByLogin(login, group);
	}
	
	@DeleteMapping("/delete/{login}")
	@ForAdmin
	public void deleteUserAsAdmin(@PathVariable String login) {
		userService.deleteUserAsAdmin(login);
	}

}
