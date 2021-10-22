package com.yago.Alkemy.controller;

import com.yago.Alkemy.dto.CharacterDTO;
import com.yago.Alkemy.dto.MovieDTO;
import com.yago.Alkemy.model.Character;
import com.yago.Alkemy.model.Movie;
import com.yago.Alkemy.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/detailed")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(movieService.findAll());
    }

    @GetMapping
    public ResponseEntity<?> findAllWithoutDetail() {
        return ResponseEntity.ok(movieService.findAllWithoutDetail());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid MovieDTO movieDTO) {
        Movie movie = movieService.create(movieDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(movie.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> read(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        return ResponseEntity.ok(movie);
    }

    @PutMapping
    public ResponseEntity<Movie> update(@RequestBody @Valid MovieDTO movieDTO) {
        Movie movie = movieService.update(movieDTO);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
