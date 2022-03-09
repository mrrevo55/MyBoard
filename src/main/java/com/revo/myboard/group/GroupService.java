package com.revo.myboard.group;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.exception.ArgumentInUseException;
import com.revo.myboard.exception.ObjectNotExistsException;
import com.revo.myboard.user.UserRepository;

import lombok.AllArgsConstructor;

/*
 * Created By Revo
 */

@Service
@Transactional
@AllArgsConstructor
public class GroupService {

	private final GroupRepository repository;
	private final UserRepository userRepository;
	
	public List<String> getAuthorityList() {
		return Arrays.asList(Authority.values()).stream().map(group -> group.toString()).toList();
	}
	
	public List<String> getNamesList() {
		return repository.findAll().stream().map(group -> group.getName()).toList();
	}

	public Group getDefaultGroup() {
		if(repository.count() == 0 || repository.findByName("UŻYTKOWNIK").isEmpty()) {
			repository.save(Group.builder().name("UŻYTKOWNIK").authority(Authority.USER).build());
		}
		return repository.findByName("UŻYTKOWNIK").get();
	}

	public Group getGroupById(long id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotExistsException(String.valueOf(id)));
	}
	
	public void createGroup(String name, String authority) {
		if(repository.findByName(name).isPresent()) {
			throw new ArgumentInUseException(name);
		}
		repository.save(Group.builder().name(name).authority(Authority.valueOf(authority)).build());
	}
	
	public void deleteGroupById(long id) {
		repository.delete(getGroupById(id));
		userRepository.findAll().stream().filter(user -> user.getGroup().getId() == id).forEach(user -> user.setGroup(getDefaultGroup()));
	}
	
	public void renameGroupById(long id, String new_name) {
		if(repository.existsByName(new_name)) {	
			throw new ArgumentInUseException(new_name);
		}
		getGroupById(id).setName(new_name);
	}

	public List<Group> getAllGroups() {
		return repository.findAll();
	}
	
	public void changeGroupAuthority(long id, String authority) {
		repository.findById(id).orElseThrow(() -> new ObjectNotExistsException(String.valueOf(id))).setAuthority(Authority.valueOf(authority));
	}
	
}
