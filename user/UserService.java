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
import com.revo.myboard.email.EmailService;
import com.revo.myboard.exception.ArgumentInUseException;
import com.revo.myboard.exception.ObjectNotExistsException;
import com.revo.myboard.group.Authority;
import com.revo.myboard.group.GroupService;

import lombok.RequiredArgsConstructor;

/*
 * Created By Revo
 */

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;
	private final BCryptPasswordEncoder encoder;
	private final EmailService emailService;
	@Value("${spring.security.jwt.secret}")
	private String secret;
	private final GroupService groupService;

	public void registerUser(String login, String password, String email) {
		if (repository.findByEmail(email).isPresent()) {
			throw new ArgumentInUseException(email);
		}
		if (repository.findByLogin(login).isPresent()) {
			throw new ArgumentInUseException(login);
		}
		var code = generateCode();
		while (repository.findByCode(code).isPresent()) {
			code = generateCode();
		}
		emailService.sendActiavtionLink(email, code);
		repository.save(User.builder().login(login).password(encoder.encode(password)).email(email).code(code)
				.data(Data.builder().age(18).city("Brak").description("Brak").description("Brak").page("Brak")
						.sex(Sex.MĘŻCZYZNA).build())
				.group(groupService.getDefaultGroup()).build());
	}

	public void resendActivationCode(String email) {
		var user = repository.findByEmail(email).orElseThrow(() -> new ObjectNotExistsException(email));
		if (user.isActive()) {
			throw new ArgumentInUseException(email);
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
		var user = repository.findByCode(code).orElseThrow(() -> new ObjectNotExistsException(code));
		if (user.isActive()) {
			throw new ArgumentInUseException(code);
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
				.orElseThrow(() -> new ObjectNotExistsException(token));
	}

	public User getUserByLogin(String login) {
		return repository.findByLogin(login).orElseThrow(() -> new ObjectNotExistsException(login));
	}

	public void changeUserPassword(String token, String new_password) {
		var user = currentLoggedUser(token);
		if (encoder.matches(new_password, user.getPassword())) {
			throw new ArgumentInUseException(new_password);
		}
		user.setPassword(encoder.encode(new_password));
	}

	public void banUserByLogin(String login) {
		var user = getUserByLogin(login);
		if (user.isBlocked()) {
			throw new ArgumentInUseException(login);
		}
		user.setBlocked(true);
	}

	public void unbanUserByLogin(String login) {
		var user = getUserByLogin(login);
		if (!user.isBlocked()) {
			throw new ArgumentInUseException(login);
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
			throw new ArgumentInUseException(login);
		}
		repository.delete(userTarget);
	}

	public List<String> getSexList() {
		return Arrays.asList(Sex.values()).stream().map(sex -> sex.toString()).toList();
	}

	public void activeByLogin(String login) {
		var user = getUserByLogin(login);
		if (user.isActive()) {
			throw new ArgumentInUseException(login);
		}
		user.setActive(true);
	}

}
