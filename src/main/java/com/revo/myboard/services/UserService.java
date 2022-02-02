package com.revo.myboard.services;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.revo.myboard.entities.User;
import com.revo.myboard.entities.Country;
import com.revo.myboard.entities.Data;
import com.revo.myboard.entities.Group;
import com.revo.myboard.entities.Sex;
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
	@Value("${spring.security.jwt.secret}")
	private String secret;
	@Autowired
	private LikeService likeService;

	/*
	 * Register New User
	 */

	public boolean register(String login, String password, String email) {
		if (userRepo.findByEmail(email).isPresent())
			return false;
		if (userRepo.findByLogin(login).isPresent())
			return false;
		User u = new User();
		u.setLogin(login);
		u.setPassword(encoder.encode(password));
		u.setEmail(email);
		u.setCode(generateCode());
		u.setData(new Data());
		if(!emailService.sendActiavtionLink(email, u.getCode())) return false;
		userRepo.save(u);
		return true;
	}

	/*
	 * Active User
	 */

	@Transactional
	public boolean active(String code) {
		Optional<User> u = userRepo.findByCode(code);
		if (u.isEmpty())
			return false;
		if (!u.get().getCode().equals(code))
			return false;
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
		for (int x = 0; x < 10; x++)
			code.append(letters.charAt(new Random().nextInt(letters.length() - 1)));
		return code.toString();

	}

	/*
	 * Get Current Logged User
	 */

	public User currentLogged(String token) {
		try {
			return userRepo.findByLogin(
					JWT.require(Algorithm.HMAC256(secret)).build().verify(token.replace("Bearer ", "")).getSubject())
					.get();
		} catch (NoSuchElementException e) {
			return null;
		}

	}

	/*
	 * Get User By Login
	 */

	public User get(String login) {
		try {
			return userRepo.findByLogin(login).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	/*
	 * Change User Password
	 */

	@Transactional
	public boolean changePassword(String token, String new_password) {
		User u = currentLogged(token);
		if (encoder.matches(new_password, u.getPassword()))
			return false;
		u.setPassword(encoder.encode(new_password));
		return true;
	}

	/*
	 * Ban User
	 */

	@Transactional
	public boolean banUser(String login) {
		User u = userRepo.findByLogin(login).get();
		if (u == null || u.isBlocked())
			return false;
		u.setBlocked(true);
		return true;
	}

	/*
	 * Unban User
	 */

	@Transactional
	public boolean unbanUser(String login) {
		User u = userRepo.findByLogin(login).get();
		if (u == null || !u.isBlocked())
			return false;
		u.setBlocked(false);
		return true;
	}

	/*
	 * Change User Description
	 */

	@Transactional
	public boolean changeDescription(String token, String description) {
		User u = currentLogged(token);
		if (u == null)
			return false;
		u.getData().setDescription(description);
		return true;
	}

	/*
	 * Change User Age
	 */

	@Transactional
	public boolean changeAge(String token, int age) {
		User u = currentLogged(token);
		if (u == null)
			return false;
		u.getData().setAge(age);
		return true;
	}

	/*
	 * Change User Country
	 */

	@Transactional
	public boolean changeCountry(String token, String country) {
		try {
			User u = currentLogged(token);
			if (u == null)
				return false;
			u.getData().setCountry(Country.valueOf(country));
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	/*
	 * Change User Page
	 */

	@Transactional
	public boolean changePage(String token, String page) {
		User u = currentLogged(token);
		if (u == null)
			return false;
		u.getData().setPage(page);
		return true;
	}

	/*
	 * Change User Sex
	 */

	@Transactional
	public boolean changeSex(String token, String sex) {
		try {
			User u = currentLogged(token);
			if (u == null)
				return false;
			u.getData().setSex(Sex.valueOf(sex));
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	/*
	 * Change User Email
	 */

	@Transactional
	public boolean changeEmail(String token, String new_email) {
		User u = currentLogged(token);
		if (u == null || u.getEmail().equals(new_email))
			return false;
		u.setEmail(new_email);
		return true;
	}

	/*
	 * Give Like To User
	 */

	@Transactional
	public boolean giveLike(String token, String login) {
		return likeService.giveForUser(token, login);
	}

	/*
	 * Remove Like From User
	 */

	@Transactional
	public boolean removeLike(String token, String login) {
		return likeService.removeForUser(token, login);
	}

	/*
	 * Set User Group
	 */

	@Transactional
	public boolean setGroup(String login, String group) {
		try {
			User u = userRepo.findByLogin(login).get();
			if (u == null || u.getGroup().equals(Group.valueOf(group)))
				return false;
			u.setGroup(Group.valueOf(group));
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	/*
	 * Delete User Account
	 */

	public boolean deleteByUser(String token) {
		User u = currentLogged(token);
		if (u == null)
			return false;
		userRepo.delete(u);
		return true;
	}

	public boolean delete(String login) {
		User u = get(login);
		if (u == null)
			return false;
		userRepo.delete(u);
		return true;
	}

}
