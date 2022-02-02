package com.revo.myboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.services.UserService;

/*
 * AuthController Class
 * 
 * Created By Revo
 */

@RestController
public class AuthController {
	
	/*
	 * Data
	 */
	
	@Autowired
	private UserService userService;
	
	/*
	 * Login User
	 */

	@PostMapping
	public void login(@RequestParam String login, @RequestParam String password) { /* FILTER AUTH HERE */ }
	
	/*
	 * Register New User
	 */
	
	@PostMapping("/register")
	public boolean register(@RequestParam String login, @RequestParam String password, @RequestParam String email) {
		return userService.register(login, password, email);
	}

	/*
	 * Active User
	 */
	
	@GetMapping("/active")
	public boolean active(String code) {
		return userService.active(code);
	}
	
}
