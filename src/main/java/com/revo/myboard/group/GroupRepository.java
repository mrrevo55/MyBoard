package com.revo.myboard.group;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Created By Revo
 */

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>{

	/*
	 * FIDN GROUP BY NAME
	 */
	
	Optional<Group> findByName(String name);

	/*
	 * GROUP EXISTS BY NAME
	 */
	
	boolean existsByName(String name);
	
}
