package com.revo.myboard.comment;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.revo.myboard.like.Like;
import com.revo.myboard.post.Post;
import com.revo.myboard.report.Report;
import com.revo.myboard.user.User;

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
public final class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@ManyToOne(optional = true, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User author;
	@Type(type="text")
	private String content;
	@OneToMany(mappedBy="comment", cascade=CascadeType.REMOVE)
	private List<Like> myLikes;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastEditDate;
	@OneToMany(mappedBy="comment", cascade=CascadeType.REMOVE)
	private List<Report> reports;
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name="post_id")
	private Post post;
	
}
