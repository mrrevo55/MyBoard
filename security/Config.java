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
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * Created By Revo
 */

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class Config extends WebSecurityConfigurerAdapter {

	@Value("${spring.security.jwt.secret}")
	private String secret;
	private final AuthenticationFilter authenticationFilter;
	private final DetailsService detailsService;

	@Override
	protected void configure(HttpSecurity http){
		try {
			http.cors();
			http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*"));
			http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Expose-Headers", "*"));
			http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "*"));
			http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "*"));
			http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Request-Headers", "*"));
			http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "*"));
			http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Max-Age", "3600"));
			http.csrf().disable();
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.addFilter(authenticationFilter)
					.addFilter(new TokenAuthorizationFilter(authenticationManager(), detailsService, secret))
					.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
		} catch(Exception exception) {
			log.warn("Error while configurin HttpSecurity class, exception message: "+exception.getMessage());
		}
	}

	@Bean
	public BCryptPasswordEncoder getBcryptPasswordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

}
