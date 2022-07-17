package com.freecodecamp.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
public class RedditUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  @NotBlank(message="Username is required")
  private String username;
  @NotBlank(message="Password is required")
  private String password;
  @Email
  @NotEmpty(message = "Email is required")
  private String email;
  private Instant created;
  private boolean enable;
}
