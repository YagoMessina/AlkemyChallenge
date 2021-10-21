package com.yago.Alkemy.security.repository;


import com.yago.Alkemy.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByActivationToken(String activationToken);
}
