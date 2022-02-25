package com.revo.myboard.group;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.security.annotation.ForAdmin;

/*
 * Created By Revo
 */

@RestController
@RequestMapping("/group")
@ForAdmin
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
	  
	@GetMapping("/names")
	public List<String> getNamesList(){
		return groupService.getNamesList();
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
	public void createGroup(@RequestBody CreateDTO groupDTO) {
		groupService.createGroup(groupDTO.getName(), groupDTO.getAuthority());
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteGroupById(@PathVariable long id) {
		groupService.deleteGroupById(id);
	}
	
	@PutMapping("/rename/{id}")
	public void renameGroupById(@PathVariable long id, @RequestParam String new_name) {
		groupService.renameGroupById(id, new_name);
	}
	
	@PutMapping("/change/{id}")
	public void changeGroupAuthority(@PathVariable long id, @RequestParam String authority) {
		groupService.changeGroupAuthority(id, authority);
	}
	
}
