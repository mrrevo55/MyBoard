package com.revo.myboard.handlers;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/*
 * Success Handler Class
 * 
 * Created By Revo
 * 
 */

@Component
public class JsonAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	/*
	 * Data
	 */
	
	@Value("${spring.security.jwt.secret}")
	private String secret;
	@Value("${spring.security.jwt.expirationTime}")
	private long expirationTime;

	/*
	 * On Auth Success Clear Atributes
	 */

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String token = JWT.create()
				.withSubject(authentication.getPrincipal().toString())
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(secret));
		response.setHeader("Authorization", "Bearer " + token);
	}

}
