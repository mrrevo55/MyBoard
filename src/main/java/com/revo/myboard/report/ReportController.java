package com.revo.myboard.report;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@PostMapping("/post/{title}")
	@ForUser
	public boolean postReportByTitle(@RequestHeader("Authorization") String token, @PathVariable String title) {
		return reportService.createReportForPost(token, title);
	}
	
	@PostMapping("/comment/{id}")
	@ForUser
	public boolean commentReportById(@RequestHeader("Authorization") String token, @PathVariable long id) {
		return reportService.createReportForComment(token, id);
	}
	
	/*
	 * MODERATOR
	 */
	
	@PutMapping("/check/{id}")
	@ForModerator
	public boolean setReportAsChecked(@PathVariable long id) {
		return reportService.setReportAsChecked(id);
	}
	
	@GetMapping("/notcheked")
	@ForModerator
	public List<Report> getAllNotCheckedReports(){
		return reportService.getAllNotCheckedReports();
	}
	
	/*
	 * ADMIN
	 */
	
	@DeleteMapping("/delete/{id}")
	@ForAdmin
	public boolean deleteReportById(@PathVariable long id) {
		return reportService.deleteReportById(id);
	}
	
}
