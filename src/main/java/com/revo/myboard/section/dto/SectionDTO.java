package com.revo.myboard.section.dto;

import java.util.List;

import com.revo.myboard.category.dto.ShortCategoryDTO;
import com.revo.myboard.section.Section;

/*
 * Created By Revo
 */

public class SectionDTO {
	
	private long id;
	private String name;
	private List<ShortCategoryDTO> categories;
	
	public String getName() {
		return name;
	}
	public List<ShortCategoryDTO> getCategories() {
		return categories;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCategories(List<ShortCategoryDTO> categories) {
		this.categories = categories;
	}	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public SectionDTO mapFromSection(Section section) {
		this.setCategories(section.getCategories().stream().map(category -> new ShortCategoryDTO().mapFromCategory(category)).toList());
		this.setName(section.getName());
		this.setId(section.getId());
		return this;
	}
}
