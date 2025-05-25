package com.example.movieapp.service;

import com.example.movieapp.model.Movie;
import com.example.movieapp.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    private MovieRepository movieRepository;
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        movieRepository = mock(MovieRepository.class);
        movieService = new MovieService(movieRepository);
    }

    @Test
    void testGetAllMovies() {
        List<Movie> movies = Arrays.asList(
                new Movie("Inception", "Christopher Nolan", 2010),
                new Movie("The Matrix", "Lana Wachowski, Lilly Wachowski", 1999)
        );
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.getAllMovies();

        assertEquals(2, result.size());
        verify(movieRepository).findAll();
    }

    @Test
    void testGetMovieById_found() {
        Movie movie = new Movie("Inception", "Christopher Nolan", 2010);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Movie result = movieService.getMovieById(1L);

        assertEquals("Inception", result.getTitle());
        verify(movieRepository).findById(1L);
    }

    @Test
    void testGetMovieById_notFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> movieService.getMovieById(1L));
        assertTrue(ex.getMessage().contains("Movie not found with id: 1"));
        verify(movieRepository).findById(1L);
    }

    @Test
    void testAddMovie() {
        Movie movie = new Movie("Interstellar", "Christopher Nolan", 2014);
        when(movieRepository.save(movie)).thenReturn(movie);

        Movie result = movieService.addMovie(movie);

        assertEquals("Interstellar", result.getTitle());
        verify(movieRepository).save(movie);
    }

    @Test
    void testDeleteMovie() {
        Long id = 1L;
        doNothing().when(movieRepository).deleteById(id);

        movieService.deleteMovie(id);

        verify(movieRepository).deleteById(id);
    }

    @Test
    void testInitWhenEmpty() {
        when(movieRepository.count()).thenReturn(0L);
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        movieService.init();

        verify(movieRepository, times(2)).save(any(Movie.class));
    }

    @Test
    void testInitWhenNotEmpty() {
        when(movieRepository.count()).thenReturn(5L);

        movieService.init();

        verify(movieRepository, never()).save(any(Movie.class));
    }
}
