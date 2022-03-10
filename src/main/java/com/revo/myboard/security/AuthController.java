package com.revo.myboard.security;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.security.annotation.ForAdmin;
import com.revo.myboard.security.dto.CodeDTO;
import com.revo.myboard.security.dto.CredentialsDTO;
import com.revo.myboard.security.dto.RegisterDTO;
import com.revo.myboard.user.UserService;
import com.revo.myboard.user.dto.EmailDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * Created By Revo
 */

@RestController
@Validated
@AllArgsConstructor
@Slf4j
public class AuthController {

	private final UserService userService;

	/*
	 * Public
	 */
	
	@PostMapping("/login")
	public void loginUser(@RequestBody @Valid CredentialsDTO credentialsDTO, HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " try to login on account: " + credentialsDTO.getLogin());
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerUser(@RequestBody @Valid RegisterDTO registerDTO, HttpServletRequest request) {
		log.info("User with ip: "+ request.getRemoteAddr() + " try to register with login: "+registerDTO.getLogin()+" and email: "+registerDTO.getEmail());
		userService.registerUser(registerDTO.getLogin(), registerDTO.getPassword(), registerDTO.getEmail());
		log.info("User with ip: "+ request.getRemoteAddr() + " success registered with login: "+registerDTO.getLogin()+" and email: "+registerDTO.getEmail());
	}

	@PatchMapping("/active")
	public void activeUserByCode(@RequestBody @Valid CodeDTO codeDTO, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" activating account with details: "+codeDTO.toString());
		userService.activeUserByCode(codeDTO.getCode());
		log.info("User with ip: "+request.getRemoteAddr()+" success activated account with details: "+codeDTO.toString());
	}

	@PatchMapping("/active/{login}")
	@ForAdmin
	public void activeUserByLogin(@PathVariable String login, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" activating account with login: "+login);
		userService.activeByLogin(login);
		log.info("User with ip: "+request.getRemoteAddr()+" success activated account with login: "+login);
	}

	@PatchMapping("/resendActivationCode")
	public void resenedActivationCode(@RequestBody @Valid EmailDTO emailDTO, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" resending activation code with details: "+emailDTO.toString());
		userService.resendActivationCode(emailDTO.getEmail());
		log.info("User with ip: "+request.getRemoteAddr()+" success resended activation code with details: "+emailDTO.toString());
	}
}
