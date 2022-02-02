package com.revo.myboard.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.revo.myboard.entities.User;
import com.revo.myboard.repositories.UserRepository;
import com.revo.myboard.utils.Details;

/*
 * Auth Manager Class
 * 
 * Created By Revo
 * 
 */

@Component
public class AuthManager implements AuthenticationManager {
	
	/*
	 * Data
	 */
	
	@Autowired
	private UserRepository repo;
	@Autowired
	@Lazy
	private BCryptPasswordEncoder encoder;
	
	/*
	 * Auth User From Base
	 */

	@Override
	public Authentication authenticate(Authentication a) throws AuthenticationException {
		User u = repo.findByLogin(a.getPrincipal().toString()).orElseThrow(() -> new BadCredentialsException("Can't find username!"));
		Details d = new Details(u);
		if(a.getPrincipal() == null || a.getCredentials() == null) throw new BadCredentialsException("You miss authentiaction params!");
		if(!encoder.matches(a.getCredentials().toString(), d.getPassword())) throw new BadCredentialsException("Password not match!");
		if(!d.isEnabled()) throw new BadCredentialsException("User is not activ!");
		if(!d.isAccountNonLocked()) throw new BadCredentialsException("User is blocked!");
		return new UsernamePasswordAuthenticationToken(d.getUsername(), null, d.getAuthorities());
	}

}
