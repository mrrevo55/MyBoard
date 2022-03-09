package com.revo.myboard.section;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.exception.ArgumentInUseException;
import com.revo.myboard.exception.ObjectNotExistsException;

import lombok.AllArgsConstructor;

/*
 * Created By Revo
 */

@Service
@AllArgsConstructor
public class SectionService {

	private final SectionRepository repository;

	public void createSection(String name) {
		if(repository.existsByName(name)) {
			throw new ArgumentInUseException(name);
		}
		repository.save(Section.builder().name(name).build());
	}
	
	public void deleteSectionById(long id) {
		repository.delete(getSectionById(id));
	}

	public Section getSectionById(long id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotExistsException(String.valueOf(id)));
	}
	
	@Transactional
	public void renameSectionById(long id, String new_name) {
		if(repository.existsByName(new_name)) {
			throw new ArgumentInUseException(new_name);
		}
		getSectionById(id).setName(new_name);
	}

	public List<Section> getAllSections() {
		return repository.findAll();
	}
}
