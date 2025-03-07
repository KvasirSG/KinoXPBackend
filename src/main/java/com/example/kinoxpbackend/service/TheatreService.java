package com.example.kinoxpbackend.service;

import com.example.kinoxpbackend.model.Seat;
import com.example.kinoxpbackend.model.Theatre;
import com.example.kinoxpbackend.repository.SeatRepository;
import com.example.kinoxpbackend.repository.TheatreRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TheatreService {

    private final TheatreRepository theatreRepository;
    private final SeatRepository seatRepository;
    public TheatreService(TheatreRepository theatreRepository, SeatRepository seatRepository) {
        this.theatreRepository = theatreRepository;
        this.seatRepository = seatRepository;
    }

    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    public Optional<Theatre> getTheatreById(Long id) {
        return theatreRepository.findById(id);
    }

    public void deleteTheatre(Long id) {
        theatreRepository.deleteById(id);
    }

    @Transactional
    public Theatre createTheatre(Theatre theatre) {
        // Save the theatre first
        Theatre savedTheatre = theatreRepository.save(theatre);

        // Generate seats based on totalRows and seatsPerRow
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= theatre.getTotalRows(); row++) {
            for (int seatNum = 1; seatNum <= theatre.getSeatsPerRow(); seatNum++) {
                seats.add(new Seat(savedTheatre, row, seatNum));
            }
        }
        seatRepository.saveAll(seats);
        return savedTheatre;
    }
}

