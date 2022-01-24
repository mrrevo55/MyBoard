package com.revo.myboard.handlers;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class TokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	/*
	 * Data
	 */
	

	@Value( "${spring.security.jwt.expirationTime}" )
	private long expirationTime;
	@Value( "${spring.security.jwt.secret}" )
	private String secret;

	/*
	 * Constructor
	 */

	public TokenAuthenticationSuccessHandler(long expirationTime, String secret) {
		this.expirationTime = expirationTime;
		this.secret = secret;
	}
	
	/*
	 * Send Token In Authorization Header
	 */

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String token = JWT.create()
				.withSubject(authentication.getName())
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(secret));
		response.addHeader("Authoriazation", "Bearer " + token);
	}

	/*
	 * Get a secret for hashin token
	 */
	
	public String getSecret() {
		return secret;
	}

}
