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
import com.revo.myboard.group.Authority;
import com.revo.myboard.group.GroupService;

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
	private GroupService groupService;

	public UserService(UserRepository repository, BCryptPasswordEncoder encoder, EmailService emailService,
			GroupService groupService) {
		this.repository = repository;
		this.encoder = encoder;
		this.emailService = emailService;
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
		var code = generateCode();
		while (repository.findByCode(code).isPresent()) {
			code = generateCode();
		}
		user.setCode(code);
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
	
	public void resendActivationCode(String email) throws AddressException, MessagingException {
		var user = repository.findByEmail(email).orElseThrow(() -> new NullPointerException(email));
		if (user.isActive()) {
			throw new IllegalArgumentException(email);
		}
		var code = generateCode();
		while (repository.findByCode(code).isPresent()) {
			code = generateCode();
		}
		user.setCode(code);
		emailService.sendActiavtionLink(email, code);
	}

	public List<User> searchUsersByContent(String content) {
		return repository.findAll().stream()
				.filter(user -> user.getLogin().toLowerCase().contains(content.toLowerCase())).toList();
	}

	public void activeUserByCode(String code) {
		var user = repository.findByCode(code).orElseThrow(() -> new NullPointerException(code));
		if (user.isActive()) {
			throw new IllegalArgumentException(code);
		}
		user.setActive(true);
	}

	private String generateCode() {
		StringBuilder code = new StringBuilder();
		StringBuilder letters = new StringBuilder();
		letters.append("abcdefghijklmnopqrstuvwxyz");
		letters.append("abcdefghijklmnopqrstuvwxyz".toUpperCase());
		letters.append("!@$%^&*");
		for (int x = 0; x < 10; x++) {
			code.append(letters.charAt(new Random().nextInt(letters.length() - 1)));
		}
		return code.toString();
	}

	public User currentLoggedUser(String token) {
		return repository.findByLogin(
				JWT.require(Algorithm.HMAC256(secret)).build().verify(token.replace("Bearer ", "")).getSubject())
				.orElseThrow(() -> new NullPointerException(token));
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
		if (user.isBlocked()) {
			throw new IllegalArgumentException(login);
		}
		user.setBlocked(true);
	}

	public void unbanUserByLogin(String login) {
		var user = getUserByLogin(login);
		if (!user.isBlocked()) {
			throw new IllegalArgumentException(login);
		}
		user.setBlocked(false);
	}

	public void changeUserData(String token, String description, int age, String city, String page, String sex) {
		var data = currentLoggedUser(token).getData();
		data.setDescription(description);
		data.setAge(age);
		data.setCity(city);
		data.setPage(page);
		data.setSex(Sex.valueOf(sex.toUpperCase()));
	}

	public void changeUserEmail(String token, String new_email) {
		var user = currentLoggedUser(token);
		user.setEmail(new_email);
	}

	public void setGroupByLogin(String login, long group_id) {
		getUserByLogin(login).setGroup(groupService.getGroupById(group_id));
	}

	public void deleteUserByLogin(String token, String login) {
		var user = currentLoggedUser(token);
		var userTarget = getUserByLogin(login);
		if (!user.getGroup().getAuthority().equals(Authority.ADMIN) && !user.equals(userTarget)) {
			throw new IllegalArgumentException(login);
		}
		repository.delete(userTarget);
	}

	public List<String> getSexList() {
		return Arrays.asList(Sex.values()).stream().map(sex -> sex.toString()).toList();
	}

	public void activeByLogin(String login) {
		var user = getUserByLogin(login);
		if(user.isActive()) {
			throw new IllegalArgumentException(login);
		}
		user.setActive(true);
	}

}
