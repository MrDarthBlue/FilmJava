package com.example.movieapp.service;

import com.example.movieapp.model.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private final List<Movie> films = new ArrayList<>();

    public MovieService() {
        films.add(new Movie(1L, "Inception", "Christopher Nolan", 2010));
        films.add(new Movie(2L, "The Matrix", "Lana Wachowski, Lilly Wachowski", 1999));
        films.add(new Movie(3L, "Interstellar", "Christopher Nolan", 2014));
    }

    public List<Movie> getAllFilms() {
        return new ArrayList<>(films); // Visszaadunk másolatot
    }

    public Movie getFilmById(Long id) {
        return films.stream()
                .filter(film -> film.getId().equals(id)) // itt getId() használata
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));
    }

    public void addFilm(Movie movie) {
        movie.setId(generateNewId()); // setId() használata
        films.add(movie);
    }

    private Long generateNewId() {
        return films.stream()
                .mapToLong(Movie::getId)
                .max()
                .orElse(0L) + 1L;
    }
}