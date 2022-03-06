package com.revo.myboard.category.dto;

import java.util.List;

import com.revo.myboard.category.Category;
import com.revo.myboard.post.dto.ShortPostDTO;

/*
 * Created By Revo
 */

public final class CategoryDTO {
	
	private long id;
	private String name;
	private List<ShortPostDTO> posts;
	private long section;
	
	public String getName() {
		return name;
	}

	public List<ShortPostDTO> getPosts() {
		return posts;
	}

	public long getSection() {
		return section;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPosts(List<ShortPostDTO> posts) {
		this.posts = posts;
	}

	public void setSection(long server) {
		this.section = server;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CategoryDTO mapFromCategory(Category category) {
		this.setId(category.getId());
		this.setName(category.getName());
		this.setPosts(category.getPosts().stream().map(post -> new ShortPostDTO().mapFromPost(post)).toList());
		this.setSection(category.getSection().getId());
		return this;
	}
	
}
