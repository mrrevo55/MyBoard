package com.revo.myboard.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.revo.myboard.comment.Comment;
import com.revo.myboard.group.Group;
import com.revo.myboard.like.Like;
import com.revo.myboard.post.Post;
import com.revo.myboard.report.Report;

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
public final class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(unique=true)
	private String login;
	private String password;
	@Column(unique=true)
	private String email;
	private boolean blocked;
	private boolean active;
	@OneToMany(mappedBy="author")
	private List<Post> posts;
	@OneToMany(mappedBy="author")
	private List<Comment> comments;
	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="reporter")
	private List<Report> reports;
	@Embedded
	private Data data;
	@OneToMany(mappedBy="who", cascade=CascadeType.REMOVE)
	private List<Like> liked;
	@OneToOne
	@JoinColumn(referencedColumnName = "id", name="group_id")
	private Group group;
	private String code;
	
}
