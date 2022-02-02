package com.revo.myboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revo.myboard.entities.Like;

/*
 * Likes Repository
 * 
 * Created By Revo
 */

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{
	
	//FIND LIKE BY WHO LIKING ID
	public Like findByWho(long id);
	//FIND LIKE BY POST ID
	public Like findByPost(long id);
	//FIND LIKE BY COMMENT ID
	public Like findByComment(long id);
	//FIND LIKE BY USER ID
	public Like findByUser(long id);
	
}
