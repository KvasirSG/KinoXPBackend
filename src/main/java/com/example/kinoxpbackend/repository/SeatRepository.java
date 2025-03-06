package com.example.kinoxpbackend.repository;

import com.example.kinoxpbackend.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByTheatre_TheatreId(Long theatreId);
}

