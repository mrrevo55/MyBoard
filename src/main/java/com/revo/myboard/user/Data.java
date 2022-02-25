package com.revo.myboard.user;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
 * Created By Revo
 */

@Embeddable
public class Data {
	
	@NotEmpty
	@NotBlank
	private String description;
	@Size(min=5,max=90)
	private int age;
	@NotEmpty
	@NotBlank
	private String city;
	@NotEmpty
	@NotBlank
	private String page;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Sex sex;
	
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}

}
