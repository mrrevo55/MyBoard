package com.revo.myboard.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/*
 * Created By Revo
 */

@Component
public class AuthManager implements AuthenticationManager {
	
	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder encoder;
	
	public AuthManager(UserDetailsService userDetailsService, @Lazy BCryptPasswordEncoder encoder) {
		this.userDetailsService = userDetailsService;
		this.encoder = encoder;
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		UserDetails details = userDetailsService.loadUserByUsername(auth.getPrincipal().toString()); 
		if(auth.getPrincipal() == null || auth.getCredentials() == null) {
			throw new BadCredentialsException("You miss authentiaction params!");
		}
		if(!encoder.matches(auth.getCredentials().toString(), details.getPassword())) {
			throw new BadCredentialsException("Password not match!");
		}
		if(!details.isAccountNonLocked()) {
			throw new BadCredentialsException("User is blocked!");
		}
		if(!details.isEnabled()) {
			throw new BadCredentialsException("User is not active!");
		}
		return new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());
	}

}
