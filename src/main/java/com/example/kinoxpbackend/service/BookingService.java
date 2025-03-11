package com.example.kinoxpbackend.service;

import com.example.kinoxpbackend.model.Booking;
import com.example.kinoxpbackend.model.Seat;
import com.example.kinoxpbackend.repository.BookingRepository;
import com.example.kinoxpbackend.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;

    public BookingService(BookingRepository bookingRepository, SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
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
        // Ensure all seats exist in DB before saving
        Set<Seat> validSeats = new HashSet<>();
        for (Seat seat : booking.getSeats()) {
            Optional<Seat> existingSeat = seatRepository.findById(seat.getSeatId());
            if (existingSeat.isEmpty() || existingSeat.get().isBooked()) {
                throw new RuntimeException("One or more seats are already booked or invalid.");
            }
            existingSeat.get().setBooked(true); // Mark seat as booked
            validSeats.add(existingSeat.get());
        }

        // Set valid seats
        booking.setSeats(validSeats);

        // ✅ Ensure `bookingDate` is set before saving
        booking.setBookingDate(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long id) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();

            // ✅ Mark all booked seats as available again
            for (Seat seat : booking.getSeats()) {
                seat.setBooked(false);
                seatRepository.save(seat);
            }

            // ✅ Update booking status to "CANCELED"
            booking.setStatus("CANCELED");
            bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Booking not found with ID: " + id);
        }
    }

}

