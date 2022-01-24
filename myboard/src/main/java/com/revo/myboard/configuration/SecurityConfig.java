package com.revo.myboard.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.revo.myboard.filters.AuthenticationFilter;
import com.revo.myboard.handlers.TokenAuthenticationSuccessHandler;
import com.revo.myboard.services.DetailsService;

/*
 * Security Config Class
 * 
 * Created By Revo
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, 
		  securedEnabled = true, 
		  jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/*
	 * Data
	 */
	
	@Autowired
	private DetailsService userDetailsService;
	@Autowired
	private TokenAuthenticationSuccessHandler tokenHandler;
	
	/*
	 * Configure User Details Service
	 */
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder()); 
	}

	
	/*
	 * Security Rules
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// HTTP Token Authentication Configuration
		http
		.formLogin()
		.successHandler(tokenHandler)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(new AuthenticationFilter(authenticationManager(), super.userDetailsService(), tokenHandler.getSecret()), BasicAuthenticationFilter.class)
        .exceptionHandling()
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
		// HTTP CSRF DISABLE
		http
		.csrf().disable();
	
	}
	
	/*
	 * Authentication Password Encoder
	 */

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

}
