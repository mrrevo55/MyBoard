package com.revo.myboard.like;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.revo.myboard.comment.Comment;
import com.revo.myboard.post.Post;
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
@Table(name="likes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Like {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@ManyToOne(optional = true, fetch=FetchType.LAZY)
	@JoinColumn(name="post_id")
	private Post post;
	@ManyToOne(optional = true, fetch=FetchType.LAZY)
	@JoinColumn(name="comment_id")
	private Comment comment;
	@ManyToOne
	@JoinColumn(name="who_id")
	private User who;
	
}
