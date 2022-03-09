package com.revo.myboard.report;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

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
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public final class Report {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reporter_id")
	private User reporter;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="post_id")
	private Post post;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="comment_id")
	private Comment comment;
	private boolean checked;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@Type(type="text")
	private String content;
	
}
