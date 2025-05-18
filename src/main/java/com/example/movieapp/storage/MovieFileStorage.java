package com.example.movieapp.storage;

import com.example.movieapp.model.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class MovieFileStorage {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void saveMovies(List<Movie> movies, String filepath) throws Exception {
        mapper.writeValue(new File(filepath), movies);
    }

    public static List<Movie> loadMovies(String filepath) throws Exception {
        return mapper.readValue(new File(filepath),
                mapper.getTypeFactory().constructCollectionType(List.class, Movie.class));
    }
}
