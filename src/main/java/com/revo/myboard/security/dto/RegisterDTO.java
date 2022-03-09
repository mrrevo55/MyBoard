package com.revo.myboard.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * Created By Revo
 */

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RegisterDTO{

	@Size(min = 4, max = 20)
	private String login;
	@Size(min = 4, max = 20)
	private String password;
	@Email
	private String email;
	
}
