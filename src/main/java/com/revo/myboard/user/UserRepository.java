package com.revo.myboard.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Created By Revo
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	/*
	 * Find User By Login
	 */
	
	Optional<User> findByLogin(String login);
	
	/*
	 * Find User By Login
	 */
	
	Optional<User> findByEmail(String email);
	
	/*
	 * Find User By Code
	 */
	
	Optional<User> findByCode(String code);
	
	/*
	 * Find All Active Users
	 */
	
	Iterable<User> findByActiveTrue();
	
	/*
	 * Find All Blocked Users
	 */
	
	Iterable<User> findByBlockedTrue();
	
}
