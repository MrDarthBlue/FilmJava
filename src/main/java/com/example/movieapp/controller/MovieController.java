package com.example.movieapp.controller;

import com.example.movieapp.model.Movie;
import com.example.movieapp.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public String listMovies(Model model) {
        model.addAttribute("movies", movieService.getAllFilms());
        return "movies/list";
    }

    @GetMapping("/{id}")
    public String viewMovie(@PathVariable Long id, Model model) {
        model.addAttribute("film", movieService.getFilmById(id));
        return "movies/details";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movies/form";  // Ellenőrizze, hogy a templates/movies/form.html létezik
    }

    @PostMapping
    public String addMovie(@ModelAttribute Movie movie) {
        movieService.addFilm(movie);
        return "redirect:/movies";
    }
}