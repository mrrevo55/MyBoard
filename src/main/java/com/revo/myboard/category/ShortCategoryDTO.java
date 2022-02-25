package com.revo.myboard.category;

/*
 * Created By Revo
 */

public class ShortCategoryDTO {
	
	private long id;
	private String name;
	
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ShortCategoryDTO mapFromCategory(Category category) {
		this.setId(category.getId());
		this.setName(category.getName());
		return this;
	}

}
