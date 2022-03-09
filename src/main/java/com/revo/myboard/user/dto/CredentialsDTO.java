package com.revo.myboard.user.dto;

import com.revo.myboard.user.User;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * Created By Revo
 */

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class CredentialsDTO {

	private String login;
	private String authority;
	
	public static CredentialsDTO mapFromUser(User user) {
		return CredentialsDTO.builder().login(user.getLogin()).authority(user.getGroup().getAuthority().toString()).build();
	}
	
}
