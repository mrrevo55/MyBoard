package com.revo.myboard.group.dto;

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
public class AuthortiyDTO {

	@Size(min = 4, max = 9)
	private String newAuthority;

}
