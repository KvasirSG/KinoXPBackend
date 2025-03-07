package com.example.kinoxpbackend.repository;

import com.example.kinoxpbackend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomer_CustomerId(Long customerId);
    List<Booking> findByShow_ShowId(Long showId);
    boolean existsByShow_ShowIdAndSeats_SeatIdIn(Long showId, Set<Long> seatIds);
}

