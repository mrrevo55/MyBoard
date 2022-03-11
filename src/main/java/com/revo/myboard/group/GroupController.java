package com.revo.myboard.group;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.revo.myboard.group.dto.AuthortiyDTO;
import com.revo.myboard.group.dto.CreateDTO;
import com.revo.myboard.group.dto.GroupDTO;
import com.revo.myboard.group.dto.NameDTO;
import com.revo.myboard.security.annotation.ForAdmin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * Created By Revo
 */

@RestController
@RequestMapping("/groups")
@ForAdmin
@Validated
@AllArgsConstructor
@Slf4j
public class GroupController {

	private final GroupService groupService;

	/*
	 * ADMIN
	 */

	@GetMapping("/authorities")
	public ResponseEntity<List<String>> getAuthorityList(HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " getting authorities list");
		var authorities = groupService.getAuthorityList();
		log.info("User with ip: " + request.getRemoteAddr() + " success got authorities list");
		return ResponseEntity.ok(authorities);
	}

	@GetMapping("")
	public ResponseEntity<List<GroupDTO>> getAllGroups(HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + "getting all groups");
		var groups = groupService.getAllGroups().stream().map(group -> GroupDTO.mapFromGroup(group)).toList();
		log.info("User with ip: " + request.getRemoteAddr() + "success got all groups");
		return ResponseEntity.ok(groups);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GroupDTO> getGroupById(@PathVariable long id, HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " getting group with id: " + id);
		var group = GroupDTO.mapFromGroup(groupService.getGroupById(id));
		log.info("User with ip: " + request.getRemoteAddr() + " success got group with id: " + id);
		return ResponseEntity.ok(group);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public void createGroup(@RequestBody @Valid CreateDTO createDTO, HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " creating group with details: " + createDTO.toString());
		groupService.createGroup(createDTO.getName(), createDTO.getAuthority());
		log.info("User with ip: " + request.getRemoteAddr() + " success created group with details: "
				+ createDTO.toString());
	}

	@DeleteMapping("{id}")
	public void deleteGroupById(@PathVariable long id, HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " deleting group with id: " + id);
		groupService.deleteGroupById(id);
		log.info("User with ip: " + request.getRemoteAddr() + " success deleted group with id: " + id);
	}

	@PatchMapping("{id}")
	public void renameGroupById(@PathVariable long id, @RequestBody @Valid NameDTO editNameDTO,
			HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " renaming group with id: " + id + " with details: "
				+ editNameDTO.toString());
		groupService.renameGroupById(id, editNameDTO.getNewName());
		log.info("User with ip: " + request.getRemoteAddr() + " success renamed group with id: " + id
				+ " with details: " + editNameDTO.toString());
	}

  @PutMapping("{id}")
	public void changeGroupAuthority(@PathVariable long id, @RequestBody @Valid AuthortiyDTO editAuthorityDTO,
			HttpServletRequest request) {
		log.info("User with ip: " + request.getRemoteAddr() + " changing authority of group with id: " + id
				+ " with details: " + editAuthorityDTO.toString());
		groupService.changeGroupAuthority(id, editAuthorityDTO.getNewAuthority());
		log.info("User with ip: " + request.getRemoteAddr() + " success changed authority of group with id: " + id
				+ " with details: " + editAuthorityDTO.toString());
	}

}
