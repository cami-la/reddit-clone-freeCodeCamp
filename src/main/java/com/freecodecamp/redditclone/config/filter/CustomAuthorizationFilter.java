package com.freecodecamp.redditclone.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
  public static final String PREFIX_ATRIBUTE = "Bearer ";

  @Override
  protected void doFilterInternal
      (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getServletPath().equals("/api/auth/login")) {
      filterChain.doFilter(request, response);
    } else {
      String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
      try {
        if (authorizationHeader != null && authorizationHeader.startsWith(PREFIX_ATRIBUTE)) {
          log.info("Verify and validate token");
          String token = authorizationHeader.substring(PREFIX_ATRIBUTE.length());
          UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          filterChain.doFilter(request, response);
        }
        else {
          filterChain.doFilter(request, response);
        }
      } catch (Exception ex) {
        log.error("Erro logging in {}", ex.getMessage());
        response.setHeader("error:", ex.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Map<String, String> error = new HashMap();
        error.put("error_message", ex.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
      }
    }

  }

  private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
    log.info("Decoding token");
    //TODO: enviroment variable
    Algorithm algorithm = Algorithm.HMAC256("stringblog".getBytes());
    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT decodedJWT = verifier.verify(token);
    String username = decodedJWT.getSubject();
    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
    return new UsernamePasswordAuthenticationToken(username, null, authorities);
  }
}
