package com.freecodecamp.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
@Table(name = "token")
public class VerificationToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String token;
  @OneToOne(fetch = FetchType.LAZY)
  private RedditUser redditUser;
  private Instant expiryDate;
}
