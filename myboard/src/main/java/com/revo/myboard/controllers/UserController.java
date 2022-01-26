package com.revo.myboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.services.UserService;

/*
 * User Controller Class
 * 
 * Created By Revo
 * 
 */

@RestController
public final class UserController {
	
	/*
	 * Data
	 */
	
	@Autowired
	private UserService userService;
	
	/*
	 * Test End Point
	 */
	
	@RequestMapping("/test")
	public String test() {
		return "OOO DZIALA!!";
	}
	
	@RequestMapping("/testAdmin")
	@Secured("ROLE_ADMIN")
	public String testA() {
		return "OOO DZIALA ADMIN!!";
	}
	
	@RequestMapping("/testUser")
	@Secured({"ROLE_ADMIN","ROLE_MODERATOR","ROLE_USER"})
	public String testU() {
		return "OOO DZIALA USER!!";
	}

	/*
	 * Register New User
	 */
	
	@PostMapping("/register")
	public boolean register(@RequestParam String login, @RequestParam String password, @RequestParam String email) {
		return userService.register(login, password, email);
	}

	/*
	 * Test User Dao Finding Users
	 */
	
	@GetMapping("/active")
	public boolean active(String code) {
		return userService.active(code);
	}
}
