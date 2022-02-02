package com.revo.myboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.entities.Category;
import com.revo.myboard.entities.Server;
import com.revo.myboard.repositories.CategoryRepository;

import java.util.NoSuchElementException;

/*
 * Category Service Class
 * 
 * Created By Revo
 */

@Service
public class CategoryService {
	
	/*
	 * Data
	 */

	@Autowired
	private CategoryRepository repo;
	@Autowired
	private ServerService serverService;
	
	/*
	 * Create New Category
	 */
	
	public boolean create(String name, String server) {
		Server s = serverService.get(server);
		if(s== null) return false;
		if(repo.findByName(name) != null) return false;
		Category c = new Category();
		c.setName(name);
		c.setServer(s);
		repo.save(c);
		return true;
	}
	
	/*
	 * Delete Category By Name
	 */
	
	public boolean delete(String name) {
		Category c = get(name);
		if(c == null) return false;
		repo.delete(c);
		return true;
	}
	
	/*
	 * Get Category By Name
	 */

	public Category get(String name) {
		try{
			return repo.findByName(name).get();
		} catch (NoSuchElementException e) { return null; }
	}
	
	/*
	 * Change Name Of Category
	 */
	
	@Transactional
	public boolean rename(String name, String new_name) {
		if(get(new_name) != null) return false;
		get(name).setName(new_name);
		return true;
	}
}