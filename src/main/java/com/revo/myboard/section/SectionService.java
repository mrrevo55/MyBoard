package com.revo.myboard.section;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * Created By Revo
 */

@Service
public class SectionService {

	private SectionRepository repository;
	
	public SectionService(SectionRepository repository) {
		this.repository = repository;
	}

	public void createSection(String name) {
		if(repository.existsByName(name)) {
			throw new IllegalArgumentException(name);
		}
		var section = new Section();
		section.setName(name);
		repository.save(section);
	}
	
	public void deleteSectionById(long id) {
		repository.delete(getSectionById(id));
	}

	public Section getSectionById(long id) {
		return repository.findById(id).orElseThrow(() -> new NullPointerException(String.valueOf(id)));
	}
	
	@Transactional
	public void renameSectionById(long id, String new_name) {
		if(repository.existsByName(new_name)) {
			throw new IllegalArgumentException(new_name);
		}
		getSectionById(id).setName(new_name);
	}

	public List<Section> getAllSections() {
		return repository.findAll();
	}
}
