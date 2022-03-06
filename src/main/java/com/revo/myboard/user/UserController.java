package com.revo.myboard.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.security.annotation.ForAdmin;
import com.revo.myboard.security.annotation.ForModerator;
import com.revo.myboard.security.annotation.ForUser;
import com.revo.myboard.user.dto.CredentialsDTO;
import com.revo.myboard.user.dto.DataDTO;
import com.revo.myboard.user.dto.EmailDTO;
import com.revo.myboard.user.dto.PasswordDTO;
import com.revo.myboard.user.dto.ProfileDTO;
import com.revo.myboard.user.dto.SearchDTO;
import com.revo.myboard.user.dto.UserDTO;

/*
 * Created By Revo
 */

@RestController
@RequestMapping("/user")
@Validated
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
	
	@GetMapping("/search/{content}")
	public List<SearchDTO> getUsersByContent(@PathVariable String content){
		return userService.searchUsersByContent(content).stream().map(user -> new SearchDTO().mapFromUser(user)).toList();
	}
	
	@GetMapping("/credentials")
	public CredentialsDTO translateTokenOnCredentials(@RequestHeader("Authorization") String token) {
		return new CredentialsDTO().mapFromUser(this.userService.currentLoggedUser(token));	
	}
	
	/*
	 *  USER
	 */

	@PatchMapping("/change/password")
	@ForUser
	public void changeUserPassword(@RequestHeader("Authorization") String token,
			@RequestBody @Valid PasswordDTO passwordDTO) {
		userService.changeUserPassword(token, passwordDTO.getPassword());
	}

	@PatchMapping("/change/email")
	@ForUser
	public void changeUserEmail(@RequestHeader("Authorization") String token, @RequestBody @Valid EmailDTO emailDTO) {
		userService.changeUserEmail(token, emailDTO.getEmail());
	}

	@PutMapping("/change/data")
	@ForUser
	public void changeUserData(@RequestHeader("Authorization") String token, @RequestBody @Valid DataDTO dataDTO) {
		userService.changeUserData(token, dataDTO.getDescription(), dataDTO.getAge(), dataDTO.getCity(), dataDTO.getPage(), dataDTO.getSex());
	}

	@GetMapping("/profile")
	@ForUser
	public ProfileDTO currentLoggedUserProfile(@RequestHeader("Authorization") String token) {
		return new ProfileDTO().mapFromUser(userService.currentLoggedUser(token));
	}
	
	@DeleteMapping("/delete/{login}")
	@ForUser
	public void deleteUserByLoggedUser(@RequestHeader("Authorization") String token, @PathVariable String login) {
		userService.deleteUserByLogin(token, login);
	}
	
	@GetMapping("/sexes")
	@ForUser
	public List<String> getSexList(){
		return userService.getSexList();
	}
	
	/*
	 *  MODERATOR
	 */

	@PatchMapping("/ban/{login}")
	@ForModerator
	public void banUserByLogin(@PathVariable String login) {
		userService.banUserByLogin(login);
	}

	@PatchMapping("/unban/{login}")
	@ForModerator
	public void unbanUserByLogin(@PathVariable String login) {
		userService.unbanUserByLogin(login);
	}

	/*
	 *  ADMIN
	 */
	
	@PatchMapping("/group/{login}/{group_id}")
	@ForAdmin
	public void setGroupByLogin(@PathVariable String login, @PathVariable long group_id) {
		userService.setGroupByLogin(login, group_id);
	}

}
