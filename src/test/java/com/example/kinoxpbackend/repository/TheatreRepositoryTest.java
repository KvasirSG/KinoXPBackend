package com.example.kinoxpbackend.repository;

import com.example.kinoxpbackend.model.Theatre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TheatreRepositoryTest {

    @Autowired
    private TheatreRepository theatreRepository;

    private Theatre testTheatre;

    @BeforeEach
    void setUp() {
        testTheatre = new Theatre("Main Hall", 25, 16);
        theatreRepository.save(testTheatre);
    }

    @AfterEach
    void tearDown() {
        theatreRepository.deleteAll();
    }

    @Test
    void testSaveTheatre() {
        Theatre theatre = new Theatre("Small Hall", 20, 12);
        Theatre savedTheatre = theatreRepository.save(theatre);
        assertNotNull(savedTheatre.getTheatreId());
        assertEquals("Small Hall", savedTheatre.getName());
    }

    @Test
    void testFindTheatreById() {
        Optional<Theatre> foundTheatre = theatreRepository.findById(testTheatre.getTheatreId());
        assertTrue(foundTheatre.isPresent());
        assertEquals("Main Hall", foundTheatre.get().getName());
    }

    @Test
    void testDeleteTheatre() {
        theatreRepository.delete(testTheatre);
        Optional<Theatre> deletedTheatre = theatreRepository.findById(testTheatre.getTheatreId());
        assertFalse(deletedTheatre.isPresent());
    }
}

