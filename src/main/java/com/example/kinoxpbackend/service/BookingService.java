package com.example.kinoxpbackend.service;

import com.example.kinoxpbackend.model.Booking;
import com.example.kinoxpbackend.repository.BookingRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomer_CustomerId(customerId);
    }

    public List<Booking> getBookingsByShow(Long showId) {
        return bookingRepository.findByShow_ShowId(showId);
    }

    public Booking createBooking(Booking booking) {
        Set<Long> seatIds = booking.getSeats().stream().map(seat -> seat.getSeatId()).collect(Collectors.toSet());

        // Check if seats are already booked for this show
        if (bookingRepository.existsByShow_ShowIdAndSeats_SeatIdIn(booking.getShow().getShowId(), seatIds)) {
            throw new RuntimeException("One or more seats are already booked for this show.");
        }

        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            Booking updatedBooking = booking.get();
            updatedBooking.setStatus("CANCELED");
            bookingRepository.save(updatedBooking);
        }
    }
}

