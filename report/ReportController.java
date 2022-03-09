package com.revo.myboard.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.report.dto.ContentDTO;
import com.revo.myboard.report.dto.ReportDTO;
import com.revo.myboard.security.annotation.ForAdmin;
import com.revo.myboard.security.annotation.ForModerator;
import com.revo.myboard.security.annotation.ForUser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 *  Created By Revo
 */

@RestController
@RequestMapping("/report")
@Validated
@AllArgsConstructor
@Slf4j
public class ReportController {
	
	private final ReportService reportService;
	
	/*
	 * USER
	 */

	@GetMapping("/{id}")
	@ForUser
	public ResponseEntity<ReportDTO> getReportByIdAsUser(@PathVariable long id, @RequestHeader("Authorization") String token, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" getting report with id: "+id);
		var report = ReportDTO.mapFromReport(reportService.getReportById(token, id));
		log.info("User with ip: "+request.getRemoteAddr()+" success got report with id: "+id);
		return ResponseEntity.ok(report);
	}
	
	@PostMapping("/post/{post_id}")
	@ForUser
	public ResponseEntity<Long> postReportById(@RequestHeader("Authorization") String token, @PathVariable long post_id, @RequestBody @Valid ContentDTO contentDTO, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" creating report with details: "+contentDTO.toString());
		var id = reportService.createReportForPost(token, post_id, contentDTO.getContent());
		log.info("User with ip: "+request.getRemoteAddr()+" success created report with details: "+contentDTO.toString());
		return ResponseEntity.status(HttpStatus.CREATED).body(id);
	}
	
	@PostMapping("/comment/{comment_id}")
	@ForUser
	public ResponseEntity<Long> commentReportById(@RequestHeader("Authorization") String token, @PathVariable long comment_id, @RequestBody @Valid ContentDTO contentDTO, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" reporting comment with id: "+comment_id+" with details: "+ contentDTO.toString());
		var id = reportService.createReportForComment(token, comment_id, contentDTO.getContent());
		log.info("User with ip: "+request.getRemoteAddr()+" success reported comment with id: "+comment_id+" with details: "+ contentDTO.toString());
		return ResponseEntity.status(HttpStatus.CREATED).body(id);
	}
	
	/*
	 * MODERATOR
	 */
	
	@PatchMapping("/check/{id}")
	@ForModerator
	public void setReportAsChecked(@PathVariable long id, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" checking report with id: "+ id);
		reportService.setReportAsChecked(id);
		log.info("User with ip: "+request.getRemoteAddr()+" success checked report with id: "+ id);
	}
	
	@GetMapping("/notchecked")
	@ForModerator
	public ResponseEntity<List<ReportDTO>> getAllNotCheckedReports(HttpServletRequest request){
		log.info("User with ip: "+request.getRemoteAddr()+" getting all not checked reports");
		var reports = reportService.getAllNotCheckedReports().stream().map(report -> ReportDTO.mapFromReport(report)).toList();
		log.info("User with ip: "+request.getRemoteAddr()+" success got all not checked reports");
		return ResponseEntity.ok(reports);
	}
	
	/*
	 * ADMIN
	 */
	
	@DeleteMapping("/delete/{id}")
	@ForAdmin
	public void deleteReportById(@PathVariable long id, HttpServletRequest request) {
		log.info("User with ip: "+request.getRemoteAddr()+" deleting report with id: "+id);
		reportService.deleteReportById(id);
		log.info("User with ip: "+request.getRemoteAddr()+" success deleted report with id: "+id);
	}
	
}
