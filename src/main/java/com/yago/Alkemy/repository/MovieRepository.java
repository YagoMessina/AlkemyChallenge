package com.yago.Alkemy.repository;

import com.yago.Alkemy.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
