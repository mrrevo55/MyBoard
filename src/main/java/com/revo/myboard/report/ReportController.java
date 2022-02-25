package com.revo.myboard.report;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.security.annotation.ForAdmin;
import com.revo.myboard.security.annotation.ForModerator;
import com.revo.myboard.security.annotation.ForUser;

/*
 *  Created By Revo
 */

@RestController
@RequestMapping("/report")
public class ReportController {
	
	private ReportService reportService;
	
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}
	
	/*
	 * USER
	 */

	@GetMapping("/logged/{id}")
	@ForUser
	public ReportDTO getReportByIdAsUser(@PathVariable long id, @RequestHeader("Authorization") String token) {
		return new ReportDTO().mapFromReport(reportService.getReportByIdAsUser(token, id));
	}
	
	@PostMapping("/post/{post_id}")
	@ForUser
	public long postReportById(@RequestHeader("Authorization") String token, @PathVariable long post_id, @RequestParam String content) {
		return reportService.createReportForPost(token, post_id, content);
	}
	
	@PostMapping("/comment/{comment_id}")
	@ForUser
	public long commentReportById(@RequestHeader("Authorization") String token, @PathVariable long comment_id, @RequestParam String content) {
		return reportService.createReportForComment(token, comment_id, content);
	}
	
	/*
	 * MODERATOR
	 */
	
	@PutMapping("/check/{id}")
	@ForModerator
	public void setReportAsChecked(@PathVariable long id) {
		reportService.setReportAsChecked(id);
	}
	
	@GetMapping("/notchecked")
	@ForModerator
	public List<ReportDTO> getAllNotCheckedReports(){
		return reportService.getAllNotCheckedReports().stream().map(report -> new ReportDTO().mapFromReport(report)).toList();
	}
	
	@GetMapping("/{id}")
	public ReportDTO getReportById(@PathVariable long id) {
		return new ReportDTO().mapFromReport(reportService.getReportById(id));
	}
	
	/*
	 * ADMIN
	 */
	
	@DeleteMapping("/delete/{id}")
	@ForAdmin
	public void deleteReportById(@PathVariable long id) {
		reportService.deleteReportById(id);
	}
	
}
