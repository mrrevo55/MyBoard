package com.revo.myboard.report;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.comment.CommentService;
import com.revo.myboard.group.Authority;
import com.revo.myboard.post.PostService;
import com.revo.myboard.user.UserService;

/*
 * Created By Revo
 */

@Service
@Transactional
public class ReportService {
	
	private ReportRepository repository;
	private UserService userService;
	private PostService postService;
	private CommentService commentService;
	
	public ReportService(ReportRepository repository, UserService userService, PostService postService,
			CommentService commentService) {
		this.repository = repository;
		this.userService = userService;
		this.postService = postService;
		this.commentService = commentService;
	}

	public Report getReportById(String token, long id) {
		var report = repository.findById(id).orElseThrow(() -> new NullPointerException("R:"+id));
		var user = userService.currentLoggedUser(token);
		if(user.getGroup().getAuthority().equals(Authority.USER) && !user.getReports().contains(report)) {
			throw new NullPointerException("R:"+id);
		}
		return report;
	}
	
	public long createReportForPost(String token, long post_id, String content) {
		var report = new Report();
		var user = userService.currentLoggedUser(token);
		var post = postService.getPostById(post_id);
		if(post.getAuthor().equals(user)) {
			throw new IllegalArgumentException("P:"+post_id);
		}
		report.setReporter(user);
		report.setPost(post);
		report.setChecked(false);
		report.setDate(new Date(System.currentTimeMillis()));
		report.setContent(content);
		return repository.save(report).getId();
	}
	
	public long createReportForComment(String token, long comment_id, String content) {
		var report = new Report();
		var user = userService.currentLoggedUser(token);
		var comment = commentService.getCommentById(comment_id);
		if(comment.getAuthor().equals(user)) {
			throw new IllegalArgumentException("C:"+comment_id);
		}
		report.setReporter(user);
		report.setComment(comment);
		report.setChecked(false);
		report.setDate(new Date(System.currentTimeMillis()));
		report.setContent(content);
		return repository.save(report).getId();
	}
	
	public List<Report> getAllNotCheckedReports(){
		return (List<Report>) repository.findByCheckedFalse();
 	}
	
	public void setReportAsChecked(long id) {
		var report = repository.findById(id).orElseThrow(() -> new NullPointerException("R:"+id));
		if(report.isChecked()) {
			throw new IllegalArgumentException("R:"+id);
		}
		report.setChecked(true);
	}
	
	public void deleteReportById(long id) {
		repository.delete(repository.getById(id));
	}
	
}
