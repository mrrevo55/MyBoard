package com.revo.myboard.security;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.user.UserService;

/*
 * Created By Revo
 */

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class AuthController {
	
	private UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}

	/*
	 * Public
	 */

	@GetMapping("/login")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public void loginUser(@RequestBody CredentialsDTO crednetialsDTO, HttpServletResponse response) { /* FILTER AUTH HERE */ }
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerUser(@RequestBody RegisterDTO registerDTO) throws AddressException, MessagingException {
		userService.registerUser(registerDTO.getLogin(), registerDTO.getPassword(), registerDTO.getEmail());
	}
	
	@PutMapping("/active")
	public void activeUserByCode(@RequestParam String code) {
		userService.activeUserByCode(code);
	}
	
}
