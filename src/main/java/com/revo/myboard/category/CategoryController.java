package com.revo.myboard.category;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.category.dto.CategoryDTO;
import com.revo.myboard.category.dto.CreateDTO;
import com.revo.myboard.category.dto.NameDTO;
import com.revo.myboard.security.annotation.ForAdmin;

/*
 *  Created By Revo
 */

@RestController
@RequestMapping("/category")
@Validated
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
	public void createCategory(@RequestBody @Valid CreateDTO createDTO) {
		categoryService.createCategory(createDTO.getName(), createDTO.getSection());
	}

	@DeleteMapping("/delete/{id}")
	@ForAdmin
	public void deleteCategoryByName(@PathVariable long id) {
		categoryService.deleteCategoryById(id);
	}

	@PatchMapping("/rename/{id}")
	public void renameCategoryByName(@PathVariable long id, @RequestBody @Valid NameDTO renameDTO) {
		categoryService.renameCategoryById(id, renameDTO.getNewName());
	}
	
}
