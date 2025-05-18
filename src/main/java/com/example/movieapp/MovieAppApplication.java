package com.example.movieapp;

import com.example.movieapp.model.Movie;
import com.example.movieapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.File;

@SpringBootApplication
public class MovieAppApplication {

    @Autowired
    private MovieRepository movieRepository;

    public static void main(String[] args) {
        SpringApplication.run(MovieAppApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            boolean created = dataDir.mkdirs();
            System.out.println("[INIT] 'data' directory created: " + created + " at " + dataDir.getAbsolutePath());
        } else {
            System.out.println("[INIT] 'data' directory already exists at: " + dataDir.getAbsolutePath());
        }

        // Tesztfilm mentése, ha az adatbázis üres
        if (movieRepository.count() == 0) {
            Movie movie = new Movie("The Matrix", "Lana Wachowski", 1999);
            movieRepository.save(movie);
            System.out.println("[INIT] Test movie saved to DB.");
        }

        // Ellenőrizzük, létrejött-e az adatbázisfájl
        File dbFile = new File("data/moviedb.mv.db");
        if (dbFile.exists()) {
            System.out.println("[INIT] Database file found at: " + dbFile.getAbsolutePath());
        } else {
            System.out.println("[INIT] Database file NOT FOUND at: " + dbFile.getAbsolutePath());
        }
    }
}
