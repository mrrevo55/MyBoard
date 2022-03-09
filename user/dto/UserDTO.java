package com.revo.myboard.user.dto;

import java.util.List;

import com.revo.myboard.post.dto.ShortPostDTO;
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
public class UserDTO {

	private String login;
	private String group;
	private String email;
	private DataDTO data;
	private List<ShortPostDTO> posts;
	private boolean blocked;

	public static UserDTO mapFromUser(User user) {
		return UserDTO.builder().data(DataDTO.mapFromData(user.getData())).email(user.getEmail())
				.group(user.getGroup().getName()).login(user.getLogin())
				.posts(user.getPosts().stream().map(post -> ShortPostDTO.mapFromPost(post)).toList())
				.blocked(user.isBlocked()).build();
	}

}
