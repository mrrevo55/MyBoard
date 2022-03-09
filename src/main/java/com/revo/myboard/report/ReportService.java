package com.revo.myboard.report;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.comment.CommentService;
import com.revo.myboard.exception.ArgumentInUseException;
import com.revo.myboard.exception.ObjectNotExistsException;
import com.revo.myboard.group.Authority;
import com.revo.myboard.post.PostService;
import com.revo.myboard.user.UserService;

import lombok.AllArgsConstructor;

/*
 * Created By Revo
 */

@Service
@Transactional
@AllArgsConstructor
public class ReportService {

	private final ReportRepository repository;
	private final UserService userService;
	private final PostService postService;
	private final CommentService commentService;

	public Report getReportById(String token, long id) {
		var report = repository.findById(id).orElseThrow(() -> new ObjectNotExistsException(String.valueOf(id)));
		var user = userService.currentLoggedUser(token);
		if (user.getGroup().getAuthority().equals(Authority.USER) && !user.getReports().contains(report)) {
			throw new ObjectNotExistsException(String.valueOf(id));
		}
		return report;
	}

	public long createReportForPost(String token, long post_id, String content) {
		var user = userService.currentLoggedUser(token);
		var post = postService.getPostById(post_id);
		if (post.getAuthor().equals(user)) {
			throw new ArgumentInUseException(String.valueOf(post_id));
		}
		return repository.save(Report.builder().reporter(user).post(post).checked(false)
				.date(new Date(System.currentTimeMillis())).content(content).build()).getId();
	}

	public long createReportForComment(String token, long comment_id, String content) {
		var user = userService.currentLoggedUser(token);
		var comment = commentService.getCommentById(comment_id);
		if (comment.getAuthor().equals(user)) {
			throw new ArgumentInUseException(String.valueOf(comment_id));
		}
		return repository.save(Report.builder().reporter(user).comment(comment).checked(false)
				.date(new Date(System.currentTimeMillis())).content(content).build()).getId();
	}

	public List<Report> getAllNotCheckedReports() {
		return (List<Report>) repository.findByCheckedFalse();
	}

	public void setReportAsChecked(long id) {
		var report = repository.findById(id).orElseThrow(() -> new ObjectNotExistsException(String.valueOf(id)));
		if (report.isChecked()) {
			throw new ArgumentInUseException(String.valueOf(id));
		}
		report.setChecked(true);
	}

	public void deleteReportById(long id) {
		repository.delete(repository.getById(id));
	}

}
