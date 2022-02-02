package com.revo.myboard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.entities.Comment;
import com.revo.myboard.entities.Post;
import com.revo.myboard.entities.Report;
import com.revo.myboard.entities.User;
import com.revo.myboard.repositories.ReportRepository;

/*
 * Post Service Class
 * 
 * Created By Revo
 */

@Service
public class ReportService {

	/*
	 * Data
	 */
	
	@Autowired
	private ReportRepository repo;
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	@Autowired
	private CommentService commentService;
	
	/*
	 * Create 
	 */
	
	@Transactional
	public boolean create(String reporter, String title) {
		User u = userService.get(reporter);
		if(u == null) return false;
		Post p = postService.get(title);
		if(p == null) return false;
		Report r = new Report();
		r.setReporter(u);
		r.setPost(p);
		repo.save(r);
		return true;
	}
	
	@Transactional
	public boolean create(String reporter, long id) {
		User u = userService.get(reporter);
		if(u == null) return false;
		Comment c = commentService.get(id);
		if(c == null) return false;
		Report r = new Report();
		r.setReporter(u);
		r.setComment(c);
		repo.save(r);
		return true;
	}
	
	/*
	 * Get All Not Viewed
	 */
	
	public List<Report> getAllNotChecked(){
		return (List<Report>) repo.findByCheckedFalse();
	}
	
	/*
	 * Get Report
	 */
	
	public Report get(long id) {
		return repo.getById(id);
	}
	
	/*
	 * Set Viewed To True
	 */
	
	@Transactional
	public boolean checked(long id) {
		Report r = get(id);
		if(r == null) return false;
		r.setChecked(true);
		return true;
	}
	
	/*
	 * Delete
	 */
	
	public boolean delete(long id) {
		if(get(id) == null) return false;
		repo.deleteById(id);
		return true;
	}
	
}
