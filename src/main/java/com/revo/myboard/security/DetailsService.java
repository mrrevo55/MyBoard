package com.revo.myboard.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.revo.myboard.user.UserRepository;

import lombok.AllArgsConstructor;

/*
 * Created By Revo
 */

@Service
@AllArgsConstructor
public class DetailsService implements UserDetailsService {
	
	private final UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return Details.builder().user(repository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(username))).build();
	}

}
