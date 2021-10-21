package com.yago.Alkemy.security.service;

import com.yago.Alkemy.security.model.User;
import com.yago.Alkemy.security.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
  private static final int EXPIRATION_TIME = 1000 * 60 * 60;
  private static final String AUTHORITIES = "authorities";
  private final String SECRET_KEY;

  private UserRepository userRepository;

  public JwtService(UserRepository userRepository) {
    SECRET_KEY = Base64.getEncoder().encodeToString("key".getBytes());
    this.userRepository = userRepository;
  }

  public String createToken(UserDetails userDetails) {
    String username = userDetails.getUsername();
    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
    return Jwts.builder()
      .setSubject(username)
      .claim(AUTHORITIES, authorities)
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
      .compact();
  }

  public Boolean hasTokenExpired(String token) {
    return Jwts.parser()
      .setSigningKey(SECRET_KEY)
      .parseClaimsJws(token)
      .getBody()
      .getExpiration()
      .before(new Date());
  }

  public Boolean validateToken(String token) {
    Optional<User> user = userRepository.findByUsername(extractUsername(token));
    return user.isPresent() && !hasTokenExpired(token);
  }

  public String extractUsername(String token) {
    return Jwts.parser()
      .setSigningKey(SECRET_KEY)
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }

  public Collection<? extends GrantedAuthority> getAuthorities(String token) {
    Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    return (Collection<? extends GrantedAuthority>) claims.get(AUTHORITIES);
  }
}
