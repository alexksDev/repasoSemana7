package com.alexdev.week077.controller;

import com.alexdev.week077.service.BookingService;
import com.alexdev.week077.service.FlightService;
import com.alexdev.week077.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cleanup")
@RequiredArgsConstructor
public class CleanupController {

    private final UserService userService;
    private final FlightService flightService;
    private final BookingService bookingService;

    @DeleteMapping
    public ResponseEntity<Void> cleanup() {
        bookingService.deleteAll();
        flightService.deleteAll();
        userService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
