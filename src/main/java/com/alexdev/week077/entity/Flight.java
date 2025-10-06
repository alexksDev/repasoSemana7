package com.alexdev.week077.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Data
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String airlineName;

    @Column(nullable = false, unique = true)
    private String flightNumber;

    @Column(nullable = false)
    private LocalDateTime estDepartureTime;

    @Column(nullable = false)
    private LocalDateTime estArrivalTime;

    @Column(nullable = false)
    private Integer availableSeats;
}
