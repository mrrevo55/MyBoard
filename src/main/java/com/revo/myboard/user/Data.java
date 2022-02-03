package com.revo.myboard.user;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

/*
 * Created By Revo
 */

@Embeddable
public class Data {

	@JsonView(UserProfileView.class)
	private String description;
	@Size(min=5,max=90)
	@JsonView(UserProfileView.class)
	private int age;
	@NotNull
	@Enumerated(EnumType.STRING)
	@JsonView(UserProfileView.class)
	private Country country = Country.POLAND;
	@JsonView(UserProfileView.class)
	private String page;
	@NotNull
	@Enumerated(EnumType.STRING)
	@JsonView(UserProfileView.class)
	private Sex sex = Sex.MALE;
	
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
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
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
