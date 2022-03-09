package com.revo.myboard.security;

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
 * Created By Revo
 */

public class TokenAuthorizationFilter extends BasicAuthenticationFilter {

	private static final String TOKEN_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";
	private final UserDetailsService userDetailsService;
	private final String secret;

	public TokenAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
			String secret) {
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
		this.secret = secret;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		var authentication = getAuthenticationToken(request);
		if (authentication == null) {
			filterChain.doFilter(request, response);
			return;
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
		var token = request.getHeader(TOKEN_HEADER);
		if (token != null && token.startsWith(TOKEN_PREFIX)) {
			var userName = JWT.require(Algorithm.HMAC256(secret)).build().verify(token.replace(TOKEN_PREFIX, ""))
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
