package com.revo.myboard.group;

/*
 * Created By Revo
 */

public class GroupDTO {

	private long id;
	private String name;
	private String authority;
	
	public String getName() {
		return name;
	}
	public String getAuthority() {
		return authority;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public GroupDTO mapFromGroup(Group group){
		this.setId(group.getId());
		this.setAuthority(group.getAuthority().toString());
		this.setName(group.getName());
		return this;
	}
	
}
