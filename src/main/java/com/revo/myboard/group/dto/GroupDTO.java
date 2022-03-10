package com.revo.myboard.group.dto;

import com.revo.myboard.group.Group;

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
public class GroupDTO {

	private long id;
	private String name;
	private String authority;

	public static GroupDTO mapFromGroup(Group group) {
		return GroupDTO.builder().id(group.getId()).authority(group.getAuthority().toString()).name(group.getName())
				.build();
	}

}
