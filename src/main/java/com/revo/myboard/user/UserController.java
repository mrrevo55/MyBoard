package com.revo.myboard.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * Created By Revo
 */

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;
	@Value("${spring.security.jwt.secret}")
	private String secret;

	/*
	 * PUBLIC
	 */

	@GetMapping("/{login}")
	public ResponseEntity<UserDTO> getUserByLogin(@PathVariable String login, HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " getting user with login: " + login);
		var user = UserDTO.mapFromUser(userService.getUserByLogin(login));
		log.info("User with ip: " + request.getRemoteAddr() + " success got user with login: " + login);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/search/{content}")
	public ResponseEntity<List<SearchDTO>> getUsersByContent(@PathVariable String content, HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " searching user with content: " + content);
		var users = userService.searchUsersByContent(content).stream()
				.map(targetUser -> SearchDTO.mapFromUser(targetUser)).toList();
		log.info("User with ip: " + request.getRemoteAddr() + " success searched user with content: " + content);
		return ResponseEntity.ok(users);
	}

	@GetMapping("/credentials")
	public ResponseEntity<CredentialsDTO> translateTokenOnCredentials(@RequestHeader("Authorization") String token,
			HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " translating token on credentials");
		var credentials = CredentialsDTO.mapFromUser(this.userService.currentLoggedUser(token));
		log.info("User with ip: " + request.getRemoteAddr() + " success translated token on credentials");
		return ResponseEntity.ok(credentials);
	}

	/*
	 * USER
	 */

	@PatchMapping("/change/password")
	@ForUser
	public void changeUserPassword(@RequestHeader("Authorization") String token,
			@RequestBody @Valid PasswordDTO passwordDTO, HttpServletRequest request) {
		log.info("User wtih ip: " + request.getRemoteAddr() + " changing password for account: "
				+ JWT.require(Algorithm.HMAC256(secret)).build().verify(token.replace("Bearer ", "")).getSubject());
		userService.changeUserPassword(token, passwordDTO.getPassword());
		log.info("User wtih ip: " + request.getRemoteAddr() + " success changed password for account: "
				+ JWT.require(Algorithm.HMAC256(secret)).build().verify(token.replace("Bearer ", "")).getSubject());
	}

	@PatchMapping("/change/email")
	@ForUser
	public void changeUserEmail(@RequestHeader("Authorization") String token, @RequestBody @Valid EmailDTO emailDTO,
			HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " changing email with details: " + emailDTO.toString());
		userService.changeUserEmail(token, emailDTO.getEmail());
		log.info("User with ip: " + request.getRemoteAddr() + " success changed email with details: "
				+ emailDTO.toString());
	}

	@PutMapping("/change/data")
	@ForUser
	public void changeUserData(@RequestHeader("Authorization") String token, @RequestBody @Valid DataDTO dataDTO,
			HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " changing data with details: " + dataDTO.toString());
		userService.changeUserData(token, dataDTO.getDescription(), dataDTO.getAge(), dataDTO.getCity(),
				dataDTO.getPage(), dataDTO.getSex());
		log.info("User with ip: " + request.getRemoteAddr() + " success changed data with details: "
				+ dataDTO.toString());
	}

	@GetMapping("/profile")
	@ForUser
	public ResponseEntity<ProfileDTO> currentLoggedUserProfile(@RequestHeader("Authorization") String token,
			HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " getting profile for login: "
				+ JWT.require(Algorithm.HMAC256(secret)).build().verify(token.replace("Bearer ", "")).getSubject());
		var profile = ProfileDTO.mapFromUser(userService.currentLoggedUser(token));
		log.info("User with ip: " + request.getRemoteAddr() + " got profile for login: "
				+ JWT.require(Algorithm.HMAC256(secret)).build().verify(token.replace("Bearer ", "")).getSubject());
		return ResponseEntity.ok(profile);
	}

	@DeleteMapping("/delete/{login}")
	@ForUser
	public void deleteUserByLoggedUser(@RequestHeader("Authorization") String token, @PathVariable String login, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" deleting account with login: "+login);
		userService.deleteUserByLogin(token, login);
		log.info("User with ip: "+request.getRemoteAddr()+" success deleted account with login: "+login);
	}

	@GetMapping("/sexes")
	@ForUser
	public ResponseEntity<List<String>> getSexList(HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" getting sex list");
		var sexList = userService.getSexList();
		log.info("User with ip: "+request.getRemoteAddr()+" success got sex list");
		return ResponseEntity.ok(sexList);
	}

	/*
	 * MODERATOR
	 */

	@PatchMapping("/ban/{login}")
	@ForModerator
	public void banUserByLogin(@PathVariable String login, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" banning account with login: "+login);
		userService.banUserByLogin(login);
		log.info("User with ip: "+request.getRemoteAddr()+" success banned account with login: "+login);
	}

	@PatchMapping("/unban/{login}")
	@ForModerator
	public void unbanUserByLogin(@PathVariable String login, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" unbanning account with login: "+login);
		userService.unbanUserByLogin(login);
		log.info("User with ip: "+request.getRemoteAddr()+" success unbanned account with login: "+login);
	}

	/*
	 * ADMIN
	 */

	@PatchMapping("/group/{login}/{group_id}")
	@ForAdmin
	public void setGroupByLogin(@PathVariable String login, @PathVariable long group_id, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" change account group with login: "+login +" to group of id: "+group_id);
		userService.setGroupByLogin(login, group_id);
		log.info("User with ip: "+request.getRemoteAddr()+" success changed account group with login: "+login +" to group of id: "+group_id);
	}

}
