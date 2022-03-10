package com.revo.myboard.user.dto;

import java.util.Date;

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
public class SearchDTO {

	private String login;
	private Date lastActivity;

	public static SearchDTO mapFromUser(User user) {
		return SearchDTO.builder().login(user.getLogin()).lastActivity(user.getLastActivityDate()).build();
	}
	
}
