package com.revo.myboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.revo.myboard.entities.User;
import com.revo.myboard.repositories.UserRepository;
import com.revo.myboard.utils.Details;

/*
 * Details Service Class
 * 
 * Created By Revo
 * 
 */

@Service
public class DetailsService implements UserDetailsService {
	
	/*
	 * Data
	 */
	
	@Autowired
	private UserRepository repo;
	
	/*
	 * Load User From Base
	 */

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = repo.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return new Details(u);
	}

}
