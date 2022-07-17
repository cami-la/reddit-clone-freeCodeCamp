package com.freecodecamp.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId;
  @NotBlank(message="Post name cannot be empty or Null")
  private String postName;
  @Nullable
  private String url;
  @Nullable
  @Lob
  private String description;
  private Integer voteCount;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", referencedColumnName = "userId")
  private RedditUser redditUser;
  private Instant createDate;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id", referencedColumnName = "id")
  private Subreddit subreddit;
}
