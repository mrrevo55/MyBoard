package com.revo.myboard.post.dto;

import javax.validation.constraints.NotEmpty;
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
public class EditDTO {

	@Size(min = 4, max = 30)
	private String title;
	@NotEmpty
	private String content;
	
}
