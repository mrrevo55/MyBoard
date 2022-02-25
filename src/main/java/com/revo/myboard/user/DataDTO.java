package com.revo.myboard.user;

import javax.validation.constraints.Min;

/*
 * Created By Revo
 */

public class DataDTO {

	@Min(5)
	private String description;
	private int age;
	private String city;
	private String page;
	private String sex;
	
	public String getDescription() {
		return description;
	}
	public int getAge() {
		return age;
	}
	public String getCity() {
		return city.toUpperCase();
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
