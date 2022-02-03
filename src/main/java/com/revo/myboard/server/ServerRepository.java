package com.revo.myboard.server;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Created By Revo
 */

@Repository
public interface ServerRepository extends JpaRepository<Server, Long>{
	
	/*
	 * FIND SERVER BY NAME
	 */
	
	Optional<Server> findByName(String name);
	
}