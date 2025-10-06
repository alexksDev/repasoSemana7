package com.alexdev.week077.controller;

import com.alexdev.week077.dto.BookingResponseDTO;
import com.alexdev.week077.entity.Booking;
import com.alexdev.week077.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flight/book")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.findById(id);

        BookingResponseDTO response = new BookingResponseDTO();
        response.setId(String.valueOf(booking.getId()));
        response.setBookingDate(booking.getBookingDate());
        response.setFlightId(String.valueOf(booking.getFlight().getId()));
        response.setFlightNumber(booking.getFlight().getFlightNumber());
        response.setCustomerId(String.valueOf(booking.getCustomer().getId()));
        response.setCustomerFirstName(booking.getCustomer().getFirstName());
        response.setCustomerLastName(booking.getCustomer().getLastName());

        return ResponseEntity.ok(response);
    }
}
