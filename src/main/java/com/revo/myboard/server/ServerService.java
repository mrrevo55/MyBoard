package com.revo.myboard.server;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revo.myboard.util.ObjectNotExistsException;

/*
 * Created By Revo
 */

@Service
public class ServerService {

	private ServerRepository repo;
	
	public ServerService(ServerRepository repo) {
		this.repo = repo;
	}

	public boolean createServer(String name) {
		if(repo.findByName(name) != null) {
			return false;
		}
		var server = new Server();
		server.setName(name);
		repo.save(server);
		return true;
	}
	
	public boolean deleteServerByName(String name) {
		var server = getServerByName(name);
		if(server == null) {
			return false;
		}
		repo.delete(server);
		return true;
	}

	public Server getServerByName(String name) {
		return repo.findByName(name).orElseThrow(() -> new ObjectNotExistsException(name));
	}
	
	@Transactional
	public boolean renameServerByName(String name, String new_name) {
		if(getServerByName(new_name) != null) {
			return false;
		}
		getServerByName(name).setName(new_name);
		return true;
	}
}
