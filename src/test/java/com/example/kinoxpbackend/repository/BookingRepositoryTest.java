package com.example.kinoxpbackend.repository;

import com.example.kinoxpbackend.model.Booking;
import com.example.kinoxpbackend.model.Customer;
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
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    private Customer testCustomer;
    private Show testShow;
    private Booking testBooking;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("John Doe", "john.doe@example.com", "123456789");
        customerRepository.save(testCustomer);

        Movie testMovie = new Movie("Inception", "Sci-Fi", 148, 13);
        movieRepository.save(testMovie);

        Theatre testTheatre = new Theatre("Main Hall", 25, 16);
        theatreRepository.save(testTheatre);

        testShow = new Show(testMovie, testTheatre, LocalDateTime.now().plusDays(1), 100);
        showRepository.save(testShow);

        testBooking = new Booking(testCustomer, testShow, LocalDateTime.now(), 2, 25.00, "CONFIRMED");
        bookingRepository.save(testBooking);
    }

    @AfterEach
    void tearDown() {
        bookingRepository.deleteAll();
        showRepository.deleteAll();
        movieRepository.deleteAll();
        theatreRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void testSaveBooking() {
        Booking booking = new Booking(testCustomer, testShow, LocalDateTime.now(), 3, 30.00, "CONFIRMED");
        Booking savedBooking = bookingRepository.save(booking);
        assertNotNull(savedBooking.getBookingId());
        assertEquals(3, savedBooking.getNumberOfSeats());
    }

    @Test
    void testFindBookingById() {
        Optional<Booking> foundBooking = bookingRepository.findById(testBooking.getBookingId());
        assertTrue(foundBooking.isPresent());
        assertEquals("CONFIRMED", foundBooking.get().getStatus());
    }

    @Test
    void testCancelBooking() {
        bookingRepository.delete(testBooking);
        Optional<Booking> deletedBooking = bookingRepository.findById(testBooking.getBookingId());
        assertFalse(deletedBooking.isPresent());
    }
}

