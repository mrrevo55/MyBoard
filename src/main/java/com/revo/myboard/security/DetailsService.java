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
	
	private UserRepository repository;

	public DetailsService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new Details(repository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(username)));
	}

}
