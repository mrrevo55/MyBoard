package com.revo.myboard.services;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.revo.myboard.entities.User;
import com.revo.myboard.repositories.UserRepository;

/*
 * User Service Class
 * 
 * Created By Revo
 */

@Service
public class UserService {
	
	/*
	 * Data
	 */
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private EmailService emailService;
	
	/*
	 * Register New User
	 */
	
	public boolean register(String login, String password, String email) {
		if(userRepo.findByEmail(email).isPresent()) return false;
		if(userRepo.findByLogin(login).isPresent()) return false;
		User u = new User();
		u.setLogin(login);
		u.setPassword(encoder.encode(password));
		u.setEmail(email);
		u.setCode(generateCode());
		if(!emailService.sendActiavtionLink(email)) return false;
		userRepo.save(u);
		return true;
	}
	
	/*
	 * Active User 
	 */
	
	public boolean active(String code) {
		Optional<User> u = userRepo.findByCode(code);
		if(u.isEmpty()) return false;
		if(!u.get().getCode().equals(code)) return false;
		u.get().setActive(true);
		return true;
	}
	
	/*
	 * Generate Activation Code
	 */

	private String generateCode() {
		StringBuilder code = new StringBuilder();
		StringBuilder letters = new StringBuilder();
		letters.append("abcdefghijklmnopqrstuvwxyz");
		letters.append("abcdefghijklmnopqrstuvwxyz".toUpperCase());
		letters.append("!@#$%^&*");
		for(int x = 0; x < 10; x++)
			code.append(letters.charAt(new Random().nextInt(letters.length() - 1)));
		return code.toString();
			
	}
}
