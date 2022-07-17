package com.freecodecamp.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
public class Subreddit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank(message= "Community name is required")
  private String name;
  @NotBlank(message = "Description is required")
  private String description;
  @OneToMany
  private List<Post> posts;
  private Instant createdDate;
  @ManyToOne(fetch = FetchType.LAZY)
  private RedditUser redditUser;
}
