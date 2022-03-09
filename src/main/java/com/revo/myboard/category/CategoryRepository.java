package com.revo.myboard.category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Created By Revo
 */

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	/*
	 * FIND CATEGORY BY NAME
	 */
	
	Optional<Category> findByName(String name);
	
	/*
	 * EXISTS BY NAME
	 */
	
	boolean existsByName(String name);
	
}
