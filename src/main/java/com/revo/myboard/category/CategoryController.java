package com.revo.myboard.category;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.revo.myboard.security.annotation.ForAdmin;
import com.revo.myboard.security.annotation.ForUser;

/*
 *  Created By Revo
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	
	/*
	 *  USER
	 */

	@GetMapping("/{name}")
	@ForUser
	@JsonView(CategoryGetView.class)
	public Category getCategoryByName(@PathVariable String name) {
		return categoryService.getCategoryByName(name);
	}

	/*
	 *  ADMIN
	 */

	@PostMapping("/create")
	@ForAdmin
	public boolean createCategory(@RequestParam String name, @RequestParam String server) {
		return categoryService.createCategory(name, server);
	}

	@DeleteMapping("/delete/{name}")
	@ForAdmin
	public boolean deleteCategoryByName(@PathVariable String name) {
		return categoryService.deleteCategoryByName(name);
	}

	@PutMapping("/rename/{name}")
	@ForAdmin
	public boolean renameCategoryByName(@PathVariable String name, @RequestParam String new_name) {
		return categoryService.renameCategoryByName(name, new_name);
	}
	
}
