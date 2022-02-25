package com.revo.myboard.user;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.revo.myboard.email.EmailService;
import com.revo.myboard.group.GroupService;
import com.revo.myboard.like.LikeService;

/*
 * Created By Revo
 */

@Service
@Transactional
public class UserService {

	private UserRepository repository;
	private BCryptPasswordEncoder encoder;
	private EmailService emailService;
	@Value("${spring.security.jwt.secret}")
	private String secret;
	private LikeService likeService;
	private GroupService groupService;

	public UserService(UserRepository repository, BCryptPasswordEncoder encoder, EmailService emailService,
			LikeService likeService, GroupService groupService) {
		this.repository = repository;
		this.encoder = encoder;
		this.emailService = emailService;
		this.likeService = likeService;
		this.groupService = groupService;
	}

	public void registerUser(String login, String password, String email) throws AddressException, MessagingException {
		if (repository.findByEmail(email).isPresent()) {
			throw new IllegalArgumentException(email);
		}
		if (repository.findByLogin(login).isPresent()) {
			throw new IllegalArgumentException(login);
		}
		var user = new User();
		user.setLogin(login);
		user.setPassword(encoder.encode(password));
		user.setEmail(email);
		user.setCode(generateCode());
		var data = new Data();
		data.setAge(0);
		data.setCity("Brak");
		data.setDescription("Brak");
		data.setPage("Brak");
		data.setSex(Sex.MĘŻCZYZNA);
		user.setData(data);
		user.setGroup(groupService.getDefaultGroup());
		emailService.sendActiavtionLink(email, user.getCode());
		repository.save(user);
	}
	
	public List<User> searchUsersByContent(String content){
		return repository.findAll().stream().filter(user -> user.getLogin().toLowerCase().contains(content.toLowerCase())).toList();
	}

	public void activeUserByCode(String code) {
		var user = repository.findByCode(code).orElseThrow(() -> new NullPointerException(code));
		if(user.isActive()) {
			throw new IllegalArgumentException(code);
		}
		user.setActive(true);
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
			return repository.findByLogin(JWT.require(Algorithm.HMAC256(secret)).build().verify(token.replace("Bearer ", "")).getSubject()).orElseThrow();
		} catch (Exception e) {
			throw new NullPointerException(token);
		}

	}

	public User getUserByLogin(String login) {
		return repository.findByLogin(login).orElseThrow(() -> new NullPointerException(login));
	}

	public void changeUserPassword(String token, String new_password) {
		var user = currentLoggedUser(token);
		if (encoder.matches(new_password, user.getPassword())) {
			throw new IllegalArgumentException(new_password);
		}
		user.setPassword(encoder.encode(new_password));
	}

	public void banUserByLogin(String login) {
		var user = getUserByLogin(login);
		if(user.isBlocked()) {
			throw new IllegalArgumentException(login);
		}
		user.setBlocked(true);
	}

	public void unbanUserByLogin(String login) {
		var user = getUserByLogin(login);
		if(!user.isBlocked()) {
			throw new IllegalArgumentException(login);
		}
		user.setBlocked(false);
	}

	public void changeUserDescription(String token, String description) {
		currentLoggedUser(token).getData().setDescription(description);
	}

	public void changeUserAge(String token, int age) {
		currentLoggedUser(token).getData().setAge(age);
	}

	public void changeUserCity(String token, String city) {
		currentLoggedUser(token).getData().setCity(city);
	}

	public void changeUserPage(String token, String page) {
		currentLoggedUser(token).getData().setPage(page);
	}

	public void changeUserSex(String token, String sex) {
		currentLoggedUser(token).getData().setSex(Sex.valueOf(sex.toUpperCase()));
	}

	public void changeUserEmail(String token, String new_email) {
		var user = currentLoggedUser(token);
		user.setEmail(new_email);
	}

	public void giveLikeForUserByLogin(String token, String login) {
		likeService.giveForUserByLogin(token, login);
	}

	public void removeLikeFromUserByLogin(String token, String login) {
		likeService.removeFromUserByLogin(token, login);
	}

	public void setGroupByLogin(String login, long group_id) {
		getUserByLogin(login).setGroup(groupService.getGroupById(group_id));
	}

	public void deleteUserByLoggedUser(String token) {
		repository.delete(currentLoggedUser(token));
	}

	public void deleteUserAsAdmin(String login) {
		repository.delete(getUserByLogin(login));
	}

	public List<String> getSexList() {
		return Arrays.asList(Sex.values()).stream().map(sex -> sex.toString()).toList();
	}

}
