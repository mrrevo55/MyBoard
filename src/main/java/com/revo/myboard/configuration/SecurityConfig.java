package com.revo.myboard.configuration;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.revo.myboard.filters.AuthenticationFilter;
import com.revo.myboard.filters.JsonWebTokenAuthorizationFilter;
import com.revo.myboard.handlers.JsonAuthenticationSuccessHandler;
import com.revo.myboard.managers.AuthManager;
import com.revo.myboard.services.DetailsService;

/*
 * Security Config Class
 * 
 * Created By Revo
 * 
 */

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * Data
	 */

	@Value("${spring.security.jwt.secret}")
	private String secret;
	@Autowired
	private JsonAuthenticationSuccessHandler successHandler;
	@Autowired
	private DetailsService detailsService;
	@Autowired
	private AuthManager authManager;

	/*
	 * Security Rules
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.
		sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilter(authFilter())
		.addFilter(new JsonWebTokenAuthorizationFilter(authenticationManager(), detailsService, secret))
		.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
	}

	/*
	 * Auth Filter
	 */

	public AuthenticationFilter authFilter() throws Exception {
		AuthenticationFilter filter = new AuthenticationFilter();
		filter.setAuthenticationSuccessHandler(successHandler);
		filter.setAuthenticationManager(authManager);
		return filter;
	}

	/*
	 * Password Encoder
	 */

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder(12);
	}

}
