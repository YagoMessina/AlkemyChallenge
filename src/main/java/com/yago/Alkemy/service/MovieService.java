package com.yago.Alkemy.service;

import com.yago.Alkemy.dto.MovieDTO;
import com.yago.Alkemy.dto.MovieWithoutDetailDTO;
import com.yago.Alkemy.error.ApiException;
import com.yago.Alkemy.mapper.MovieMapper;
import com.yago.Alkemy.model.Genre;
import com.yago.Alkemy.model.Movie;
import com.yago.Alkemy.repository.GenreRepository;
import com.yago.Alkemy.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private MovieRepository movieRepository;
    private GenreRepository genreRepository;

    public MovieService(MovieRepository movieRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
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
        Comparator<Movie> comparator = (movie1, movie2) -> 0;

        if(param.containsKey("title")){
            predicate = predicate.and(movie -> movie.getTitle().equals(param.get("title")));
        }else if(param.containsKey("genre")){
            predicate = predicate.and(movie -> movie.getGenre().getName().equals(param.get("genre")));
        }else if(param.containsKey("order")){
            String order = param.get("order");
            if(order.equals("ASC")){
                comparator = (movie1, movie2) -> movie1.getCreationDate().compareTo(movie2.getCreationDate());
            }else if (order.equals("DESC")){
                comparator = (movie1, movie2) -> movie2.getCreationDate().compareTo(movie1.getCreationDate());
            }
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid param", "Valid params are: title, genre, order ASC|DESC");
        }

        return findAll().stream().filter(predicate).sorted(comparator).collect(Collectors.toList());
    }

    private List<MovieWithoutDetailDTO> findAllWithoutDetail() {
        return findAll().stream().map(MovieMapper::toMovieWithoutDetailDTO).collect(Collectors.toList());
    }

    public Movie create(MovieDTO movieDTO) {
        if(movieDTO.getId() != null){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid body", "Cannot pass an id");
        }
        return save(movieDTO);
    }

    private Movie save(MovieDTO movieDTO) {
        Genre genre = null;
        Optional<Genre> genreOptional = genreRepository.findByName(movieDTO.getGenre().getName());
        if(!genreOptional.isPresent()){
            genre = new Genre(movieDTO.getGenre().getName(), movieDTO.getGenre().getImage(), new HashSet<>());
            genreRepository.save(genre);
        }else {
            genre = genreOptional.get();
        }
        Movie movie = MovieMapper.toModel(movieDTO, genre);
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
