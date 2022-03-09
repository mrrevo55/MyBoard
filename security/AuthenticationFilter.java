package com.revo.myboard.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revo.myboard.security.dto.CredentialsDTO;


/*
 * Created By Revo
 */

@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final ObjectMapper objectMapper;
	
	public AuthenticationFilter(AuthenticationSuccessHandler successHandler, AuthManager authManager, ObjectMapper objectMapper, AuthenticationFailureHandler failureHandler) {
		this.objectMapper = objectMapper;
		this.setAuthenticationSuccessHandler(successHandler);
		this.setAuthenticationFailureHandler(failureHandler);
		this.setAuthenticationManager(authManager);
		this.setFilterProcessesUrl("/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			CredentialsDTO credentials = objectMapper.readValue(request.getInputStream(), CredentialsDTO.class);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getLogin(), credentials.getPassword());
			setDetails(request, token);
			return this.getAuthenticationManager().authenticate(token);
		} catch (IOException exception) { throw new InternalAuthenticationServiceException("Something went wrong while parsing /login request body", exception); }
	}
	
}
