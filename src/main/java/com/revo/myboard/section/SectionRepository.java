package com.revo.myboard.section;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Created By Revo
 */

@Repository
public interface SectionRepository extends JpaRepository<Section, Long>{
	
	/*
	 * FIND SECTION BY NAME
	 */
	
	boolean existsByName(String name);
	
}