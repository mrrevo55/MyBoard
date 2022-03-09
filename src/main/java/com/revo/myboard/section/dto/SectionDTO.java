package com.revo.myboard.section.dto;

import java.util.List;

import com.revo.myboard.category.dto.ShortCategoryDTO;
import com.revo.myboard.section.Section;

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
public class SectionDTO {

	private long id;
	private String name;
	private List<ShortCategoryDTO> categories;

	public static SectionDTO mapFromSection(Section section) {
		return SectionDTO.builder().id(section.getId()).name(section.getName())
				.categories(section.getCategories().stream()
						.map(category -> ShortCategoryDTO.mapFromCategory(category)).toList())
				.build();
	}
	
}
