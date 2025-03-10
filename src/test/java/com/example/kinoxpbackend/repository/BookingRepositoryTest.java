package com.example.kinoxpbackend.repository;

import com.example.kinoxpbackend.model.Booking;
import com.example.kinoxpbackend.model.Customer;
import com.example.kinoxpbackend.model.Movie;
import com.example.kinoxpbackend.model.Show;
import com.example.kinoxpbackend.model.Theatre;
import com.example.kinoxpbackend.model.Seat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    private SeatRepository seatRepository;

    private Customer testCustomer;
    private Show testShow;
    private Booking testBooking;
    private Set<Seat> testSeats;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("John Doe", "john.doe@example.com", "123456789");
        customerRepository.save(testCustomer);

        Movie testMovie = new Movie("Inception", "Sci-Fi", 148, 13);
        movieRepository.save(testMovie);

        Theatre testTheatre = new Theatre("Main Hall", 25, 16);
        theatreRepository.save(testTheatre);

        testShow = new Show(testMovie, testTheatre, LocalDateTime.now().plusDays(1));
        showRepository.save(testShow);

        Seat seat1 = new Seat(testShow, 1, 5);
        Seat seat2 = new Seat(testShow, 1, 6);
        seatRepository.save(seat1);
        seatRepository.save(seat2);

        testSeats = new HashSet<>();
        testSeats.add(seat1);
        testSeats.add(seat2);

        testBooking = new Booking(testCustomer, testShow, LocalDateTime.now(), testSeats, 50.00, "CONFIRMED");
        bookingRepository.save(testBooking);
    }

    @AfterEach
    void tearDown() {
        bookingRepository.deleteAll();
        seatRepository.deleteAll();
        showRepository.deleteAll();
        movieRepository.deleteAll();
        theatreRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void testSaveBooking() {
        Seat seat3 = new Seat(testShow, 2, 10);
        seatRepository.save(seat3);

        Set<Seat> newSeats = new HashSet<>();
        newSeats.add(seat3);

        Booking booking = new Booking(testCustomer, testShow, LocalDateTime.now(), newSeats, 25.00, "CONFIRMED");
        Booking savedBooking = bookingRepository.save(booking);

        assertNotNull(savedBooking.getBookingId());
        assertEquals(1, savedBooking.getSeats().size());
    }

    @Test
    void testFindBookingById() {
        Optional<Booking> foundBooking = bookingRepository.findById(testBooking.getBookingId());
        assertTrue(foundBooking.isPresent());
        assertEquals("CONFIRMED", foundBooking.get().getStatus());
        assertEquals(2, foundBooking.get().getSeats().size());
    }

    @Test
    void testFindBookingsByCustomer() {
        List<Booking> bookings = bookingRepository.findByCustomer_CustomerId(testCustomer.getCustomerId());
        assertFalse(bookings.isEmpty());
        assertEquals(1, bookings.size());
    }

    @Test
    void testSeatAvailabilityCheck() {
        Set<Long> seatIds = testSeats.stream().map(Seat::getSeatId).collect(java.util.stream.Collectors.toSet());
        boolean seatTaken = bookingRepository.existsByShow_ShowIdAndSeats_SeatIdIn(testShow.getShowId(), seatIds);
        assertTrue(seatTaken);
    }

    @Test
    void testCancelBooking() {
        testBooking.setStatus("CANCELED");
        bookingRepository.save(testBooking);

        Optional<Booking> canceledBooking = bookingRepository.findById(testBooking.getBookingId());
        assertTrue(canceledBooking.isPresent());
        assertEquals("CANCELED", canceledBooking.get().getStatus());
    }
}
