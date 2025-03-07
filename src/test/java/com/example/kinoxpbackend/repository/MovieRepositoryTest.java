package com.example.kinoxpbackend.repository;

import com.example.kinoxpbackend.model.Movie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    private Movie testMovie;

    @BeforeEach
    void setUp() {
        testMovie = new Movie("Inception", "Sci-Fi", 148, 13);
        movieRepository.save(testMovie);
    }

    @AfterEach
    void tearDown() {
        movieRepository.deleteAll();
    }

    @Test
    void testSaveMovie() {
        Movie movie = new Movie("Titanic", "Romance", 195, 12);
        Movie savedMovie = movieRepository.save(movie);
        assertNotNull(savedMovie.getMovieId());
        assertEquals("Titanic", savedMovie.getTitle());
    }

    @Test
    void testFindMovieById() {
        Optional<Movie> foundMovie = movieRepository.findById(testMovie.getMovieId());
        assertTrue(foundMovie.isPresent());
        assertEquals("Inception", foundMovie.get().getTitle());
    }

    @Test
    void testDeleteMovie() {
        movieRepository.delete(testMovie);
        Optional<Movie> deletedMovie = movieRepository.findById(testMovie.getMovieId());
        assertFalse(deletedMovie.isPresent());
    }
}