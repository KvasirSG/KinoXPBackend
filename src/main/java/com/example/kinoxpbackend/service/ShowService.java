package com.example.kinoxpbackend.service;

import com.example.kinoxpbackend.model.Show;
import com.example.kinoxpbackend.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
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

    public Show createShow(Show show) {
        return showRepository.save(show);
    }

    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }
}

