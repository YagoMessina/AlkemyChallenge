package com.yago.Alkemy.controller;

import com.yago.Alkemy.dto.CharacterDTO;
import com.yago.Alkemy.dto.MovieDTO;
import com.yago.Alkemy.model.Character;
import com.yago.Alkemy.model.Movie;
import com.yago.Alkemy.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(summary = "List of movies", description = "param are optional")
    @GetMapping
    public ResponseEntity<?> find(@RequestParam @Schema(defaultValue = "{\n" +
            "  \"title\": \"string\",\n" +
            "  \"genre\": \"string\",\n" +
            "  \"order\": \"ASC|DESC\"\n" +
            "}") Map<String, String> param) {
        return ResponseEntity.ok(movieService.findAll(param));
    }

    @Operation(summary = "Detailed list of movies")
    @GetMapping("/detailed")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(movieService.findAll());
    }

    @Operation(summary = "Movie creation", description = "Do not pass an id")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid MovieDTO movieDTO) {
        Movie movie = movieService.create(movieDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(movie.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get movie by id")
    @GetMapping("/{id}")
    public ResponseEntity<Movie> read(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        return ResponseEntity.ok(movie);
    }

    @Operation(summary = "Movie update")
    @PutMapping
    public ResponseEntity<Movie> update(@RequestBody @Valid MovieDTO movieDTO) {
        Movie movie = movieService.update(movieDTO);
        return ResponseEntity.ok(movie);
    }

    @Operation(summary = "Movie delete")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
