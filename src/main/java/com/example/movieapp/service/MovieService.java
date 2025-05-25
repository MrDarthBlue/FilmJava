package com.example.movieapp.service;

import com.example.movieapp.model.Movie;
import com.example.movieapp.repository.MovieRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for movie operations.
 */
@Service
public class MovieService {
    /**
     * Repository for accessing movie data.
     */
    private final MovieRepository movieRepository;


    /**
     * Constructs a new MovieService with the given repository.
     * @param movieRepository the movie repository
     */
    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Initializes sample data if database is empty.
     */
    @PostConstruct
    @Transactional
    public void init() {
        if (movieRepository.count() == 0) {
            movieRepository.save(new Movie("Inception", "Christopher Nolan", 2010));
            movieRepository.save(new Movie("The Matrix", "Lana Wachowski, Lilly Wachowski", 1999));
            System.out.println("Initial data loaded");
        }
    }

    /**
     * Retrieves all movies.
     * @return list of all movies
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * Retrieves a movie by ID.
     * @param id the movie ID
     * @return the found movie
     * @throws RuntimeException if movie not found
     */
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    /**
     * Adds a new movie.
     * @param movie the movie to add
     * @return the saved movie
     */
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    /**
     * Deletes a movie by ID.
     * @param id the movie ID to delete
     */
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}