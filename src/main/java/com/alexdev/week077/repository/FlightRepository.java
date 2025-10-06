package com.alexdev.week077.repository;

import com.alexdev.week077.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>, JpaSpecificationExecutor<Flight> {
    Optional<Flight> findByFlightNumber(String flightNumber);
    boolean existsByFlightNumber(String flightNumber);
}
