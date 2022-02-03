package com.revo.myboard.post;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Created By Revo
 */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	/*
	 * FIND POST BY TITLE
	 */
	
	Optional<Post> findByTitle(String title);

}
