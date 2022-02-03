package com.revo.myboard.user;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.revo.myboard.email.EmailService;
import com.revo.myboard.like.LikeService;
import com.revo.myboard.util.ObjectNotExistsException;

/*
 * Created By Revo
 */

@Service
@Transactional
public class UserService {

	private UserRepository userRepo;
	private BCryptPasswordEncoder encoder;
	private EmailService emailService;
	@Value("${spring.security.jwt.secret}")
	private String secret;
	private LikeService likeService;

	public UserService(UserRepository userRepo, BCryptPasswordEncoder encoder, EmailService emailService,
			LikeService likeService) {
		super();
		this.userRepo = userRepo;
		this.encoder = encoder;
		this.emailService = emailService;
		this.likeService = likeService;
	}

	public boolean registerUser(String login, String password, String email) {		
		if (userRepo.findByEmail(email).isPresent()) {
			return false;
		}
		if (userRepo.findByLogin(login).isPresent()) {
			return false;
		}
		var user = new User();
		user.setLogin(login);
		user.setPassword(encoder.encode(password));
		user.setEmail(email);
		user.setCode(generateCode());
		user.setData(new Data());
		//if(!emailService.sendActiavtionLink(email, user.getCode())) {
		//	return false;
		//}
		userRepo.save(user);
		return true;
	}

	public boolean activeUserByCode(String code) {
		var user = userRepo.findByCode(code).orElseThrow(() -> new ObjectNotExistsException(code));
		if (!user.getCode().equals(code)) {
			return false;
		}
		user.setActive(true);
		return true;
	}
	
	private String generateCode() {
		StringBuilder code = new StringBuilder();
		StringBuilder letters = new StringBuilder();
		letters.append("abcdefghijklmnopqrstuvwxyz");
		letters.append("abcdefghijklmnopqrstuvwxyz".toUpperCase());
		letters.append("!@#$%^&*");
		for (int x = 0; x < 10; x++) {
			code.append(letters.charAt(new Random().nextInt(letters.length() - 1)));
		}
		return code.toString();

	}

	public User currentLoggedUser(String token) {
		try {
			var name = JWT.require(Algorithm.HMAC256(secret)).build().verify(token.replace("Bearer ", "")).getSubject();
			return userRepo.findByLogin(name).orElseThrow(() -> new ObjectNotExistsException(name));
		} catch (TokenExpiredException e) {
			return null;
		}

	}

	public User getUserByLogin(String login) {
		return userRepo.findByLogin(login).orElseThrow(() -> new ObjectNotExistsException(login));
	}

	public boolean changeUserPassword(String token, String new_password) {
		var user = currentLoggedUser(token);
		if (encoder.matches(new_password, user.getPassword())) {
			return false;
		}
		user.setPassword(encoder.encode(new_password));
		return true;
	}

	public void banUserByLogin(String login) {
		var user = getUserByLogin(login);
		user.setBlocked(true);
	}
	
	public boolean unbanUserByLogin(String login) {
		var user = getUserByLogin(login);
		user.setBlocked(false);
		return true;
	}

	public boolean changeUserDescription(String token, String description) {
		var user = currentLoggedUser(token);
		if(user.getData().getDescription().equals(description)) {
			return false;
		}
		user.getData().setDescription(description);
		return true;
	}

	public boolean changeUserAge(String token, int age) {
		var user = currentLoggedUser(token);
		if(user.getData().getAge() == age) {
			return false;
		}
		user.getData().setAge(age);
		return true;
	}

	public boolean changeUserCountry(String token, String country) {
		try {
			var user = currentLoggedUser(token);
			if (user.getData().getCountry().toString().equals(country)) {
				return false;
			}
			user.getData().setCountry(Country.valueOf(country));
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	public boolean changeUserPage(String token, String page) {
		var user = currentLoggedUser(token);
		if(user.getData().getPage().equals(page)) {
			return false;
		}
		user.getData().setPage(page);
		return true;
	}

	public boolean changeUserSex(String token, String sex) {
		try {
			var user = currentLoggedUser(token);
			if (user.getData().getSex().toString().equals(sex)) {
				return false;
			}
			user.getData().setSex(Sex.valueOf(sex));
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	public boolean changeUserEmail(String token, String new_email) {
		var user = currentLoggedUser(token);
		if (user.getEmail().equals(new_email)) {
			return false;
		}
		user.setEmail(new_email);
		return true;
	}

	public boolean giveLikeForUserByLogin(String token, String login) {
		return likeService.giveForUserByLogin(token, login);
	}

	public boolean removeLikeFromUserByLogin(String token, String login) {
		return likeService.removeFromUserByLogin(token, login);
	}

	public boolean setGroupByLogin(String login, String group) {
		try {
			var user = getUserByLogin(login);
			if (user.getGroup().equals(Group.valueOf(group))) {
				return false;
			}
			user.setGroup(Group.valueOf(group));
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	public void deleteUserByLoggedUser(String token) {
		var user = currentLoggedUser(token);
		userRepo.delete(user);
	}

	public void deleteUserAsAdmin(String login) {
		var user = getUserByLogin(login);
		userRepo.delete(user);
	}
	
	public List<String> getGroupList(){
		return Arrays.asList(Group.values()).stream().map(group -> group.toString()).toList();
	}
	
	public List<String> getSexList(){
		return Arrays.asList(Sex.values()).stream().map(sex -> sex.toString()).toList();
	}
	
	public List<String> getCountryList(){
		return Arrays.asList(Country.values()).stream().map(country -> country.toString()).toList();
	}

}
