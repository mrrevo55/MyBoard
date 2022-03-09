package com.revo.myboard.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/*
 * Created By Revo
 */

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

	/*
	 * FIND ALL NOT VIEWED REPORTS
	 */
	
	@Query("FROM Report r where r.checked = 0")
	Iterable<Report> findByCheckedFalse();
	
}
