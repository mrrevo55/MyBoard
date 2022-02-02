package com.revo.myboard.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/*
 * Authentication Filter Class
 * 
 * Created By Revo
 * 
 */

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/*
	 * Auth User
	 */
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getParameter("login"), request.getParameter("password"));
		setDetails(request, token);
		return this.getAuthenticationManager().authenticate(token);
	}

	
	
}
