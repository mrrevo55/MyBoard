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

import com.revo.myboard.exception.NonActiveAccountException;

import lombok.AllArgsConstructor;

/*
 * Created By Revo
 */

@Component
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class AuthManager implements AuthenticationManager {

	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder encoder;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		UserDetails details = userDetailsService.loadUserByUsername(auth.getPrincipal().toString());
		if (auth.getPrincipal() == null || auth.getCredentials() == null) {
			throw new BadCredentialsException("You miss authentiaction params!");
		}
		if (!encoder.matches(auth.getCredentials().toString(), details.getPassword())) {
			throw new BadCredentialsException("Password not match!");
		}
		if (!details.isAccountNonLocked()) {
			throw new BadCredentialsException("User is blocked!");
		}
		if (!details.isEnabled()) {
			throw new NonActiveAccountException(auth.getPrincipal().toString());
		}
		return new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());
	}

}
