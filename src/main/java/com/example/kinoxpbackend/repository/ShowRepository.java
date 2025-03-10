package com.example.kinoxpbackend.repository;

import com.example.kinoxpbackend.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByTheatre_TheatreId(Long theatreId);
    List<Show> findByMovie_MovieId(Long movieId);
    List<Show> findByShowTimeBetween(LocalDateTime start, LocalDateTime end);
}

