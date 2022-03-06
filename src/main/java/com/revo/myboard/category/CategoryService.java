package com.revo.myboard.category;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.section.SectionService;

/*
 * Created By Revo
 */

@Service
@Transactional
public class CategoryService {
	
	private CategoryRepository repository;
	private SectionService serverService;
	
	public CategoryService(CategoryRepository repository, SectionService serverService) {
		this.repository = repository;
		this.serverService = serverService;
	}

	public void createCategory(String name, long section_id) {
		if(repository.existsByName(name)) {
			throw new IllegalArgumentException(name);
		}
		var category = new Category();
		category.setName(name);
		category.setSection(serverService.getSectionById(section_id));
		repository.save(category);
	}
	
	public void deleteCategoryById(long id) {
		repository.delete(getCategoryById(id));

	}

	public Category getCategoryById(long id) {
		return repository.findById(id).orElseThrow(() -> new NullPointerException(String.valueOf(id)));
	}
	
	public void renameCategoryById(long id, String new_name) {
		if(repository.existsByName(new_name)) {
			throw new IllegalArgumentException(new_name);
		}
		getCategoryById(id).setName(new_name);
	}
	
}