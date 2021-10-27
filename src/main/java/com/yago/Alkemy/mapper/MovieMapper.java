package com.yago.Alkemy.mapper;

import com.yago.Alkemy.dto.MovieDTO;
import com.yago.Alkemy.dto.MovieWithoutDetailDTO;
import com.yago.Alkemy.model.Character;
import com.yago.Alkemy.model.Genre;
import com.yago.Alkemy.model.Movie;

import java.util.Set;
import java.util.stream.Collectors;

public class MovieMapper {

    public static Movie toModel(MovieDTO movieDTO, Genre genre) {
        Set<Character> characters = movieDTO.getCharacters().stream()
                .map(CharacterMapper::toModel).collect(Collectors.toSet());
        return new Movie(movieDTO.getId(), movieDTO.getImage(),
                movieDTO.getTitle(), movieDTO.getCreationDate(),
                movieDTO.getQualification(), characters,
                genre);
    }

    public static MovieWithoutDetailDTO toMovieWithoutDetailDTO(Movie movie) {
        return new MovieWithoutDetailDTO(movie.getImage(),
                movie.getTitle(), movie.getCreationDate());
    }
}
