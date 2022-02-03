package com.revo.myboard.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.user.UserService;

/*
 * Created By Revo
 */

@RestController
public class AuthController {
	
	private UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}

	/*
	 * Public
	 */

	@GetMapping("/login")
	public void loginUser(@RequestParam String login, @RequestParam String password) { /* FILTER AUTH HERE */ }
	
	@PostMapping("/register")
	public boolean registerUser(@RequestParam String login, @RequestParam String password, @RequestParam String email) {
		return userService.registerUser(login, password, email);
	}
	
	@PutMapping("/active")
	public boolean activeUserByCode(String code) {
		return userService.activeUserByCode(code);
	}
	
}
