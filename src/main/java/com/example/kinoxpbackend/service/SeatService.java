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

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public Optional<Seat> getSeatById(Long id) {
        return seatRepository.findById(id);
    }

    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public void deleteSeat(Long id) {
        seatRepository.deleteById(id);
    }

    public Map<String, List<Seat>> getSeatAvailabilityForShow(Long showId) {
        List<Seat> allSeats = seatRepository.findByShow_ShowId(showId);

        List<Seat> availableSeats = allSeats.stream()
                .filter(seat -> !seat.isBooked())
                .toList();

        List<Seat> bookedSeats = allSeats.stream()
                .filter(Seat::isBooked)
                .toList();

        Map<String, List<Seat>> seatAvailability = new HashMap<>();
        seatAvailability.put("availableSeats", availableSeats);
        seatAvailability.put("bookedSeats", bookedSeats);

        return seatAvailability;
    }
}

