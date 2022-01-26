package com.revo.myboard.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
 * Data Embedded For User Table Class
 * 
 * 
 * Created By Revo
 */

@Embeddable
public class Data {
	
	/*
	 * Data
	 */

	private String description;
	@Size(min=5,max=90)
	private int age;
	@NotEmpty
	@NotBlank
	@Column(columnDefinition = "varchar(255) default 'POLAND'")
	private String country;
	private String page;
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(255) default 'MALE'")
	private Sex sex;
	
	/*
	 * Getters & Setters
	 */
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}

}
