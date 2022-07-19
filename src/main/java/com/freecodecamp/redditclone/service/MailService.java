package com.freecodecamp.redditclone.service;

import com.freecodecamp.redditclone.exception.SpringRedditException;
import com.freecodecamp.redditclone.model.NotificationEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MailService {
  private final JavaMailSender mailSender;
  private final MailContentBuilder mailContentBuilder;

  @Async
  public void sendMail(NotificationEmail notificationEmail) {
    MimeMessagePreparator messagePreparator = mimeMessage -> {
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
      mimeMessageHelper.setFrom("springreddit@gmail.com");
      mimeMessageHelper.setTo(notificationEmail.getRecipient());
      mimeMessageHelper.setSubject(notificationEmail.getSubject());
      mimeMessageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
    };
    try {
      mailSender.send(messagePreparator);
      log.info("Activation email sent!!");
    } catch (MailException e) {
      log.error("Activation email cannot sent!!");
      throw new SpringRedditException(String.format("Exception occurred when sending mail to %s", notificationEmail.getRecipient()));
    }
  }
}
