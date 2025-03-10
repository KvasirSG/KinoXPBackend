package com.example.kinoxpbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    @JsonIgnore // Prevents infinite recursion
    private Show show;

    @Column(nullable = false)
    private int rowIndex;

    @Column(nullable = false)
    private int seatNumber;

    @Column(nullable = false)
    private boolean isBooked = false; // New field to track if the seat is booked

    // Constructors
    public Seat() {}

    public Seat(Show show, int rowIndex, int seatNumber) {
        this.show = show;
        this.rowIndex = rowIndex;
        this.seatNumber = seatNumber;
        this.isBooked = false;
    }

    // Getters and Setters
    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}


