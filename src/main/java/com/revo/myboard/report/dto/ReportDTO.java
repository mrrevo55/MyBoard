package com.revo.myboard.report.dto;

import java.util.Date;

import com.revo.myboard.report.Report;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * Created By Revo
 */

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class ReportDTO {

	private long id;
	private String reporter;
	private long post;
	private String postTitle;
	private long comment;
	private Date date;
	private boolean checked;
	private String content;

	public static ReportDTO mapFromReport(Report report) {
		if (report.getComment() != null) {
			return ReportDTO.builder().id(report.getId()).reporter(report.getReporter().getLogin())
					.comment(report.getComment().getId()).date(report.getDate()).checked(report.isChecked())
					.content(report.getContent()).build();
		} else {
			return ReportDTO.builder().id(report.getId()).reporter(report.getReporter().getLogin())
					.postTitle(report.getPost().getTitle()).date(report.getDate()).checked(report.isChecked())
					.content(report.getContent()).build();
		}
	}

}
