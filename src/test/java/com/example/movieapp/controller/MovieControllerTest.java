package com.example.movieapp.controller;

import com.example.movieapp.model.Movie;
import com.example.movieapp.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    void testListMovies() throws Exception {
        Movie movie1 = new Movie("Movie 1", "Director 1", 2020);
        movie1.setId(1L);
        Movie movie2 = new Movie("Movie 2", "Director 2", 2021);
        movie2.setId(2L);

        Mockito.when(movieService.getAllFilms()).thenReturn(List.of(movie1, movie2));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(view().name("movies/list"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attribute("movies", hasSize(2)))
                .andExpect(model().attribute("movies", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("title", is("Movie 1"))
                        )
                )));
    }

    @Test
    void testViewMovie() throws Exception {
        Movie movie = new Movie("Movie 1", "Director 1", 2020);
        movie.setId(1L);

        Mockito.when(movieService.getFilmById(1L)).thenReturn(movie);

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("movies/details"))
                .andExpect(model().attributeExists("film"))
                .andExpect(model().attribute("film", hasProperty("title", is("Movie 1"))));
    }

    @Test
    void testShowAddForm() throws Exception {
        mockMvc.perform(get("/movies/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("movies/form"))
                .andExpect(model().attributeExists("movie"));
    }

    @Test
    void testAddMovie() throws Exception {
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "New Movie")
                        .param("director", "Some Director")
                        .param("year", "2025"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movies"));

        Mockito.verify(movieService).addFilm(Mockito.any(Movie.class));
    }

    @Test
    void testDeleteMovie() throws Exception {
        mockMvc.perform(post("/movies/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movies"));

        Mockito.verify(movieService).deleteFilm(1L);
    }
}
