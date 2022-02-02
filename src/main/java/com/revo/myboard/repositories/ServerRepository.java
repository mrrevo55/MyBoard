package com.revo.myboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revo.myboard.entities.Server;

/*
 * Server Repository
 * 
 * 
 * Created By Revo
 */

@Repository
public interface ServerRepository extends JpaRepository<Server, Long>{
	
	//FIND SERVER BY NAME
	public Server findByName(String name);
	
}