package com.revo.myboard.section.dto;

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
public class NameDTO {

	@Size(min = 4, max = 20)
	private String name;
	
}
