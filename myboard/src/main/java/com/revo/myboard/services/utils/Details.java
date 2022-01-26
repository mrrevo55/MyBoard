package com.revo.myboard.services.utils;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.revo.myboard.entities.User;

/*
 * User Details Class
 * 
 * Created By Revo
 */


public class Details implements UserDetails {

	/*
	 * Data
	 */
	
	private static final long serialVersionUID = -6125028147031281247L;
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
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_"+user.getGroup().toString()));
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
