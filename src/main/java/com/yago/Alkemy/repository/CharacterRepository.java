package com.yago.Alkemy.repository;

import com.yago.Alkemy.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
