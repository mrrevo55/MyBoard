package com.revo.myboard.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revo.myboard.annotations.ForAdmin;
import com.revo.myboard.annotations.ForModerator;
import com.revo.myboard.annotations.ForUser;
import com.revo.myboard.entities.Report;
import com.revo.myboard.services.ReportService;

/*
 *  Post Controller
 *  
 *  Created By Revo
 */

@RestController
@RequestMapping("/report")
public class ReportController {

	/*
	 * Data
	 */
	
	@Autowired
	private ReportService reportService;
	
	//USER
	
	/*
	 * Report Post
	 */
	
	@GetMapping("/post")
	@ForUser
	public boolean post(@RequestHeader("Authorization") String token, @RequestParam String title) {
		return reportService.create(token, title);
	}
	
	/*
	 * Report Comment
	 */
	
	@GetMapping("/comment")
	@ForUser
	public boolean comment(@RequestHeader("Authorization") String token, @RequestParam long id) {
		return reportService.create(token, id);
	}
	
	//MODERATOR
	
	/*
	 * Set as viewed
	 */
	
	@GetMapping("/check")
	@ForModerator
	public boolean check(@RequestParam long id) {
		return reportService.checked(id);
	}
	
	/*
	 * Get All Not Viewed Reports
	 */
	
	@GetMapping("/notcheked")
	@ForModerator
	public List<Report> notchecked(){
		return reportService.getAllNotChecked();
	}
	
	//ADMIN
	
	/*
	 * Delete
	 */
	
	@GetMapping("/delete")
	@ForAdmin
	public boolean delete(@RequestParam long id) {
		return reportService.delete(id);
	}
	
}
