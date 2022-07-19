package com.freecodecamp.redditclone.service;

import com.freecodecamp.redditclone.dto.RegisterRequest;
import com.freecodecamp.redditclone.exception.SpringRedditException;
import com.freecodecamp.redditclone.model.NotificationEmail;
import com.freecodecamp.redditclone.model.RedditUser;
import com.freecodecamp.redditclone.model.VerificationToken;
import com.freecodecamp.redditclone.repository.RedditUserRepository;
import com.freecodecamp.redditclone.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final PasswordEncoder passwordEncoder;
  private final RedditUserRepository userRepository;
  private final VerificationTokenRepository verificationTokenRepository;
  private final MailService mailService;

  public void signup(RegisterRequest registerRequest) {
    RedditUser user = RedditUser.builder()
        .username(registerRequest.getUsername())
        .email(registerRequest.getEmail())
        .password(passwordEncoder.encode(registerRequest.getPassword()))
        .created(Instant.now())
        .enable(false)
        .build();
    userRepository.save(user);
    String token = generateVerificationToken(user);
    mailService.sendMail(new NotificationEmail(
        "Please activate your account",
        user.getEmail(),
        "Thank you for signing up to Spring Reddit. Please click on the below url to activate your account: \n http://localhost:8080/api/auth/accountVerification/" + token));
  }

  @Transactional
  private String generateVerificationToken(RedditUser user) {
    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = VerificationToken.builder()
        .token(token)
        .user(user)
        .build();
    verificationTokenRepository.save(verificationToken);
    return token;
  }

  public void verifyAccount(String token) {
    VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
        .orElseThrow(() -> {
          throw new SpringRedditException("Invalid Token");
        });
    fetchUserAndEnable(verificationToken);
  }

  @Transactional
  private void fetchUserAndEnable(VerificationToken verificationToken) {
    String username = verificationToken.getUser().getUsername();
    RedditUser user = userRepository.findByUsername(username)
        .orElseThrow(() -> {
          throw new SpringRedditException(String.format("User %s not found!", username));
        });
    user.setEnable(true);
    userRepository.save(user);
  }
}
