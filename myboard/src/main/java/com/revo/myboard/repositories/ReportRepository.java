package com.revo.myboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revo.myboard.entities.Report;

/*
 * Report Repository
 * 
 * 
 * Created By Revo
 */

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

	//FIND ALL NOT VIEWED REPORTS
	public Iterable<Report> findByViewedFalse();
	
}
