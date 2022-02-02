package com.revo.myboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.revo.myboard.annotations.ForAdmin;
import com.revo.myboard.annotations.ForUser;
import com.revo.myboard.entities.Server;
import com.revo.myboard.services.ServerService;
import com.revo.myboard.views.ServerGetView;

/*
 *  Server Controller
 *  
 *  Created By Revo
 */

@RestController
@RequestMapping("/server")
public class ServerController {

	/*
	 * Data
	 */
	
	@Autowired
	private ServerService serverService;
	
	//USER
	
	@GetMapping
	@ForUser
	@JsonView(ServerGetView.class)
	public Server get(@RequestParam String name) {
		return serverService.get(name);
	}
	
	//ADMIN
	
	/*
	 * Create 
	 */
	
	@GetMapping("/create")
	@ForAdmin
	public boolean create(@RequestParam String name) {
		return serverService.create(name);
	}
	
	/*
	 * Delete
	 */
	
	@GetMapping("/delete")
	@ForAdmin
	public boolean delete(@RequestParam String name) {
		return serverService.delete(name);
	}
	
	/*
	 * Rename
	 */
	
	@GetMapping("/rename")
	@ForAdmin
	public boolean rename(@RequestParam String name, @RequestParam String new_name) {
		return serverService.rename(name, new_name);
	}
	
}
