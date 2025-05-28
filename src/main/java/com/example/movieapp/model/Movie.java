package com.example.movieapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a movie entity in the database.
 */
@Entity
public class Movie {

    /**
     * The unique identifier for the movie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the movie.
     */
    private String title;

    /**
     * The director of the movie.
     */
    private String director;

    /**
     * The year the movie was released.
     */
    @Column(name = "release_year")
    private int year;

    /**
     * Default constructor required by JPA.
     */
    public Movie() {
    }

    /**
     * Constructs a new Movie with specified parameters.
     *
     * @param title    the title of the movie
     * @param director the director of the movie
     * @param year     the release year of the movie
     */
    public Movie(String title, String director, int year) {
        this.title = title;
        this.director = director;
        this.year = year;
    }

    /**
     * Gets the movie ID.
     * @return the movie ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the movie title.
     * @return the movie title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the movie director.
     * @return the movie director
     */
    public String getDirector() {
        return director;
    }

    /**
     * Gets the release year.
     * @return the release year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the movie ID.
     * @param id the movie ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the movie title.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the movie director.
     * @param director the director to set
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Sets the release year.
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * The filename of the movie poster.
     */
    @Column(name = "poster_filename")
    private String posterFilename;

    /**
     * Gets the poster filename.
     * @return the poster filename
     */
    public String getPosterFilename() {
        return posterFilename;
    }

    /**
     * Sets the poster filename.
     * @param posterFilename the poster filename to set
     */
    public void setPosterFilename(String posterFilename) {
        this.posterFilename = posterFilename;
    }

}
