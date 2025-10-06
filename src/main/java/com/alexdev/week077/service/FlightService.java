package com.alexdev.week077.service;

import com.alexdev.week077.entity.Flight;
import com.alexdev.week077.repository.FlightRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public Flight createFlight(Flight flight) {
        if (flight.getEstDepartureTime().isAfter(flight.getEstArrivalTime()) ||
            flight.getEstDepartureTime().isEqual(flight.getEstArrivalTime())) {
            throw new RuntimeException("Departure time must be before arrival time");
        }

        if (flightRepository.existsByFlightNumber(flight.getFlightNumber())) {
            throw new RuntimeException("Flight number already exists");
        }

        return flightRepository.save(flight);
    }

    public Flight findById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    public List<Flight> searchFlights(String flightNumber, String airlineName,
                                     LocalDateTime estDepartureTimeFrom,
                                     LocalDateTime estDepartureTimeTo) {
        Specification<Flight> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (flightNumber != null && !flightNumber.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("flightNumber")),
                        "%" + flightNumber.toUpperCase() + "%"
                ));
            }

            if (airlineName != null && !airlineName.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("airlineName")),
                        "%" + airlineName.toUpperCase() + "%"
                ));
            }

            if (estDepartureTimeFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("estDepartureTime"),
                        estDepartureTimeFrom
                ));
            }

            if (estDepartureTimeTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("estDepartureTime"),
                        estDepartureTimeTo
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return flightRepository.findAll(spec);
    }

    public void deleteAll() {
        flightRepository.deleteAll();
    }
}
