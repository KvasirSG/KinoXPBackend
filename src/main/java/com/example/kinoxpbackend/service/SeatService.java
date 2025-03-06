package com.example.kinoxpbackend.service;

import com.example.kinoxpbackend.model.Seat;
import com.example.kinoxpbackend.repository.SeatRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public List<Seat> getSeatsByTheatre(Long theatreId) {
        return seatRepository.findByTheatre_TheatreId(theatreId);
    }

    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public void deleteSeat(Long id) {
        seatRepository.deleteById(id);
    }
}

