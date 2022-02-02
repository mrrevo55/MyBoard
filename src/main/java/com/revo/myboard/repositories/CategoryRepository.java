package com.revo.myboard.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revo.myboard.entities.Category;

/*
 * Category Repository
 * 
 * 
 * Created By Revo
 */

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	//FIND CATEGORY BY NAME
	public Optional<Category> findByName(String name);
	
}
