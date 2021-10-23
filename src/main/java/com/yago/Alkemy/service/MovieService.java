package com.yago.Alkemy.service;

import com.yago.Alkemy.dto.CharacterDTO;
import com.yago.Alkemy.dto.MovieDTO;
import com.yago.Alkemy.dto.MovieWithoutDetailDTO;
import com.yago.Alkemy.error.ApiException;
import com.yago.Alkemy.mapper.CharacterMapper;
import com.yago.Alkemy.mapper.MovieMapper;
import com.yago.Alkemy.model.Character;
import com.yago.Alkemy.model.Movie;
import com.yago.Alkemy.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAll() {
        List<Movie> movies = movieRepository.findAll();
        if(movies.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "Not Found", "No movies found");
        return movies;
    }

    public List<?> findAll(Map<String, String> param) {
        if(param.isEmpty())
            return findAllWithoutDetail();

        Predicate<Movie> predicate = movie -> true;
        if(param.containsKey("title")){
            predicate = predicate.and(movie -> movie.getTitle().equals(param.get("title")));
        }else if(param.containsKey("genre")){
            //TODO filtrar por genero
        }else if(param.containsKey("order")){
            //TODO ordenar por fecha ASC | DESC
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid param", "Valid params are: title, genreId, order ASC|DESC");
        }

        return findAll().stream().filter(predicate).collect(Collectors.toList());
    }

    private List<MovieWithoutDetailDTO> findAllWithoutDetail() {
        return findAll().stream().map(MovieMapper::toMoviesDTO).collect(Collectors.toList());
    }

    public Movie create(MovieDTO movieDTO) {
        if(movieDTO.getId() != null){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid body", "Cannot pass an id");
        }
        return save(movieDTO);
    }

    private Movie save(MovieDTO movieDTO) {
        Movie movie = MovieMapper.toModel(movieDTO);
        return movieRepository.save(movie);
    }

    public Movie update(MovieDTO movieDTO) {
        findById(movieDTO.getId());
        return save(movieDTO);
    }

    public Movie findById(Long id) {
        if(id == null)
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid id", "The id cannot be null");
        Optional<Movie> movie = movieRepository.findById(id);
        if(!movie.isPresent())
            throw new ApiException(HttpStatus.NOT_FOUND, "Not found", "Movie not found with id: " + id);
        return movie.get();
    }


    public Movie delete(Long id) {
        Movie movie = findById(id);
        movieRepository.delete(movie);
        return movie;
    }
}
