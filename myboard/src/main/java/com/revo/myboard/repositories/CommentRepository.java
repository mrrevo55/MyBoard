package com.revo.myboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revo.myboard.entities.Comment;

/*
 * Comment Repository
 * 
 * 
 * Created By Revo
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {}