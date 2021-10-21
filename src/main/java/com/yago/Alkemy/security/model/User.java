package com.yago.Alkemy.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table
public class User implements UserDetails {

  private static final int NAME_LENGTH = 70;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = NAME_LENGTH, unique = true)
  private String username;

  @Column(nullable = false, length = NAME_LENGTH)
  private String password;

  @Column(nullable = false)
  private boolean active;

  @Column(nullable = false)
  private String activationToken;

  /** ORM's default constructor. */
  User() {
  }

  public User(String username, String password, boolean active, String activationToken) {
    this.username = username;
    this.password = password;
    this.active = active;
    this.activationToken = activationToken;
  }

  public Long getId() {
    return id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public String getActivationToken() {
    return activationToken;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return active;
  }

  public void activate(){
    active = true;
  }
}
