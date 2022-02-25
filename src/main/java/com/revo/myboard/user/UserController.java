package com.revo.myboard.user;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	/*
	 * PUBLIC
	 */
	
	@GetMapping("/{login}")
	public UserDTO getUserByLogin(@PathVariable String login) {
		return new UserDTO().mapFromUser(userService.getUserByLogin(login));
	}
	
	@GetMapping("/search")
	public List<SearchDTO> getUsersByContent(@RequestParam String content){
		return userService.searchUsersByContent(content).stream().map(user -> new SearchDTO().mapFromUser(user)).toList();
	}
	
	@GetMapping("/translate")
	public CredentialsDTO translateTokenOnLogin(@RequestParam String token) {
		return new CredentialsDTO().mapFromUser(this.userService.currentLoggedUser(token));	
	}
	
	/*
	 *  USER
	 */

	@PutMapping("/change/password")
	@ForUser
	public void changeUserPassword(@RequestHeader("Authorization") String token,
			@RequestParam String new_password) {
		userService.changeUserPassword(token, new_password);
	}

	@PutMapping("/change/email")
	@ForUser
	public void changeUserEmail(@RequestHeader("Authorization") String token, @RequestParam String new_email) {
		userService.changeUserEmail(token, new_email);
	}

	@PutMapping("/change/data")
	@ForUser
	public void changeUserData(@RequestHeader("Authorization") String token, @RequestBody DataDTO dataDTO) {
		userService.changeUserDescription(token, dataDTO.getDescription()); 
		userService.changeUserAge(token, dataDTO.getAge());
		userService.changeUserCity(token, dataDTO.getCity());
		userService.changeUserPage(token, dataDTO.getPage());
		userService.changeUserSex(token, dataDTO.getSex());
	}
	
	@PostMapping("/like/{login}")
	@ForUser
	public void giveLikeForUserByLogin(@RequestHeader("Authorization") String token, @PathVariable String login) {
		userService.giveLikeForUserByLogin(token, login);
	}
	
	@DeleteMapping("/unlike/{login}")
	@ForUser
	public void removeLikeFromUserByLogin(@RequestHeader("Authorization") String token, @PathVariable String login) {
		userService.removeLikeFromUserByLogin(token, login);
	}

	@GetMapping("/profile")
	@ForUser
	public ProfileDTO currentLoggedUserProfile(@RequestHeader("Authorization") String token) {
		return new ProfileDTO().mapFromUser(userService.currentLoggedUser(token));
	}
	
	@DeleteMapping("/delete/logged")
	@ForUser
	public void deleteUserByLoggedUser(@RequestHeader("Authorization") String token) {
		userService.deleteUserByLoggedUser(token);
	}
	
	@GetMapping("/sexes")
	@ForUser
	public List<String> getSexList(){
		return userService.getSexList();
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
	public void unbanUserByLogin(@PathVariable String login) {
		userService.unbanUserByLogin(login);
	}

	/*
	 *  ADMIN
	 */
	
	@PutMapping("/group/{login}")
	@ForAdmin
	public void setGroupByLogin(@PathVariable String login, @RequestParam long group_id) {
		userService.setGroupByLogin(login, group_id);
	}
	
	@DeleteMapping("/delete/{login}")
	@ForAdmin
	public void deleteUserAsAdmin(@PathVariable String login) {
		userService.deleteUserAsAdmin(login);
	}

}
