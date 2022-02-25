package com.revo.myboard.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.security.annotation.ForAdmin;

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
	 * PUBLIC
	 */
	
	@GetMapping("/{id}")
	public CategoryDTO getCategoryById(@PathVariable long id) {
		return new CategoryDTO().mapFromCategory(categoryService.getCategoryById(id));
	}

	/*
	 *  ADMIN
	 */

	@PostMapping("/create")
	@ForAdmin
	@ResponseStatus(HttpStatus.CREATED)
	public void createCategory(@RequestBody CreateDTO categoryDTO) {
		categoryService.createCategory(categoryDTO.getName(), categoryDTO.getSection());
	}

	@DeleteMapping("/delete/{id}")
	@ForAdmin
	public void deleteCategoryByName(@PathVariable long id) {
		categoryService.deleteCategoryById(id);
	}

	@PutMapping("/rename/{id}")
	@ForAdmin
	public void renameCategoryByName(@PathVariable long id, @RequestParam String new_name) {
		categoryService.renameCategoryById(id, new_name);
	}
	
}
