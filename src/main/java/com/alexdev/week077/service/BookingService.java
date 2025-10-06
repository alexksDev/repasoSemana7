package com.alexdev.week077.service;

import com.alexdev.week077.entity.Booking;
import com.alexdev.week077.entity.Flight;
import com.alexdev.week077.entity.User;
import com.alexdev.week077.event.BookingCreatedEvent;
import com.alexdev.week077.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightService flightService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Booking bookFlight(Long flightId, User customer) {
        Flight flight = flightService.findById(flightId);

        if (flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("No available seats");
        }

        LocalDateTime now = LocalDateTime.now();
        if (flight.getEstDepartureTime().isBefore(now)) {
            throw new RuntimeException("Flight is in the past");
        }

        if (flight.getEstDepartureTime().isBefore(now) || flight.getEstArrivalTime().isBefore(now)) {
            throw new RuntimeException("Flight is in the past or in transit");
        }

        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                customer,
                flight.getEstDepartureTime(),
                flight.getEstArrivalTime()
        );

        if (!overlappingBookings.isEmpty()) {
            throw new RuntimeException("Customer has overlapping flight booking");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);

        Booking booking = new Booking();
        booking.setBookingDate(LocalDateTime.now());
        booking.setFlight(flight);
        booking.setCustomer(customer);

        Booking savedBooking = bookingRepository.save(booking);

        eventPublisher.publishEvent(new BookingCreatedEvent(this, savedBooking));

        return savedBooking;
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public void deleteAll() {
        bookingRepository.deleteAll();
    }
}
