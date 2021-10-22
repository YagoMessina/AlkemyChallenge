package com.yago.Alkemy.mapper;

import com.yago.Alkemy.dto.MovieDTO;
import com.yago.Alkemy.dto.MovieWithoutDetailDTO;
import com.yago.Alkemy.model.Movie;

public class MovieMapper {

    public static Movie toModel(MovieDTO movieDTO) {
        return new Movie(movieDTO.getId(), movieDTO.getImage(),
                movieDTO.getTitle(), movieDTO.getCreationDate(),
                movieDTO.getQualification(), movieDTO.getCharacters());
    }

    public static MovieWithoutDetailDTO toMoviesDTO(Movie movie) {
        return new MovieWithoutDetailDTO(movie.getImage(),
                movie.getTitle(), movie.getCreationDate());
    }

}
