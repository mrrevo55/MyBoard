package com.revo.myboard.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/*
 * Authentication Filter Class
 * 
 * Created By Revo
 */

public class AuthenticationFilter extends BasicAuthenticationFilter {

	/*
	 * Data
	 */

	private final UserDetailsService userDetailsService;
	private final String secret;

	/*
	 * Constructor
	 */

	public AuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
			String secret) {
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
		this.secret = secret;
	}
	
	/*
	 * Try Authenticate User
	 */

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		if (authentication == null) {
			filterChain.doFilter(request, response);
			return;
		}
		SecurityContextHolder.getContext().setAuthentication(authentication); 
		filterChain.doFilter(request, response);
	}
	
	/*
	 * Check User Authorization Token Is Correct
	 */

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader("Authorization"); 
		if (token != null && token.startsWith("Bearer ")) {
			String userName = JWT.require(Algorithm.HMAC256(secret)) 
					.build().verify(token.replace("Bearer ", "")) 
					.getSubject(); 
			if (userName != null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(userName); 
				return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,
						userDetails.getAuthorities()); 
			}
		}
		return null;
	}
}
