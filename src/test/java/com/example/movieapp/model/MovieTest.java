package com.example.movieapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void testGettersAndSetters() {
        Movie movie = new Movie();

        movie.setId(10L);
        movie.setTitle("Interstellar");
        movie.setDirector("Christopher Nolan");
        movie.setYear(2014);
        movie.setPosterFilename("interstellar.jpg");

        assertEquals(10L, movie.getId());
        assertEquals("Interstellar", movie.getTitle());
        assertEquals("Christopher Nolan", movie.getDirector());
        assertEquals(2014, movie.getYear());
        assertEquals("interstellar.jpg", movie.getPosterFilename());
    }

    @Test
    void testConstructorAndGetters() {
        Movie movie = new Movie("Inception", "Christopher Nolan", 2010);

        assertNull(movie.getId()); // mivel nem állítottuk, null kell legyen
        assertEquals("Inception", movie.getTitle());
        assertEquals("Christopher Nolan", movie.getDirector());
        assertEquals(2010, movie.getYear());

        // posterFilename nincs konstruktornál megadva, így null az értéke
        assertNull(movie.getPosterFilename());
    }
}
