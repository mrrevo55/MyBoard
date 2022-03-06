package com.revo.myboard.security;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.security.annotation.ForAdmin;
import com.revo.myboard.security.dto.CodeDTO;
import com.revo.myboard.security.dto.CredentialsDTO;
import com.revo.myboard.security.dto.RegisterDTO;
import com.revo.myboard.user.UserService;
import com.revo.myboard.user.dto.EmailDTO;

/*
 * Created By Revo
 */

@RestController
@Validated
public class AuthController {
	
	private UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}

	/*
	 * Public
	 */

	@PostMapping("/login")
	public void loginUser(@RequestBody @Valid CredentialsDTO credentialsDTO, HttpServletResponse response) { /* FILTER AUTH HERE */ }
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerUser(@RequestBody @Valid RegisterDTO registerDTO) throws AddressException, MessagingException {
		userService.registerUser(registerDTO.getLogin(), registerDTO.getPassword(), registerDTO.getEmail());
	}
	
	@PatchMapping("/active")
	public void activeUserByCode(@RequestBody @Valid CodeDTO codeDTO) {
		userService.activeUserByCode(codeDTO.getCode());
	}
	
	@PatchMapping("/active/{login}")
	@ForAdmin
	public void activeUserByLogin(@PathVariable String login){
		userService.activeByLogin(login);
	}
	
	@PatchMapping("resendActivationCode")
	public void resenedActivationCode(@RequestBody @Valid  EmailDTO emailDTO) throws AddressException, MessagingException {
		userService.resendActivationCode(emailDTO.getEmail());
	}
}
