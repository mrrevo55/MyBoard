package com.revo.myboard.section;

import java.util.List;

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

import com.revo.myboard.section.dto.NameDTO;
import com.revo.myboard.section.dto.SectionDTO;
import com.revo.myboard.security.annotation.ForAdmin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 *  Created By Revo
 */

@RestController
@RequestMapping("/section")
@Validated
@AllArgsConstructor
@Slf4j
public class SectionController {

	private final SectionService serverService;

	/*
	 * PUBLIC
	 */

	@GetMapping("/{id}")
	public ResponseEntity<SectionDTO> getSectionById(@PathVariable long id, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" getting section with id: "+ id);
		var section = SectionDTO.mapFromSection(serverService.getSectionById(id));
		log.info("User with ip: "+request.getRemoteAddr()+" success got section with id: "+ id);
		return ResponseEntity.ok(section);
	}

	@GetMapping("/all")
	public ResponseEntity<List<SectionDTO>> getAllSections(HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" getting all sections");
		var sections = serverService.getAllSections().stream().map(server -> SectionDTO.mapFromSection(server)).toList();
		log.info("User with ip: "+request.getRemoteAddr()+" success got all sections");
		return ResponseEntity.ok(sections);
	}

	/*
	 * ADMIN
	 */

	@PostMapping("/create")
	@ForAdmin
	@ResponseStatus(HttpStatus.CREATED)
	public void createSection(@RequestBody @Valid NameDTO createDTO, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" creating sections with details: "+createDTO.toString());
		serverService.createSection(createDTO.getName());
		log.info("User with ip: "+request.getRemoteAddr()+" success created sections with details: "+createDTO.toString());
	}

	@DeleteMapping("/delete/{id}")
	@ForAdmin
	public void deleteSectionByName(@PathVariable long id, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" deleting section with id: "+id);
		serverService.deleteSectionById(id);
		log.info("User with ip: "+request.getRemoteAddr()+" success deleted section with id: "+id);
	}

	@PatchMapping("/rename/{id}")
	@ForAdmin
	public void renameSectionByName(@PathVariable long id, @RequestBody @Valid NameDTO nameDTO, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" renaming section with id: "+id+" with details: "+nameDTO.toString());
		serverService.renameSectionById(id, nameDTO.getName());
		log.info("User with ip: "+request.getRemoteAddr()+" success renamed section with id: "+id+" with details: "+nameDTO.toString());
	}

}
