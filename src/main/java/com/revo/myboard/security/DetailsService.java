package com.revo.myboard.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.revo.myboard.user.UserRepository;

/*
 * Created By Revo
 */

@Service
public class DetailsService implements UserDetailsService {
	
	private UserRepository repo;

	public DetailsService(UserRepository repo) {
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = repo.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return new Details(user);
	}

}
