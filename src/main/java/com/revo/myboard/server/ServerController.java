package com.revo.myboard.server;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.revo.myboard.security.annotation.ForAdmin;
import com.revo.myboard.security.annotation.ForUser;

/*
 *  Created By Revo
 */

@RestController
@RequestMapping("/server")
public class ServerController {
	
	private ServerService serverService;
	
	public ServerController(ServerService serverService) {
		super();
		this.serverService = serverService;
	}

	/*
	 * USER
	 */
	
	@GetMapping("/{name}")
	@ForUser
	@JsonView(ServerGetView.class)
	public Server getServerByName(@PathVariable String name) {
		return serverService.getServerByName(name);
	}
	
	/*
	 * ADMIN
	 */
	
	@PostMapping("/create")
	@ForAdmin
	public boolean createServer(@RequestParam String name) {
		return serverService.createServer(name);
	}
	
	@DeleteMapping("/delete/{name}")
	@ForAdmin
	public boolean deleteServerByName(@PathVariable String name) {
		return serverService.deleteServerByName(name);
	}
	
	@PutMapping("/rename/{name}")
	@ForAdmin
	public boolean renameServerByName(@PathVariable String name, @RequestParam String new_name) {
		return serverService.renameServerByName(name, new_name);
	}
	
}
