package com.revo.myboard.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revo.myboard.entities.Post;

/*
 * Post Repository
 * 
 * 
 * Created By Revo
 */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	//FIND POST BY TITLE
	public Optional<Post> findByTitle(String title);

}
