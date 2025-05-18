package com.example.movieapp.service;

import com.example.movieapp.model.Movie;
import com.example.movieapp.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    private MovieRepository movieRepository;
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        movieRepository = Mockito.mock(MovieRepository.class);
        movieService = new MovieService(movieRepository);
    }

    @Test
    public void getAllFilms_ShouldReturnAllMovies() {
        List<Movie> movies = Arrays.asList(
                new Movie("Inception", "Christopher Nolan", 2010),
                new Movie("The Matrix", "Lana Wachowski, Lilly Wachowski", 1999)
        );

        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.getAllFilms();

        assertEquals(2, result.size());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    public void getFilmById_ValidId_ShouldReturnMovie() {
        Movie movie = new Movie("Inception", "Christopher Nolan", 2010);
        movie.setId(1L);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Movie result = movieService.getFilmById(1L);

        assertEquals("Inception", result.getTitle());
    }

    @Test
    public void getFilmById_InvalidId_ShouldThrowException() {
        when(movieRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movieService.getFilmById(99L);
        });

        assertTrue(exception.getMessage().contains("Film not found with id"));
    }

    @Test
    public void addFilm_ShouldSaveAndReturnMovie() {
        Movie movie = new Movie("Interstellar", "Christopher Nolan", 2014);
        when(movieRepository.save(movie)).thenReturn(movie);

        Movie result = movieService.addFilm(movie);

        assertEquals("Interstellar", result.getTitle());
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    public void deleteFilm_ShouldDeleteMovieById() {
        Long id = 1L;
        movieService.deleteFilm(id);
        verify(movieRepository, times(1)).deleteById(id);
    }
}
