package com.revo.myboard.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.revo.myboard.entities.User;

/*
 * Details Class
 * 
 * Created By Revo
 * 
 */

public class Details implements UserDetails {
	
	/*
	 * Data
	 */
	
	private static final long serialVersionUID = 7502322939676542596L;
	private User user;
	
	/*
	 * Constructor
	 */
	
	public Details(User user) {
		this.user = user;
	}
	
	/*
	 * UserDetails
	 */

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(user.getGroup().toString()).stream().map(n -> new SimpleGrantedAuthority("ROLE_"+n)).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !user.isBlocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isActive();
	}

}
