package com.revo.myboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.entities.Server;
import com.revo.myboard.repositories.ServerRepository;

/*
 * Server Service Class
 * 
 * Created By Revo
 */

@Service
public class ServerService {
	
	/*
	 * Data
	 */

	@Autowired
	private ServerRepository repo;
	
	/*
	 * Create New Server
	 */
	
	public boolean create(String name) {
		if(repo.findByName(name) != null) return false;
		Server s = new Server();
		s.setName(name);
		repo.save(s);
		return true;
	}
	
	/*
	 * Delete Server By Name
	 */
	
	public boolean delete(String name) {
		Server s = get(name);
		if(s == null) return false;
		repo.delete(s);
		return true;
	}
	
	/*
	 * Get Server By Name
	 */

	public Server get(String name) {
		return repo.findByName(name);
	}
	
	/*
	 * Change Name Of Server
	 */
	
	@Transactional
	public boolean rename(String name, String new_name) {
		if(get(new_name) != null) return false;
		get(name).setName(new_name);
		return true;
	}
}
