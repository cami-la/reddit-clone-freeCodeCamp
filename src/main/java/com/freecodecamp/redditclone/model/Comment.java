package com.freecodecamp.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  @NotEmpty(message = "Text is required")
  private String text;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "postId", referencedColumnName = "postId")
  private Post post;
  private Instant createdDate;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="userId", referencedColumnName = "userId")
  private RedditUser redditUser;
}
