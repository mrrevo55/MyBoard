package com.revo.myboard.report;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.comment.CommentService;
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

	public Report getReportById(long id) {
		return repository.findById(id).orElseThrow(() -> new NullPointerException("R:"+id));
	}
	
	public Report getReportByIdAsUser(String token, long id) {
		var report = this.getReportById(id);
		if(!userService.currentLoggedUser(token).getReports().contains(report)) {
			throw new NullPointerException("R:"+id);
		}
		return report;
	}
	
	public long createReportForPost(String token, long post_id, String content) {
		var report = new Report();
		report.setReporter(userService.currentLoggedUser(token));
		report.setPost(postService.getPostById(post_id));
		report.setChecked(false);
		report.setDate(new Date(System.currentTimeMillis()));
		report.setContent(content);
		return repository.save(report).getId();
	}
	
	public long createReportForComment(String token, long comment_id, String content) {
		var report = new Report();
		report.setReporter(userService.currentLoggedUser(token));
		report.setComment(commentService.getCommentById(comment_id));
		report.setChecked(false);
		report.setDate(new Date(System.currentTimeMillis()));
		report.setContent(content);
		return repository.save(report).getId();
	}
	
	public List<Report> getAllNotCheckedReports(){
		return (List<Report>) repository.findByCheckedFalse();
 	}
	
	public void setReportAsChecked(long id) {
		getReportById(id).setChecked(true);
	}
	
	public void deleteReportById(long id) {
		repository.delete(getReportById(id));
	}
	
}
