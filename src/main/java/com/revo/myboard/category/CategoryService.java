package com.revo.myboard.category;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.exception.ArgumentInUseException;
import com.revo.myboard.exception.ObjectNotExistsException;
import com.revo.myboard.section.SectionService;

import lombok.AllArgsConstructor;

/*
 * Created By Revo
 */

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {

	private final CategoryRepository repository;
	private final SectionService sectionService;

	public void createCategory(String name, long section_id) {
		if (repository.existsByName(name)) {
			throw new ArgumentInUseException(name);
		}
		repository.save(Category.builder().name(name).section(sectionService.getSectionById(section_id)).build());
	}

	public void deleteCategoryById(long id) {
		repository.delete(getCategoryById(id));

	}

	public Category getCategoryById(long id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotExistsException(String.valueOf(id)));
	}

	public void renameCategoryById(long id, String new_name) {
		if (repository.existsByName(new_name)) {
			throw new ArgumentInUseException(new_name);
		}
		getCategoryById(id).setName(new_name);
	}

}