package com.revo.myboard.category.dto;

import com.revo.myboard.category.Category;

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
public class ShortCategoryDTO {
	
	private long id;
	private String name;
	
	public static ShortCategoryDTO mapFromCategory(Category category) {
		return ShortCategoryDTO.builder().id(category.getId()).name(category.getName()).build();
	}

}
