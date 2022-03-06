package com.revo.myboard.group;

import java.util.List;

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

import com.revo.myboard.group.dto.AuthortiyDTO;
import com.revo.myboard.group.dto.CreateDTO;
import com.revo.myboard.group.dto.GroupDTO;
import com.revo.myboard.group.dto.NameDTO;
import com.revo.myboard.security.annotation.ForAdmin;

/*
 * Created By Revo
 */

@RestController
@RequestMapping("/group")
@ForAdmin
@Validated
public class GroupController {

	private GroupService groupService;
	
	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}
	
	/*
	 * ADMIN
	 */ 
	 
	@GetMapping("/authorities")
	public List<String> getAuthorityList(){
		return groupService.getAuthorityList();
	}   
	
	@GetMapping("/all")
	public List<GroupDTO> getAllGroups(){
		return groupService.getAllGroups().stream().map(group -> new GroupDTO().mapFromGroup(group)).toList();
	}
	
	@GetMapping("/{id}")
	public GroupDTO getGroupById(@PathVariable long id) {
		return new GroupDTO().mapFromGroup(groupService.getGroupById(id));
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public void createGroup(@RequestBody @Valid CreateDTO createDTO) {
		groupService.createGroup(createDTO.getName(), createDTO.getAuthority());
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteGroupById(@PathVariable long id) {
		groupService.deleteGroupById(id);
	}
	
	@PatchMapping("/rename/{id}")
	public void renameGroupById(@PathVariable long id, @RequestBody @Valid NameDTO editNameDTO) {
		groupService.renameGroupById(id, editNameDTO.getNewName());
	}
	
	@PatchMapping("/change/{id}")
	public void changeGroupAuthority(@PathVariable long id, @RequestBody @Valid AuthortiyDTO editAuthorityDTO) {
		groupService.changeGroupAuthority(id, editAuthorityDTO.getNewAuthority());
	}
	
}
