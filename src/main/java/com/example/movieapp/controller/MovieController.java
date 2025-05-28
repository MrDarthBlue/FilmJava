package com.example.movieapp.controller;

import com.example.movieapp.model.Movie;
import com.example.movieapp.service.MovieService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * Constructs a controller with the specified service.
     *
     * @param movieService the movie service
     */
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Shows list of all movies.
     *
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
     *
     * @param id the movie ID
     * @param model the model to add attributes
     * @return the view name
     */
    @GetMapping("/{id}")
    public String viewMovie(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "movies/details";
    }

    /**
     * Shows the form to create a new movie.
     *
     * @param model the model to add attributes
     * @return the view name
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movies/form";
    }

    /**
     * Handles form submission to create a new movie.
     *
     * @param movie the movie from the form
     * @param posterFile the uploaded poster file
     * @return redirect to list view
     */
    @PostMapping
    public String saveMovie(@ModelAttribute Movie movie,
                            @RequestParam("posterFile") MultipartFile posterFile) {
        try {
            if (!posterFile.isEmpty()) {
                String uploadDir = "data/posters";
                Files.createDirectories(Path.of(uploadDir));
                String fileName = System.currentTimeMillis()
                        + "_" + posterFile.getOriginalFilename();
                Path filePath = Path.of(uploadDir, fileName);
                Files.copy(posterFile.getInputStream(),
                        filePath, StandardCopyOption.REPLACE_EXISTING);
                movie.setPosterFilename(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        movieService.addMovie(movie);
        return "redirect:/movies";
    }

    /**
     * Deletes a movie by ID.
     *
     * @param id the ID of the movie to delete
     * @return redirect to list view
     */
    @PostMapping("/{id}/delete")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }
}
