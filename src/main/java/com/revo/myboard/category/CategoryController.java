package com.revo.myboard.category;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 *  Created By Revo
 */

@RestController
@RequestMapping("/category")
@Validated
@AllArgsConstructor
@Slf4j
public class CategoryController {

	private final CategoryService categoryService;

	/*
	 * PUBLIC
	 */

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable long id, HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " getting category of id: " + id);
		var category = CategoryDTO.mapFromCategory(categoryService.getCategoryById(id));
		log.info("User with ip: " + request.getRemoteAddr() + " success got category of id: " + id);
		return ResponseEntity.ok(category);
	}

	/*
	 * ADMIN
	 */

	@PostMapping("/create")
	@ForAdmin
	@ResponseStatus(HttpStatus.CREATED)
	public void createCategory(@RequestBody @Valid CreateDTO createDTO, HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " creating new category with details: "
				+ createDTO.toString());
		categoryService.createCategory(createDTO.getName(), createDTO.getSection());
		log.info("User with ip: " + request.getRemoteAddr() + " success created category with details!"
				+ createDTO.toString());
	}

	@DeleteMapping("/delete/{id}")
	@ForAdmin
	public void deleteCategoryByName(@PathVariable long id, HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " deleting category with id: " + id);
		categoryService.deleteCategoryById(id);
		log.info("User with ip: " + request.getRemoteAddr() + " success deleted category with id: " + id);
	}

	@PatchMapping("/rename/{id}")
	@ForAdmin
	public void renameCategoryByName(@PathVariable long id, @RequestBody @Valid NameDTO renameDTO,
			HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " renaming category with id: " + id + " with details: "
				+ renameDTO.toString());
		categoryService.renameCategoryById(id, renameDTO.getNewName());
		log.info("User with ip: " + request.getRemoteAddr() + " success renamed category with id: " + id + " with details: "
				+ renameDTO.toString());
	}

}
