package com.revo.myboard.group;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.user.User;
import com.revo.myboard.user.UserRepository;

/*
 * Created By Revo
 */

@Service
@Transactional
public class GroupService {

	private GroupRepository repository;
	private UserRepository userRepository;
	
	public GroupService(GroupRepository repository, UserRepository userRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
	}
	
	public List<String> getAuthorityList() {
		return Arrays.asList(Authority.values()).stream().map(group -> group.toString()).toList();
	}
	
	public List<String> getNamesList() {
		return repository.findAll().stream().map(group -> group.getName()).toList();
	}

	public Group getDefaultGroup() {
		if(repository.count() == 0 || repository.findByName("UŻYTKOWNIK").isEmpty()) {
			var group = new Group();
			group.setName("UŻYTKOWNIK");
			repository.save(group);
		}
		return repository.findByName("UŻYTKOWNIK").get();
	}

	public Group getGroupById(long id) {
		return repository.findById(id).orElseThrow(() -> new NullPointerException(String.valueOf(id)));
	}
	
	public void createGroup(String name, String authority) {
		if(repository.findByName(name).isPresent()) {
			throw new IllegalArgumentException(name);
		}
		var group = new Group();
		group.setName(name);
		group.setAuthority(Authority.valueOf(authority));
		repository.save(group);
	}
	
	public void deleteGroupById(long id) {
		repository.delete(getGroupById(id));
		for(User user : userRepository.findAll()) {
			if(user.getGroup().getId() == id) {
				user.setGroup(getDefaultGroup());
			}
		}
	}
	
	public void renameGroupById(long id, String new_name) {
		if(repository.existsByName(new_name)) {
			throw new IllegalArgumentException(new_name);
		}
		getGroupById(id).setName(new_name);
	}

	public List<Group> getAllGroups() {
		return repository.findAll();
	}
	
	public void changeGroupAuthority(long id, String authority) {
		repository.findById(id).get().setAuthority(Authority.valueOf(authority));
	}
	
}
