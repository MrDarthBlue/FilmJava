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
import org.springframework.mock.web.MockMultipartFile;
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
        Movie movie1 = new Movie("Inception", "Christopher Nolan", 2010);
        movie1.setId(1L);
        Movie movie2 = new Movie("The Matrix", "Lana Wachowski", 1999);
        movie2.setId(2L);

        Mockito.when(movieService.getAllMovies()).thenReturn(List.of(movie1, movie2));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(view().name("movies/list"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attribute("movies", hasSize(2)))
                .andExpect(model().attribute("movies", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("title", is("Inception"))
                        )
                )));
    }

    @Test
    void testViewMovie() throws Exception {
        Movie movie = new Movie("Interstellar", "Christopher Nolan", 2014);
        movie.setId(3L);

        Mockito.when(movieService.getMovieById(3L)).thenReturn(movie);

        mockMvc.perform(get("/movies/3"))
                .andExpect(status().isOk())
                .andExpect(view().name("movies/details"))
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attribute("movie", hasProperty("title", is("Interstellar"))));
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/movies/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("movies/form"))
                .andExpect(model().attributeExists("movie"));
    }

    @Test
    void testSaveMovie() throws Exception {
        MockMultipartFile posterFile = new MockMultipartFile(
                "posterFile",
                "poster.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "FakeImageContent".getBytes());

        mockMvc.perform(multipart("/movies")
                        .file(posterFile)
                        .param("title", "Dune")
                        .param("director", "Denis Villeneuve")
                        .param("year", "2021"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movies"));

        Mockito.verify(movieService).addMovie(Mockito.any(Movie.class));
    }

    @Test
    void testDeleteMovie() throws Exception {
        Long movieIdToDelete = 5L;

        Mockito.doNothing().when(movieService).deleteMovie(movieIdToDelete);

        mockMvc.perform(post("/movies/{id}/delete", movieIdToDelete))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movies"));

        Mockito.verify(movieService).deleteMovie(movieIdToDelete);
    }
}
