package com.example.movieapp.controller;

import com.example.movieapp.model.Movie;
import com.example.movieapp.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for movie-related web operations.
 */
@Controller
@RequestMapping("/movies")
public class MovieController {
    /**
     * Service layer for movie operations.
     */
    private final MovieService movieService;


    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Shows list of all movies.
     * @param model the model to add attributes
     * @return the view name
     */
    @GetMapping
    public String listMovies(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "movies/list";
    }

    /**
     * Shows details of a specific movie.
     * @param id the movie ID
     * @param model the model to add attributes
     * @return the view name
     */
    @GetMapping("/{id}")
    public String viewMovie(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "movies/details";
    }
}