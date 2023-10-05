package com.kn.knwremodel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "comments")
@Entity
public class Comment {
 
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
 
@Column(columnDefinition = "TEXT", nullable = false)
private String comment; // 댓글 내용
 
@Column(name = "created_date")
@CreatedDate
private String createdDate;

@Column(name = "modified_date")
@LastModifiedDate
private String modifiedDate;
 
@ManyToOne
@JoinColumn(name = "posts_id")
private Notice notice;


@Column(nullable = false)
private String user; // 작성자
}