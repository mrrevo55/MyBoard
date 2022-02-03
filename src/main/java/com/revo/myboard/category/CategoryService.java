package com.revo.myboard.category;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.server.ServerService;
import com.revo.myboard.util.ObjectNotExistsException;

/*
 * Created By Revo
 */

@Service
@Transactional
public class CategoryService {
	
	private CategoryRepository repo;
	private ServerService serverService;
	
	public CategoryService(CategoryRepository repo, ServerService serverService) {
		this.repo = repo;
		this.serverService = serverService;
	}

	public boolean createCategory(String name, String server_name) {
		var server = serverService.getServerByName(server_name);
		if(server== null) return false;
		if(repo.existsByName(name)) {
			return false;
		}
		var category = new Category();
		category.setName(name);
		category.setServer(server);
		repo.save(category);
		return true;
	}
	
	public boolean deleteCategoryByName(String name) {
		var category = getCategoryByName(name);
		if(category== null) {
			return false;
		}
		repo.delete(category);
		return true;
	}

	public Category getCategoryByName(String name) {
		return repo.findByName(name).orElseThrow(() -> new ObjectNotExistsException(name));
	}
	
	public boolean renameCategoryByName(String name, String new_name) {
		var category = getCategoryByName(new_name);
		if(category == null) {
			return false;
		}
		category.setName(new_name);
		return true;
	}
	
}