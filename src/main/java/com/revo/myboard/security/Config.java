package com.revo.myboard.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

/*
 * Created By Revo
 */

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class Config extends WebSecurityConfigurerAdapter {
	
	@Value("${spring.security.jwt.secret}")
	private String secret;
	private AuthenticationSuccessHandler successHandler;
	private DetailsService detailsService;
	private AuthManager authManager;

	public Config(AuthenticationSuccessHandler successHandler, DetailsService detailsService,
			AuthManager authManager) {
		this.successHandler = successHandler;
		this.detailsService = detailsService;
		this.authManager = authManager;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.
		sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilter(createAuthFilter())
		.addFilter(new TokenAuthorizationFilter(authenticationManager(), detailsService, secret))
		.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
	}

	private AuthenticationFilter createAuthFilter() throws Exception {
		var filter = new AuthenticationFilter();
		filter.setAuthenticationSuccessHandler(successHandler);
		filter.setAuthenticationManager(authManager);
		return filter;
	}

	@Bean
	public BCryptPasswordEncoder getBcryptPasswordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

}
