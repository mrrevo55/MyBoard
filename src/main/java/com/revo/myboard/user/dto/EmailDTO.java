package com.revo.myboard.user.dto;

import javax.validation.constraints.Email;

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
public class EmailDTO {

	@Email
	private String email;
	
}
