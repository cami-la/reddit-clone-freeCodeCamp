package com.freecodecamp.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class NotificationEmail {
  private String subject;
  private String recipient;
  private String body;
}
