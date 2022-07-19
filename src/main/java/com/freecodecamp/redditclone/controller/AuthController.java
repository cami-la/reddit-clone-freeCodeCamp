package com.freecodecamp.redditclone.controller;

import com.freecodecamp.redditclone.dto.RegisterRequest;
import com.freecodecamp.redditclone.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
    authService.signup(registerRequest);
    return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
  }

  @GetMapping("/accountVerification/{token}")
  public ResponseEntity<String> verifyAccount(@PathVariable(name = "token") String token) {
    authService.verifyAccount(token);
    return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
  }
}
