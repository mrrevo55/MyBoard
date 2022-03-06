package com.revo.myboard.report;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.report.dto.ContentDTO;
import com.revo.myboard.report.dto.ReportDTO;
import com.revo.myboard.security.annotation.ForAdmin;
import com.revo.myboard.security.annotation.ForModerator;
import com.revo.myboard.security.annotation.ForUser;

/*
 *  Created By Revo
 */

@RestController
@RequestMapping("/report")
@Validated
public class ReportController {
	
	private ReportService reportService;
	
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}
	
	/*
	 * USER
	 */

	@GetMapping("/{id}")
	@ForUser
	public ReportDTO getReportByIdAsUser(@PathVariable long id, @RequestHeader("Authorization") String token) {
		return new ReportDTO().mapFromReport(reportService.getReportById(token, id));
	}
	
	@PostMapping("/post/{post_id}")
	@ForUser
	@ResponseStatus(HttpStatus.CREATED)
	public long postReportById(@RequestHeader("Authorization") String token, @PathVariable long post_id, @RequestBody @Valid ContentDTO contentDTO) {
		return reportService.createReportForPost(token, post_id, contentDTO.getContent());
	}
	
	@PostMapping("/comment/{comment_id}")
	@ForUser
	@ResponseStatus(HttpStatus.CREATED)
	public long commentReportById(@RequestHeader("Authorization") String token, @PathVariable long comment_id, @RequestBody @Valid ContentDTO contentDTO) {
		return reportService.createReportForComment(token, comment_id, contentDTO.getContent());
	}
	
	/*
	 * MODERATOR
	 */
	
	@PatchMapping("/check/{id}")
	@ForModerator
	public void setReportAsChecked(@PathVariable long id) {
		reportService.setReportAsChecked(id);
	}
	
	@GetMapping("/notchecked")
	@ForModerator
	public List<ReportDTO> getAllNotCheckedReports(){
		return reportService.getAllNotCheckedReports().stream().map(report -> new ReportDTO().mapFromReport(report)).toList();
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
