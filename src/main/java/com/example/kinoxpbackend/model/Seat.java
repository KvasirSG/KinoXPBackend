package com.example.kinoxpbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @Column(name = "row_index", nullable = false)  // 🔹 Renamed from "row_number"
    private int rowIndex;

    @Column(nullable = false)
    private int seatNumber;

    // Constructors
    public Seat() {}

    public Seat(Theatre theatre, int rowIndex, int seatNumber) {
        this.theatre = theatre;
        this.rowIndex = rowIndex;
        this.seatNumber = seatNumber;
    }

    // Getters and Setters
    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
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
}

