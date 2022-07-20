package com.freecodecamp.redditclone.service;

import com.freecodecamp.redditclone.model.RedditUser;
import com.freecodecamp.redditclone.repository.RedditUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final RedditUserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    RedditUser user = userRepository.findByUsername(username).orElseThrow(() -> {
      throw new UsernameNotFoundException(String.format("No user found with username: %s", username));
    });
    return new User(user.getUsername(),
        user.getPassword(),
        user.isEnabled(),
        user.isCredentialsNonExpired(),
        user.isAccountNonExpired(),
        user.isAccountNonLocked(),
        user.getAuthorities());
  }
}
