package com.revo.myboard.user.dto;

import java.util.List;

import com.revo.myboard.post.dto.ShortPostDTO;
import com.revo.myboard.user.User;

/*
 * Created By Revo
 */

public class UserDTO {

	private String login;
	private String group;
	private String email;
	private DataDTO data;
	private List<ShortPostDTO> posts;
	private boolean blocked;

	public String getLogin() {
		return login;
	}

	public String getGroup() {
		return group;
	}

	public String getEmail() {
		return email;
	}

	public DataDTO getData() {
		return data;
	}

	public List<ShortPostDTO> getPosts() {
		return posts;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setData(DataDTO data) {
		this.data = data;
	}

	public void setPosts(List<ShortPostDTO> posts) {
		this.posts = posts;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public UserDTO mapFromUser(User user) {
		this.setData(new DataDTO().mapFromData(user.getData()));
		this.setEmail(user.getEmail());
		this.setGroup(user.getGroup().getName());
		this.setLogin(user.getLogin());
		this.setPosts(user.getPosts().stream().map(post -> new ShortPostDTO().mapFromPost(post)).toList());
		this.setBlocked(user.isBlocked());
		return this;
	}
	
	

}
