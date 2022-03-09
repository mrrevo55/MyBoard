package com.revo.myboard.section;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.revo.myboard.category.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Created By Revo
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Section {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(unique=true)
	private String name;
	@OneToMany(mappedBy="section", cascade=CascadeType.REMOVE)
	private List<Category> categories;
	
}
