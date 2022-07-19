package com.freecodecamp.redditclone.repository;

import com.freecodecamp.redditclone.model.RedditUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedditUserRepository extends JpaRepository<RedditUser, Long> {
  Optional<RedditUser> findByUsername(String username);
}
