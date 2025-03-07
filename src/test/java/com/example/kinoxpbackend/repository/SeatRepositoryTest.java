package com.example.kinoxpbackend.repository;

import com.example.kinoxpbackend.model.Seat;
import com.example.kinoxpbackend.model.Show;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SeatRepositoryTest {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowRepository showRepository;

    private Show testShow;
    private Seat testSeat;

    @BeforeEach
    void setUp() {
        testShow = new Show();
        showRepository.save(testShow);

        testSeat = new Seat(testShow, 1, 5);
        seatRepository.save(testSeat);
    }

    @AfterEach
    void tearDown() {
        seatRepository.deleteAll();
        showRepository.deleteAll();
    }

    @Test
    void testSaveSeat() {
        Seat seat = new Seat(testShow, 2, 10);
        Seat savedSeat = seatRepository.save(seat);
        assertNotNull(savedSeat.getSeatId());
        assertEquals(2, savedSeat.getRowIndex());
    }

    @Test
    void testFindSeatById() {
        Optional<Seat> foundSeat = seatRepository.findById(testSeat.getSeatId());
        assertTrue(foundSeat.isPresent());
        assertEquals(1, foundSeat.get().getRowIndex());
    }

    @Test
    void testFindSeatsByShow() {
        List<Seat> seats = seatRepository.findByShow_ShowId(testShow.getShowId());
        assertFalse(seats.isEmpty());
        assertEquals(1, seats.get(0).getRowIndex());
    }

    @Test
    void testDeleteSeat() {
        seatRepository.delete(testSeat);
        Optional<Seat> deletedSeat = seatRepository.findById(testSeat.getSeatId());
        assertFalse(deletedSeat.isPresent());
    }
}
