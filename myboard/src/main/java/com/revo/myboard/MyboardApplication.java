package com.revo.myboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/*
 * Main Spring Boot Application Class
 */

@Configuration
@EnableAutoConfiguration
@PropertySource({"classpath:jwt.properties","classpath:smtp.properties"})
@ComponentScan("come.revo.myboard.configuration")
@SpringBootApplication
public class MyboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyboardApplication.class, args);
	}
	
}
