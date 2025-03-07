package com.example.kinoxpbackend.service;

import com.example.kinoxpbackend.model.Seat;
import com.example.kinoxpbackend.repository.SeatRepository;
import com.example.kinoxpbackend.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;

    public SeatService(SeatRepository seatRepository, BookingRepository bookingRepository) {
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public Optional<Seat> getSeatById(Long id) {
        return seatRepository.findById(id);
    }

    public List<Seat> getSeatsByTheatre(Long theatreId) {
        return seatRepository.findByTheatre_TheatreId(theatreId);
    }

    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public void deleteSeat(Long id) {
        seatRepository.deleteById(id);
    }

    public Map<String, List<Seat>> getSeatsAvailabilityForShow(Long showId) {
        // Get all seats in the theatre where the show is playing
        List<Seat> allSeats = seatRepository.findByTheatre_TheatreId(
                bookingRepository.findByShow_ShowId(showId)
                        .stream()
                        .findFirst()
                        .map(booking -> booking.getShow().getTheatre().getTheatreId())
                        .orElseThrow(() -> new RuntimeException("Show not found"))
        );

        // Get all booked seats for this show
        Set<Seat> bookedSeats = bookingRepository.findByShow_ShowId(showId)
                .stream()
                .flatMap(booking -> booking.getSeats().stream())
                .collect(Collectors.toSet());

        // Separate available and unavailable seats
        List<Seat> availableSeats = new ArrayList<>();
        List<Seat> unavailableSeats = new ArrayList<>();

        for (Seat seat : allSeats) {
            if (bookedSeats.contains(seat)) {
                unavailableSeats.add(seat);
            } else {
                availableSeats.add(seat);
            }
        }

        // Return both lists as a map
        Map<String, List<Seat>> seatAvailability = new HashMap<>();
        seatAvailability.put("availableSeats", availableSeats);
        seatAvailability.put("unavailableSeats", unavailableSeats);

        return seatAvailability;
    }
}

