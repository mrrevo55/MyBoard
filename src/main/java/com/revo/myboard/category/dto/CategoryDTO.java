package com.revo.myboard.category.dto;

import java.util.List;

import com.revo.myboard.category.Category;
import com.revo.myboard.post.dto.ShortPostDTO;

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
public final class CategoryDTO {

	private long id;
	private String name;
	private List<ShortPostDTO> posts;
	private long section;

	public static CategoryDTO mapFromCategory(Category category) {
		return CategoryDTO.builder().id(category.getId()).name(category.getName())
				.posts(category.getPosts().stream().map(post -> ShortPostDTO.mapFromPost(post)).toList())
				.section(category.getSection().getId()).build();
	}

}
