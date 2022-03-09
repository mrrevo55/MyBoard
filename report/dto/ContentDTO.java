package com.revo.myboard.report.dto;

import javax.validation.constraints.NotEmpty;

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
public class ContentDTO {

	@NotEmpty
	private String content;
	
}
