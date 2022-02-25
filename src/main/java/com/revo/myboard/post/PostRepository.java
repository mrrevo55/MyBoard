package com.revo.myboard.post;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
	
	/*
	 * POST EXISTS BY TITLE
	 */
	
	boolean existsByTitle(String title);
	
	/*
	 * DELETE POST BY ID
	 */
	
	@Modifying
	@Query("delete from Post p where p.id = ?1")
	void deleteById(long id);

}
