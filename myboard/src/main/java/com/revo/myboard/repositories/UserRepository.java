package com.revo.myboard.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revo.myboard.entities.User;

/*
 * User Repository
 * 
 * 
 * Created By Revo
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	//Find User By Login
	public Optional<User> findByLogin(String login); 
	//Find User By Login
	public Optional<User> findByEmail(String email);
	//Find User By Code
	public Optional<User> findByCode(String code);
	//Find All Active Users
	public Iterable<User> findByActiveTrue();
	//Find All Blocked Users
	public Iterable<User> findByBlockedTrue();
	
}
