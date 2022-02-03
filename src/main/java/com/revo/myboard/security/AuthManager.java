package com.revo.myboard.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.revo.myboard.user.UserRepository;

/*
 * Created By Revo
 */

@Component
public class AuthManager implements AuthenticationManager {
	
	private UserRepository repo;
	private BCryptPasswordEncoder encoder;
	
	public AuthManager(UserRepository repo, @Lazy BCryptPasswordEncoder encoder) {
		this.repo = repo;
		this.encoder = encoder;
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		var user = repo.findByLogin(auth.getPrincipal().toString()).orElseThrow(() -> new BadCredentialsException("Can't find username!"));
		var details = new Details(user);
		if(auth.getPrincipal() == null || auth.getCredentials() == null) {
			throw new BadCredentialsException("You miss authentiaction params!");
		}
		if(!encoder.matches(auth.getCredentials().toString(), details.getPassword())) {
			throw new BadCredentialsException("Password not match!");
		}
		if(!details.isEnabled()) {
			throw new BadCredentialsException("User is not activ!");
		}
		if(!details.isAccountNonLocked()) {
			throw new BadCredentialsException("User is blocked!");
		}
		return new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());
	}

}
