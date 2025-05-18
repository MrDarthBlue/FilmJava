package com.example.movieapp.service;

import com.example.movieapp.model.Movie;
import com.example.movieapp.repository.MovieRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        // Csak akkor töltjük fel az adatokat, ha üres az adatbázis
        if (movieRepository.count() == 0) {
            movieRepository.save(new Movie("Inception", "Christopher Nolan", 2010));
            movieRepository.save(new Movie("The Matrix", "Lana Wachowski, Lilly Wachowski", 1999));
            System.out.println("Alapadatok betöltve");
        }
    }


    public List<Movie> getAllFilms() {
        return movieRepository.findAll();
    }

    public Movie getFilmById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));
    }

    public Movie addFilm(Movie movie) {
        return movieRepository.save(movie);  // Visszaadjuk az elmentett entitást
    }
    // Add this method to MovieService.java
    public void deleteFilm(Long id) {
        movieRepository.deleteById(id);
    }
}