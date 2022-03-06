package com.revo.myboard.user.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.revo.myboard.user.Data;

/*
 * Created By Revo
 */

public class DataDTO {

	@NotEmpty
	private String description;
	@Min(1)
	@Max(99)
	private int age;
	@NotEmpty
	private String city;
	@NotEmpty
	private String page;
	@Size(min = 7 , max = 9)
	private String sex;
	
	public String getDescription() {
		return description;
	}
	public int getAge() {
		return age;
	}
	public String getCity() {
		return city;
	}
	public String getPage() {
		return page;
	}
	public String getSex() {
		return sex.toUpperCase();
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public DataDTO mapFromData(Data data) {
		this.setAge(data.getAge());
		this.setCity(data.getCity());
		this.setDescription(data.getDescription());
		this.setPage(data.getPage());
		this.setSex(data.getSex().toString());
		return this;
	}
	
}
