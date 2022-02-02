package com.revo.myboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.revo.myboard.annotations.ForAdmin;
import com.revo.myboard.annotations.ForUser;
import com.revo.myboard.entities.Category;
import com.revo.myboard.services.CategoryService;
import com.revo.myboard.views.CategoryGetView;

/*
 *  category Controller
 *  
 *  Created By Revo
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

	/*
	 * Data
	 */

	@Autowired
	private CategoryService categoryService;
	
	// USER

	@GetMapping
	@ForUser
	@JsonView(CategoryGetView.class)
	public Category get(@RequestParam String name) {
		return categoryService.get(name);
	}

	// ADMIN

	/*
	 * Create
	 */

	@GetMapping("/create")
	@ForAdmin
	public boolean create(@RequestParam String name, @RequestParam String server) {
		return categoryService.create(name, server);
	}

	/*
	 * Delete
	 */

	@GetMapping("/delete")
	@ForAdmin
	public boolean delete(@RequestParam String name) {
		return categoryService.delete(name);
	}

	/*
	 * Rename
	 */

	@GetMapping("/rename")
	@ForAdmin
	public boolean rename(@RequestParam String name, @RequestParam String new_name) {
		return categoryService.rename(name, new_name);
	}
}
