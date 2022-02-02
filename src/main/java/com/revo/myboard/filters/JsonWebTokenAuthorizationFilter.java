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
import com.auth0.jwt.exceptions.TokenExpiredException;

/*
 * Json Web Token Authentiaction Filter Class
 * 
 * Created By Revo
 * 
 */

public class JsonWebTokenAuthorizationFilter extends BasicAuthenticationFilter {

	/*
	 * Data
	 */

	private static final String TOKEN_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";
	private final UserDetailsService userDetailsService;
	private final String secret;

	/*
	 * Constructor
	 */

	public JsonWebTokenAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
			String secret) {
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
		this.secret = secret;
	}
	
	/*
	 * Authorize Before Redirect If Token Match
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
	 * Get User Details By Token From Header
	 */

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		try{
			String token = request.getHeader(TOKEN_HEADER); 
			if (token != null && token.startsWith(TOKEN_PREFIX)) {
				String userName = JWT.require(Algorithm.HMAC256(secret)) 
						.build().verify(token.replace(TOKEN_PREFIX, "")) 
						.getSubject(); 
				if (userName != null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(userName); 
					return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,
							userDetails.getAuthorities());
				}
			}
		}catch(TokenExpiredException e) {}	
		return null;
	}

}
