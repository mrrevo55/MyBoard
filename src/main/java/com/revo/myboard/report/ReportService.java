package com.revo.myboard.report;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.comment.CommentService;
import com.revo.myboard.post.PostService;
import com.revo.myboard.user.UserService;
import com.revo.myboard.util.ObjectNotExistsException;

/*
 * Created By Revo
 */

@Service
@Transactional
public class ReportService {
	
	private ReportRepository repo;
	private UserService userService;
	private PostService postService;
	private CommentService commentService;
	
	public ReportService(ReportRepository repo, UserService userService, PostService postService,
			CommentService commentService) {
		this.repo = repo;
		this.userService = userService;
		this.postService = postService;
		this.commentService = commentService;
	}

	public boolean createReportForPost(String reporter, String title) {
		var user = userService.getUserByLogin(reporter);
		if(user == null) {
			return false;
		}
		var post = postService.getPostByTitle(title);
		if(post == null) {
			return false;
		}
		var report = new Report();
		report.setReporter(user);
		report.setPost(post);
		repo.save(report);
		return true;
	}
	
	public boolean createReportForComment(String reporter, long id) {
		var user = userService.getUserByLogin(reporter);
		if(user == null) {
			return false;
		}
		var comment = commentService.getCommentById(id);
		if(comment == null) {
			return false;
		}
		var report = new Report();
		report.setReporter(user);
		report.setComment(comment);
		repo.save(report);
		return true;
	}
	
	public List<Report> getAllNotCheckedReports(){
		return (List<Report>) repo.findByCheckedFalse();
	}
	
	public Report getReportById(long id) {
		return repo.findById(id).orElseThrow(() -> new ObjectNotExistsException("R"+String.valueOf(id)));
	}
	
	public boolean setReportAsChecked(long id) {
		var report = getReportById(id);
		if(report == null) {
			return false;
		}
		report.setChecked(true);
		return true;
	}
	
	public boolean deleteReportById(long id) {
		if(getReportById(id) == null) {
			return false;
		}
		repo.deleteById(id);
		return true;
	}
	
}
