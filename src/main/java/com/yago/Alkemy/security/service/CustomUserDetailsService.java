package com.yago.Alkemy.security.service;

import com.yago.Alkemy.dto.UserDTO;
import com.yago.Alkemy.error.ApiException;
import com.yago.Alkemy.security.model.User;
import com.yago.Alkemy.security.repository.UserRepository;
import com.yago.Alkemy.service.WelcomeEmailService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private UserRepository repository;
  private BCryptPasswordEncoder encoder;

  private WelcomeEmailService welcomeEmailService;

  public CustomUserDetailsService(UserRepository repository, WelcomeEmailService welcomeEmailService) {
    this.repository = repository;
    this.encoder = new BCryptPasswordEncoder();
    this.welcomeEmailService = welcomeEmailService;
  }

  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    User user = repository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException(
        "Username does not exist: " + username));
    return user;
  }

  public User save(UserDTO userDTO) {
    if(repository.findByUsername(userDTO.getUsername()).isPresent())
      throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid username", "Username already exists");

    String activationToken = generateActivationToken();
    User user = repository.save(new User(userDTO.getUsername(), encoder.encode(userDTO.getPassword()), false, activationToken));
    welcomeEmailService.sendWelcomeEmail(userDTO.getUsername(), activationToken);
    return user;
  }

  private String generateActivationToken() {
    String charset = "1234567890qwertyuiopasdfghjklzxcvbnmQWWERTYUIOPASDFGHJKLZXCVBNM";
    StringBuilder token = new StringBuilder();
    Random random = new Random();
    for(int i = 0; i < 100; i++){
      token.append(charset.charAt(random.nextInt(charset.length())));
    }
    return token.toString();
  }

  public void activate(String activationToken) {
    User user = repository.findByActivationToken(activationToken).orElseThrow(()->
      new ApiException(HttpStatus.BAD_REQUEST, "Failed to Activate", "No user found with given token.")
    );
    if(user.isEnabled())
      throw new ApiException(HttpStatus.BAD_REQUEST, "Failed to Activate", "Your account is already activated.");
    user.activate();
    repository.save(user);
  }
}
