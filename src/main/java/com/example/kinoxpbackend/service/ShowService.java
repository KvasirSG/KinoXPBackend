package com.example.kinoxpbackend.service;

import com.example.kinoxpbackend.model.Seat;
import com.example.kinoxpbackend.model.Show;
import com.example.kinoxpbackend.repository.SeatRepository;
import com.example.kinoxpbackend.repository.ShowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    public ShowService(ShowRepository showRepository, SeatRepository seatRepository) {
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public Optional<Show> getShowById(Long id) {
        return showRepository.findById(id);
    }

    public List<Show> getShowsByTheatre(Long theatreId) {
        return showRepository.findByTheatre_TheatreId(theatreId);
    }

    public List<Show> getShowsByMovie(Long movieId) {
        return showRepository.findByMovie_MovieId(movieId);
    }

    public List<Show> getShowsByTimeRange(LocalDateTime start, LocalDateTime end) {
        return showRepository.findByShowTimeBetween(start, end);
    }

    @Transactional
    public Show createShow(Show show) {
        // Save the show first
        Show savedShow = showRepository.save(show);

        // Generate seats for this specific show
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= savedShow.getTheatre().getTotalRows(); row++) {
            for (int seatNum = 1; seatNum <= savedShow.getTheatre().getSeatsPerRow(); seatNum++) {
                seats.add(new Seat(savedShow, row, seatNum));
            }
        }
        seatRepository.saveAll(seats);

        return savedShow;
    }

    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }
}

