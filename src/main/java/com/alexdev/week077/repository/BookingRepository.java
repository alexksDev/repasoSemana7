package com.alexdev.week077.repository;

import com.alexdev.week077.entity.Booking;
import com.alexdev.week077.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.customer = :customer AND " +
           "((b.flight.estDepartureTime < :arrivalTime AND b.flight.estArrivalTime > :departureTime))")
    List<Booking> findOverlappingBookings(
        @Param("customer") User customer,
        @Param("departureTime") LocalDateTime departureTime,
        @Param("arrivalTime") LocalDateTime arrivalTime
    );
}
