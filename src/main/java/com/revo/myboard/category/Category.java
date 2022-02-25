package com.revo.myboard.category;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.revo.myboard.post.Post;
import com.revo.myboard.section.Section;

/*
 * Created By Revo
 */

@Entity
public final class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	private long id;
	@NotEmpty
	@NotBlank
	@Column(unique=true)
	@Size(min=5, max=50)
	private String name;
	@OneToMany(mappedBy="category", cascade=CascadeType.REMOVE)
	private List<Post> posts;
	@ManyToOne
	@JoinColumn(name="section_id")
	private Section section;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}
	
}
