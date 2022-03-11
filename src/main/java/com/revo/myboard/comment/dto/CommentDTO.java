package com.revo.myboard.comment.dto;

import lombok.*;

import com.revo.myboard.comment.Comment;
import com.revo.myboard.like.dto.LikeDTO;
import com.revo.myboard.report.dto.ReportDTO;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/*
 * Created By Revo
 */

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class CommentDTO {

  private long id;
  private String user;
  private String content;
  private Date date;
  private Date lastEditDate;
  private List<ReportDTO> reports;
  private long post;
  private List<LikeDTO> likes;

  public static CommentDTO mapFromComment(Comment comment) {
    return CommentDTO
        .builder()
        .content(comment.getContent())
        .date(comment.getDate())
        .id(comment.getId())
        .lastEditDate(comment.getLastEditDate())
        .likes(comment.getMyLikes().stream().map(like -> LikeDTO.mapFromLike(like)).collect(
            toList()))

        .reports(
            comment.getReports().stream().map(report -> ReportDTO.mapFromReport(report)).collect(
                toList()))
        .user(comment.getAuthor().getLogin())
        .post(comment.getPost().getId())
        .build();
  }
}
