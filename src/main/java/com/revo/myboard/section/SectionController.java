package com.revo.myboard.section;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.section.dto.NameDTO;
import com.revo.myboard.section.dto.SectionDTO;
import com.revo.myboard.security.annotation.ForAdmin;

/*
 *  Created By Revo
 */

@RestController
@RequestMapping("/section")
@Validated
public class SectionController {
	
	private SectionService serverService;
	
	public SectionController(SectionService serverService) {
		super();
		this.serverService = serverService;
	}

	/*
	 * PUBLIC
	 */
	
	@GetMapping("/{id}")
	public SectionDTO getSectionById(@PathVariable long id) {
		return new SectionDTO().mapFromSection(serverService.getSectionById(id));
	}
	
	@GetMapping("/all")
	public List<SectionDTO> getAllSections(){
		return serverService.getAllSections().stream().map(server -> new SectionDTO().mapFromSection(server)).toList();
	}
	
	/*
	 * ADMIN
	 */
	
	@PostMapping("/create")
	@ForAdmin
	@ResponseStatus(HttpStatus.CREATED)
	public void createSection(@RequestBody @Valid NameDTO createDTO) {
		serverService.createSection(createDTO.getName());
	}
	
	@DeleteMapping("/delete/{id}")
	@ForAdmin
	public void deleteSectionByName(@PathVariable long id) {
		serverService.deleteSectionById(id);
	}
	
	@PatchMapping("/rename/{id}")
	@ForAdmin
	public void renameSectionByName(@PathVariable long id, @RequestBody @Valid NameDTO nameDTO) {
		serverService.renameSectionById(id, nameDTO.getName());
	}
	
}
