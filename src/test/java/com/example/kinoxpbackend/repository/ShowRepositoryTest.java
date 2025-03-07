package com.example.kinoxpbackend.repository;

import com.example.kinoxpbackend.model.Movie;
import com.example.kinoxpbackend.model.Show;
import com.example.kinoxpbackend.model.Theatre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ShowRepositoryTest {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    private Movie testMovie;
    private Theatre testTheatre;
    private Show testShow;

    @BeforeEach
    void setUp() {
        testMovie = new Movie("Inception", "Sci-Fi", 148, 13);
        movieRepository.save(testMovie);

        testTheatre = new Theatre("Main Hall", 25, 16);
        theatreRepository.save(testTheatre);

        testShow = new Show(testMovie, testTheatre, LocalDateTime.now().plusDays(1));
        showRepository.save(testShow);
    }

    @AfterEach
    void tearDown() {
        showRepository.deleteAll();
        movieRepository.deleteAll();
        theatreRepository.deleteAll();
    }

    @Test
    void testSaveShow() {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(2);
        Show show = new Show(testMovie, testTheatre, localDateTime);
        Show savedShow = showRepository.save(show);
        assertNotNull(savedShow.getShowId());
        assertEquals(localDateTime, savedShow.getShowTime());
    }

    @Test
    void testFindShowById() {
        Optional<Show> foundShow = showRepository.findById(testShow.getShowId());
        assertTrue(foundShow.isPresent());
        assertEquals(testMovie.getTitle(), foundShow.get().getMovie().getTitle());
    }

    @Test
    void testFindShowsByTheatre() {
        List<Show> shows = showRepository.findByTheatre_TheatreId(testTheatre.getTheatreId());
        assertFalse(shows.isEmpty());
        assertEquals(testTheatre.getName(), shows.get(0).getTheatre().getName());
    }

    @Test
    void testDeleteShow() {
        showRepository.delete(testShow);
        Optional<Show> deletedShow = showRepository.findById(testShow.getShowId());
        assertFalse(deletedShow.isPresent());
    }
}

