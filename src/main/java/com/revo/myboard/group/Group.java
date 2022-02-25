package com.revo.myboard.group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/*
 * Created By Revo
 */

@Entity
@Table(name="user_group")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotEmpty
	@NotBlank
	@Column(unique=true)
	private String name;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Authority authority = Authority.USER;
	
	public String getName() {
		return name;
	}
	public Authority getAuthority() {
		return authority;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", authority=" + authority + "]";
	}

}
